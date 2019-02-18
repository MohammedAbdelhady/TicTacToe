
package models;

public class Player {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int score;
    private String password;
    private int status;
    
    
   public Player(){
   
   
   }
   public Player(String fName,String lName,String email,String password){
   
       firstName=fName;
       lastName=lName;
       this.email=email;
       this.password=password;
       score=0;
       status = 1;
       
   
   }
   
   public void setid(int id){
   
       this.id=id;
   }
    
    public void setfirstName(String fname){
       firstName=fname;
    }
    
    public void setlastName(String lname){
       lastName=lname;
    }
    
    public void setEmail(String em){
       email=em;
    }
    
    public void setScore(int sc){
       score=sc;
    }
    
    public void setPassword(String pass){
       password=pass;
    }
    
    public String getfirstName(){
        return firstName;
    }
    public String getlastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public int getScore(){
        return score;
    }
    
    public String getPassword(){
    
        return password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
