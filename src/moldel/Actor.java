package moldel;

import java.util.ArrayList;

public class Actor {
    private final String name;
    private final ArrayList<Integer> roles;  
    
     public Actor(String name, ArrayList<Integer> filmIDs) {
        this.name = name;
        this.roles = filmIDs;
    }

    public Actor(String name) {
        this.name = name;
        this.roles = new ArrayList<>();
    }
     
    
    public ArrayList<Integer> getRoles() {
       return roles;
   }
     
    public void addRole(int fimID){
        roles.add(fimID);
    }

    public String getName() {
        return name;
    }
    
}
