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

public class DbManager {

    Connection conn;
    PreparedStatement pstmt;
    ArrayList<Player> playerList = new ArrayList<>();

    public DbManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean connect() throws ClassNotFoundException {
        try {

            String url = "jdbc:mysql://localhost:3306/tictactoe";
            conn = DriverManager.getConnection(url, "root", "");

            if (conn != null) {
                System.out.println("Connected");
                return true;
            }

            }catch (SQLException e) {

            //System.out.println(e.getMessage());
            return false;
        }
        return false;

        }
        // get All Playerss
    public ArrayList<Player> getAllPlayers() {

        ArrayList<Player> list = null;

        try {
            list = new ArrayList<>();
            String query = "select * from Player";
            pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Player p = new Player();
                p.setid(rs.getInt(1));
                p.setfirstName(rs.getString(2));
                p.setlastName(rs.getString(3));
                p.setScore(rs.getInt(6));
                p.setEmail(rs.getString(4));
                p.setPassword(rs.getString(5));
                p.setStatus(rs.getInt(7));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    //Login//
    public Player getPlayer(String email) {

        Player pfound = null;
        
        try {
            
            String query = "select * from Player where email = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {

                pfound = new Player();
                pfound.setid(rs.getInt(1));
                pfound.setfirstName(rs.getString(2));
                pfound.setlastName(rs.getString(3));
                pfound.setScore(rs.getInt(6));
                pfound.setEmail(rs.getString(4));
                pfound.setPassword(rs.getString(5));
                pfound.setStatus(rs.getInt(7));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pfound;
    }

    //SignUP
    public boolean addPlayer(String fName, String lName, String email, String password) throws SQLException {

        int score = 0;

        //Adding New Palyer
        if (playerList.isEmpty() || getPlayer(email) == null) {

            Player newPlayer = new Player();
            newPlayer.setfirstName(fName);
            newPlayer.setlastName(lName);
            newPlayer.setEmail(email);
            newPlayer.setPassword(password);
            newPlayer.setScore(score);
            playerList.add(newPlayer);
            String query = "INSERT INTO Player (firstname,lastname, email, password, score, status) Values(?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(query);
          //  pstmt.setInt(1, 0);
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setInt(5, score);
            pstmt.setInt(6, 1);
            pstmt.executeUpdate();
            return true;

        } // Player Already Existed
        else {

            return false;

        }

    }

    // Updating Score 
    public void updateScore(String email) throws SQLException {

        int newScore = getPlayer(email).getScore() + 1;
        
        String query = "update Player set score =? where email=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, newScore);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
       
    }
    
    public void updateStatus(int state, String email) throws SQLException {
       
        String query = "update Player set status = ? where email = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, state);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
       
    }

    
}
