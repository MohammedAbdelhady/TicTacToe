/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskdatabase;
import java.sql.* ;
/**
 *
 * @author sarat
 */
import com.mysql.jdbc.Driver;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.util.ArrayList;
public class TaskDataBase {

    /**
     * @param args the command line arguments
     */
    Connection conn;
    
    ArrayList<Player> playerList = new ArrayList<>();
    private PreparedStatement pstmt;
    int pos;
    
    
    public void connect() throws ClassNotFoundException{
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
             String url="jdbc:mysql://localhost:3306/test";
            conn= DriverManager.getConnection(url,"root","");
            // Class.forName("com.mysql.jdbc.Driver");
  // Setup the connection with the DB
             try{
           // conn = DriverManager.getConnection("jdbc:mysql://my.vertabelo.com/model/jzXfkAwdaMRJYlcYGdk97kr6tU5lRJ92/test?" +
                     //              "user=sara tarek&password=saratarek");
             }
             catch(Exception e){
                 
                 System.out.println("hhhhhhhh");
             }
                     
             System.out.println("connected");
             String sql="select * from player";
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql);
             
             while(rs.next()){
                // System.out.println(rs.getString(3));
                 Player p=new Player();
                 p.ID=rs.getInt(1);
                 p.firstName=rs.getString(2);
                 p.lastName=rs.getString(3);
                 p.score=rs.getInt(4);
                 p.email=rs.getString(5);
                 p.password=rs.getString(6);
                 playerList.add(p);
             }
            
        } catch (SQLException ex) {
           // Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("gggggggg");
        }
        
    }
    
    //Login//
    
     Player getPlayer(String email){
    
      Player pfound=new Player();
        for (int i = 0; i < playerList.size(); i++) {
            
            if(playerList.get(i).email.equalsIgnoreCase(email) ){
                
                pfound=playerList.get(i);
                break;
            }
            
            else{
            
               
                pfound=null;
            }
        }
        
        
        return pfound;
         
    }
     
     //Register
     
     
     
     
     
     
    public void insert(String fName,String lName,String email,String password) throws SQLException {
            int score=0;
            Player newPlayer=new Player();
            newPlayer.setfirstName(fName);
            newPlayer.setlastName(lName);
            newPlayer.setEmail(email);
            playerList.add(newPlayer);
           
           /* pstmt=conn.prepareStatement("INSERT INTO player Values(?,?,?,?,?)");
            pstmt.setString(1,fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, email);
            pstmt.setInt(4,score);
            pstmt.setString(5,password);
            pstmt.executeUpdate();*/
           // System.out.println(pstmt);
           
        String sql="insert into player (firstName,lastName,score,email,password) values('"+fName+"','"+lName+"','"+score+"','"+email+"','"+password+"');";
        System.out.print(sql);
           Statement stmt=conn.createStatement();
             stmt.executeUpdate(sql);
            
        }
        //   intexecuteUpdate(String );
    

    public  void update(Player p1) throws SQLException {
        int id = p1.ID;
        for (int i = 0; i < playerList.size(); i++) {
            if (id == playerList.get(i).ID) {
                playerList.remove(i);
                playerList.add(p1);
            } else {
              //  PersonList.add(p1);
                System.out.println("This ID not already exists");
            }
        }
        String sql="update person set firstname='"+p1.firstName+"',lastname='"+p1.lastName+"',mail='"+p1.email+"' where id = '"+p1.ID+"';";
        System.out.println(sql);
        Statement stmt=conn.createStatement();
             stmt.executeUpdate(sql);
    }

    public void delete(Player p1) throws SQLException {
        int id = p1.ID;
        for (int i = 0; i < playerList.size(); i++) {
            if (id == playerList.get(i).ID) {
                playerList.remove(i);
            }
        }
         String sql="delete from person where id = '"+p1.ID+"';";
        System.out.print(sql);
             Statement stmt=conn.createStatement();
             stmt.executeUpdate(sql);
    }
    public Player first(){
        pos=0;
        Player p=new Player();
        p=playerList.get(pos);
        //System.out.println(p.firstName);
       // System.out.println(PersonList.get(0).firstName);
        for(int i=0;i<playerList.size();i++){
        System.out.println(playerList.get(i).firstName);
        }
        return p;
    }
     public Player last(){
        pos=playerList.size()-1;
        Player p=new Player();
        p=playerList.get(pos);
        //System.out.println(p.firstName);
       // System.out.println(PersonList.get(0).firstName);
        for(int i=0;i<playerList.size();i++){
        System.out.println(playerList.get(i).firstName);
        }
        return p;
    }
     public Player next(){
        pos+=1;
        if(pos>=playerList.size()){
        pos=playerList.size()-1;
        }
        Player p=new Player();
        p=playerList.get(pos);
        System.out.println(pos);
       // System.out.println(PersonList.get(0).firstName);
        
        return p;
    } 
    public Player previous(){
        pos-=1;
        if(pos<0){
        pos=0;
        }
        Player p=new Player();
        p=playerList.get(pos);
        System.out.println(pos);
       // System.out.println(PersonList.get(0).firstName);
        return p;
    }
    
        
    }
    

   
    
    
  /*  public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        connect();
        Person p=new Person();
        p.firstName="sara";
        p.middleName="dd";
        p.lastName="kk";
        p.phone="222";
        p.email="dsdds";
        add(p);
    }*/
    

