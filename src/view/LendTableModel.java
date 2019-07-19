package view;

import moldel.DatabaseConnection;
import moldel.Lending;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LendTableModel extends AbstractTableModel{
    
    private ArrayList<Lending> lendings;
    
    public LendTableModel() {
        lendings=new ArrayList<>();
        loadTable();
    }
    
    public void loadTable(){
        lendings.clear();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from lendings");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lendings.add(new Lending(rs.getInt("LENDID"), rs.getDate("TIME"), rs.getInt("LOANEDFILMID"), rs.getString("PERSONNAME"), rs.getDate("BACKTIME")));
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println("SQL problema!\n"+ex);
        }
        
        fireTableDataChanged();
    }

    public ArrayList<Lending> getLendings() {
        return lendings;
    }
    
    
    
    @Override
    public String getColumnName(int i) {
        String colNames[] = new String[]{ "ID", "Kölcsönző személy", "Kölcsönadott film id", "Kölcsönadás dátuma","Visszaadás dátuma" };
        return colNames[i];
    }
    
    @Override
    public int getRowCount() {
        return lendings.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: return lendings.get(rowIndex).getId();
            case 1: return lendings.get(rowIndex).getBorrowingPerson();
            case 2: return lendings.get(rowIndex).getBorrowedFilmID();
            case 3: return lendings.get(rowIndex).getDate(); 
            case 4: return lendings.get(rowIndex).getBackDate(); 
            default: return null;
        }        
    }
    
}
