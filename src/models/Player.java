/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskdatabase;

/**
 *
 * @author sarat
 */
public class Player {
    
    int ID;
    String firstName;
    String lastName;
    String email;
    int score;
    String password;
   public Player(){
   
   
   }
   public Player(String fName,String lName,String email){
   
       firstName=fName;
       lastName=lName;
       this.email=email;
       score=0;
   
   }
    
    void setfirstName(String fname){
       firstName=fname;
    }
    
    void setlastName(String lname){
       lastName=lname;
    }
    
    void setEmail(String em){
       email=em;
    }
    
    void setScore(int sc){
       score=sc;
    }
    
     void setPassword(String pass){
       password=pass;
    }
    
    String getfirstName(){
        return firstName;
    }
    String getlastName(){
        return lastName;
    }
    String getEmail(){
        return email;
    }
    int getScore(){
        return score;
    }
    
    String getPassword(){
    
        return password;
    }
    
    
    
}
