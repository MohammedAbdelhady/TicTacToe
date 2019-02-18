package networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    ServerSocket serverSocket;
    private final ArrayList<ClientThread> clientList;

    public Server() {
        clientList = new ArrayList<>();
    }

    public void start() {

        try {
            serverSocket = new ServerSocket(5005);

            display("Server is listening for Clients on port 5005.");

            while (true) {

                Socket socket = serverSocket.accept();

                ClientThread newThread = new ClientThread(socket);
                clientList.add(newThread);
                newThread.start();
                display("player just connected on " + socket.getInetAddress());

            }

        } catch (IOException e) {

            try {

                serverSocket.close();
                for (int i = 0; i < clientList.size(); i++) {
                    clientList.get(i).safelyClose();
                }

            } catch (IOException ex) {
                display("Closing the Server and Clients: " + e);
            }
        }
    }

    private void display(String prompt) {

        System.out.println(prompt);
    }

    private void sendMessage(String type, String source, String destination, String msg) {

        for (int i = 0; i < clientList.size(); i++) {

            if (clientList.get(i).username.equalsIgnoreCase(destination)) {

                if (type.equals("chat")) {
                    clientList.get(i).Output.println(type + "$[" + source + "] : " + msg);
                    display(msg);
                } else if (type.equals("board")) {
                    clientList.get(i).Output.println(type + "$" + msg);
                    display(msg);
                } else if (type.equals("challenge")) {
                    clientList.get(i).Output.println(type + "$" + source + "$wants to challenge you to a game ? " + msg);
                    display(msg);
                } else if (type.equals("accepted")) {
                    clientList.get(i).Output.println(type + "$" + source + "$Accepted the challenge" + msg);
                    display(msg);
                } else if (type.equals("lost")) {
                    clientList.get(i).Output.println(type + "$" + source + "$Won the Game" + msg);
                    display(msg);
                } else if (type.equals("draw")) {
                    clientList.get(i).Output.println(type + "$" + "Appears we have a draw");
                    display(msg);
                }

                break;

            }

        }
    }

    public void stop() {

        try {

            for (int i = 0; i < clientList.size(); i++) {
                clientList.get(i).safelyClose();
                clientList.get(i).stop();
                clientList.remove(i);
            }

            serverSocket.close();

            display("Bye : The Server Has been Closed ..");
        } catch (IOException e) {
            display("Can't Close the Server and Clients: " + e);
        }
    }

    class ClientThread extends Thread {

        Socket socket;
        DataInputStream Input;
        PrintStream Output;
        String username;

        ClientThread(Socket socket) {

            this.socket = socket;

        }

        @Override
        public void run() {

            String msg;

            try {

                Output = new PrintStream(socket.getOutputStream());
                Input = new DataInputStream(socket.getInputStream());

            } catch (IOException e) {

                e.printStackTrace();
            }

            while (true) {
                try {

                    display("Here ");
                    msg = Input.readLine();
                    display(msg + " Message");
                    String[] separatedMsg = msg.split("\\$");
                    display("length " + separatedMsg.length);
                    if (separatedMsg.length == 1) {
                        username = msg;
                        display(username + " is now connected.");
                    } else if (separatedMsg[0].equalsIgnoreCase("chat")) {
                        sendMessage(separatedMsg[0], username, separatedMsg[1], separatedMsg[2]);
                    } else if (separatedMsg[0].equalsIgnoreCase("challenge")) {
                        sendMessage(separatedMsg[0], username, separatedMsg[1], "");
                    } else if (separatedMsg[0].equalsIgnoreCase("accepted")) {
                        sendMessage(separatedMsg[0], username, separatedMsg[1], "");
                    } else if (separatedMsg[0].equalsIgnoreCase("board")) {
                        sendMessage(separatedMsg[0], username, separatedMsg[1], separatedMsg[2] + "$" + separatedMsg[3]);
                    } else if (separatedMsg[0].equalsIgnoreCase("lost")) {
                        sendMessage(separatedMsg[0], username, separatedMsg[1], "");
                    } else if (separatedMsg[0].equalsIgnoreCase("draw")) {
                        sendMessage(separatedMsg[0], username, separatedMsg[1], "");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        private void safelyClose() {

            try {

                if (Output != null) {
                    Output.close();
                }

                if (Input != null) {
                    Input.close();
                }
                if (socket != null) {
                    socket.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
