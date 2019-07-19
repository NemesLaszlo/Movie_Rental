package moldel;

import view.LendTableModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SystemManager {
    private static Connection conn;
    
    
    private static ArrayList<Film> films;
    private static ArrayList<Actor> mainCharacters;
    private static LendTableModel ltb;
    public SystemManager() {
        
        try {
            films = new ArrayList<>();
            conn = DatabaseConnection.getConnection();
            fillFilms("select * from films");
            ltb = new LendTableModel();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println("SQL probléma: \n"+ex);
        }
        
    }

    public LendTableModel getLtb() {
        return ltb;
    }
    
    private static void fillFilms(String querry) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(querry);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){


            mainCharacters = new ArrayList<>();

            querry = "SELECT starname FROM roles where filmid="+rs.getInt("filmid");
            PreparedStatement mcPs = conn.prepareStatement(querry);
            ResultSet mcRs = mcPs.executeQuery();
            while(mcRs.next()){
                mainCharacters.add(new Actor(mcRs.getString("starname")));
            }
            films.add(new Film(rs.getInt("filmID"),
                                rs.getString("title"),
                                rs.getString("director"), 
                                rs.getInt("length"),
                                rs.getInt("releaseYear"), 
                                MediaForm.valueOf(rs.getString("mediatype")),
                                rs.getString("coverPath"), 
                                rs.getBoolean("original"),
                                mainCharacters,
                                rs.getBoolean("borrowed"), rs.getInt("lendCount")));
        }
    }
    
    public ArrayList<Film> getFilms() {
        return films;
    }
    
    public static void addNewFilm(String title, String director, int length, int year, MediaForm media, String coverPath, boolean original, ArrayList<Actor> mainCharacters){
        String querry = "INSERT INTO films VALUES("+films.size()+",'"+title+"','"+director+"',"+year+","+length+",'"+media.toString()+"',"+original+",'"+coverPath+"', 0, false)";
        try {
            conn = DatabaseConnection.getConnection();    
            PreparedStatement insertFilm = conn.prepareStatement(querry);
            insertFilm.executeUpdate();  
            for(int i = 0; i<mainCharacters.size();i++){
                querry = "INSERT INTO ROLES VALUES("+films.size()+",'"+mainCharacters.get(i).getName()+"')";
                PreparedStatement insertRole = conn.prepareStatement(querry);
                insertRole.executeUpdate();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassHiba");
        } catch (SQLException ex) {
            System.out.println(querry);
            System.out.println("SQL hiva! Beszúrásnál"+ex);
        }
        
        films.add(new Film(films.size(),title,director,length,year,media,coverPath,original, mainCharacters, false, 0));
    }
    
    public static void deleteFilm(Film film){
        if(!film.isBorrowed()){
            String querry="DELETE FROM ROLES WHERE FILMID="+film.getID();

            try {
                PreparedStatement deleteRolesPS = conn.prepareStatement(querry);
                deleteRolesPS.executeUpdate();

                querry="DELETE FROM FILMS WHERE FILMID="+film.getID();
                PreparedStatement deletePS = conn.prepareStatement(querry);
                deletePS.executeUpdate();

                querry="UPDATE ROLES SET FILMID=FILMID-1 WHERE FILMID>="+(film.getID()+1);
                PreparedStatement setRolesPS = conn.prepareStatement(querry);
                setRolesPS.executeUpdate(); 
                
                querry="UPDATE FILMS SET FILMID=FILMID-1 WHERE FILMID>="+(film.getID()+1);
                PreparedStatement setIDsPS = conn.prepareStatement(querry);
                setIDsPS.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(querry+"\n"+ex);
            }
            films.remove(film);
        }else{
            System.out.println("A film kölcsön van adva. Nem lehet törölni!");
        }
        
        
    }

    public static void alterFilm(int id, String title, String director,int year, String mainCharacter, int length, String media, String coverPath, boolean original){
        String[] mcNames=mainCharacter.split(";");
        ArrayList<Actor> newMCs = new ArrayList<>();
        
        try{
        PreparedStatement rolesDeletePS = conn.prepareStatement("DELETE FROM ROLES where filmid="+id);
        rolesDeletePS.executeUpdate();
        for(int i = 0; i<mcNames.length;i++){
            
            PreparedStatement rolesPS = conn.prepareStatement("INSERT INTO ROLES VALUES("+id+", '"+mcNames[i]+"')");
            rolesPS.executeUpdate();
           newMCs.add(new Actor(mcNames[i]));
        }
       
        String querry = "UPDATE films set title='"+title+"', director='"+director+"', releaseyear="+year+", length="+length+","
                + "mediatype='"+media+"', original="+original+", coverpath='"+coverPath+"' where filmid="+id;
        PreparedStatement alterPS = conn.prepareStatement(querry);
        alterPS.executeUpdate();
        
         } catch (SQLException ex) {
                System.out.println("SQL hiba az alter fimben! A role táblánál\n"+ex);
        }
        films.set(id, new Film(id, title,director, length,year,MediaForm.valueOf(media),coverPath, original, newMCs, films.get(id).isBorrowed(), films.get(id).getLendCount() ));
    } 
    
    public static void searchFilms(String title, String director,String year, String mainCharacter, String length, String media, boolean original, boolean lended){
        if ("Mindkettő".equals(media)) media = "";
        String querry="SELECT * from films where lower(title) like lower('%"+title+"%') "
                        + "and lower(director) like lower('%"+director+"%') and cast(releaseyear as char(4)) like '%"+year+"%' and cast(length as char(3)) like '%"+length+"%'"
                + "and films.filmid in (select filmid from roles natural join films where lower(starname) like lower('%"+mainCharacter+"%'))"
                + "and mediatype like '%"+media+"%' and original="+original+" and borrowed = "+lended;
        try {
            PreparedStatement searchPS = conn.prepareStatement(querry);
            ResultSet rs = searchPS.executeQuery();
            mainCharacters.clear();
            films.clear();
            fillFilms(querry);           
        } catch (SQLException ex) {
            System.out.println(querry+"\n"+ex);
        }
    }
    
    public static void addLend(String personName, Date backDate, Film film){
        java.sql.Date todayDate = new java.sql.Date(System.currentTimeMillis());      
            try {               
                PreparedStatement ps = conn.prepareStatement("INSERT INTO LENDINGS VALUES("+ltb.getLendings().size()+", '"+personName+"', "+film.getID()+", '"+todayDate+"', '"+backDate+"')");
                ps.executeUpdate();
                PreparedStatement updatePS = conn.prepareStatement("UPDATE FILMS SET LENDCOUNT=LENDCOUNT+1, BORROWED=true WHERE filmid="+film.getID());
                updatePS.executeUpdate();
                film.setBorrowed(true);
                film.increaseLendCount();
            } catch (SQLException ex) {
                System.out.println("SQL probléma a kölcsöönzés hozzáadásánál!\n"+ex);
            }
            ltb.loadTable();
        
    }
    
    public static void getBackFilm(Film film){
        java.sql.Date todayDate = new java.sql.Date(System.currentTimeMillis());      
            try {               
                PreparedStatement ps = conn.prepareStatement("UPDATE LENDINGS SET BACKTIME='"+todayDate+"' WHERE LOANEDFILMID="+film.getID()+" and BACKTIME>'"+todayDate+"'");
                ps.executeUpdate();
                PreparedStatement updatePS = conn.prepareStatement("UPDATE FILMS SET BORROWED=false WHERE filmid="+film.getID());
                updatePS.executeUpdate();
                film.setBorrowed(false);
            } catch (SQLException ex) {
                System.out.println("SQL probléma a film visszaadásánál!\n"+ex);
            }
            ltb.loadTable();
    }
}
