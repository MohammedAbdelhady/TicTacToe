
package helpers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Player;
/**
 *
 * @author mr_null
 */
public class DbManager {
    
    Connection conn;
    PreparedStatement pstmt;
    ArrayList<Player> playerList = new ArrayList<>();
   
    
    
    
    public boolean connect() throws ClassNotFoundException{
        try {
                
                String url="jdbc:mysql://sql2.freemysqlhosting.net:3306/";
                String databaseName="sql2276419";
                String user="sql2276419";
                String Password="xH7*hD4*";
                conn= DriverManager.getConnection(url+databaseName,user,Password);
            
                 playerList=getAllPlayers();
                 return true;
            
        }
        catch(SQLException e)
        {
        
            //System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    // get All Playerss
    
    public ArrayList<Player> getAllPlayers() throws SQLException{
            
              ArrayList<Player>list=new ArrayList();
              String query="select * from Player";
              pstmt=conn.prepareStatement(query);
             ResultSet rs=pstmt.executeQuery();
             
             while(rs.next()){
                 
                 Player p=new Player();
                 p.setid(rs.getInt(1));
                 p.setfirstName(rs.getString(2));
                 p.setlastName(rs.getString(3));
                 p.setScore(rs.getInt(4));
                 p.setEmail(rs.getString(5));
                 p.setPassword(rs.getString(6));
                 list.add(p);
             }
             
             return list;
    
    }
    
    
    
    //Login//
    
    public Player getPlayer(String email){
    
      Player pfound=new Player();
      
        for (int i = 0; i < playerList.size(); i++) {
            
             
            if(playerList.get(i).getEmail().equalsIgnoreCase(email) ){
               
                pfound=playerList.get(i);
                
                break;
            }
            
            else{
           
               
                pfound=null;
            }
        }
        
        
        return pfound;
         
    }
     
     
     
     
     
     //SignUP
     
    public boolean addPlayer(String fName,String lName,String email,String password) throws SQLException {
            
         
            int score=0;
            
            //Adding New Palyer
           if (playerList.size()==0||getPlayer(email)==null){
           
               
            Player newPlayer=new Player();
            newPlayer.setfirstName(fName);
            newPlayer.setlastName(lName);
            newPlayer.setEmail(email);
            newPlayer.setPassword(password);
            newPlayer.setScore(score);
            playerList.add(newPlayer);
            String query="INSERT INTO Player Values(?,?,?,?,?,?)";
    
            pstmt=conn.prepareStatement(query);    
            pstmt.setInt(1,0);
            pstmt.setString(2,fName);
            pstmt.setString(3, lName);
            pstmt.setInt(4, score);
            pstmt.setString(5, email);
            pstmt.setString(6,password);
            
            pstmt.executeUpdate();
            return true;
           
           }
           
           // Player Already Existed
           else{
            
                return false;
            
            }
            
          
            
        }
    
    
    // Updating Score 
    
    public void updateScore(int score,String email) throws SQLException{
        
        int oldScore=getPlayer(email).getScore();
        score+=oldScore;
        String query="update Player set score =? where email=?";
        pstmt=conn.prepareStatement(query);
         pstmt.setInt(1,score);
         pstmt.setString(2, email);
         pstmt.executeUpdate();
         playerList.clear();
         playerList=getAllPlayers();
    
    }
    
     
  
        
    }
        
    

