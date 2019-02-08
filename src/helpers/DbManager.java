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

            String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2277463";
            conn = DriverManager.getConnection(url, "sql2277463", "iW6%vE8%");

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

        for (int i = 0; i < playerList.size(); i++) {

            if (playerList.get(i).getEmail().equalsIgnoreCase(email)) {

                pfound = playerList.get(i);

                break;
            }

        }

        return pfound;

    }

    //SignUP
    public boolean addPlayer(String fName, String lName, String email, String password) throws SQLException {

        int score = 0;

        //Adding New Palyer
        if (playerList.size() == 0 || getPlayer(email) == null) {

            Player newPlayer = new Player();
            newPlayer.setfirstName(fName);
            newPlayer.setlastName(lName);
            newPlayer.setEmail(email);
            newPlayer.setPassword(password);
            newPlayer.setScore(score);
            playerList.add(newPlayer);
            String query = "INSERT INTO Player Values(?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 0);
            pstmt.setString(2, fName);
            pstmt.setString(3, lName);
            pstmt.setInt(6, score);
            pstmt.setString(4, email);
            pstmt.setString(5, password);

            pstmt.executeUpdate();
            return true;

        } // Player Already Existed
        else {

            return false;

        }

    }

    // Updating Score 
    public void updateScore(int score, String email) throws SQLException {

        int oldScore = getPlayer(email).getScore();
        score += oldScore;
        String query = "update Player set score =? where email=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, score);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
        playerList.clear();
        playerList = getAllPlayers();

    }

    public static void main(String args[]) {

        try {
            DbManager db = new DbManager();

            if (db.connect()) {
                System.out.println("DB connected");

                try {
                    if (db.addPlayer("HossamMohamedHWAFBsdsd", "Gomaa", "Hossssm@g.com", "123456")) {
                        System.out.println("Player Added Successfully");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
                }

                ArrayList<Player> list = null;
                list = db.getAllPlayers();

                for (Player p : list) {
                    System.out.println(p.getfirstName() + " " + p.getlastName());
                }

            } else {
                System.out.println("Can't connect");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
