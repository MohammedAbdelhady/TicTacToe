package networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public DataInputStream Input;
    public PrintStream Output;
    private Socket socket;
    private String server;
    private int port;
    private String username;
    
    public Client(String server, int port, String username) {
   
        this.server = server;
        this.port = port;
        this.username = username;

        
    }

    public void start() {

                
        try {
            
            socket = new Socket(server, port);
            Input = new DataInputStream(socket.getInputStream());
            Output = new PrintStream(socket.getOutputStream());
            
            Output.println(username);
            display(username + " is connected");

        } catch (IOException ec) {
            display("Error connecting to server: " + ec);

        }
     
    }

    public void display(String msg) {
        System.out.println(msg);
    }
    
    public void send(String msg) {
        Output.println(msg);
    }
    
    public String recieve() {
        
        try {
            return Input.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public void safelyClose() {
        try {
            if (Input != null) {
                Input.close();
            }

            if (Output != null) {
                Output.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}
