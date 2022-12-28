import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;

public class TCPClient {

    public void Connect(String host, int port) {

        Socket clientSocket;
        String phrase;
        String modifiedPhrase;
        String ip = "127.0.0.1";
        BufferedReader inFromUser;
        DataOutputStream outToServer;
        BufferedReader inFromServer;
//        int port = 6999;
//        String username;

//        System.out.println("Starting the client application");
        System.out.println("-- Client connecting to host/port " + host + "/" + port + " --");

        try {
            //Input text stream to get data from the user via the keyboard.
            inFromUser = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Defined input text stream from the keyboard");

            //Create a new socket named clientSocket for port 6789 of the current IP 127.0.0.1 (current system).
            //This socket will be used to send a connection request to the server running at the provided IP on port 6789.
            //The hostname may be used instead of the loop IP address, or any IP or hostname.
            clientSocket = new Socket(host, port);

            System.out.println("Defined new socket " + ip + port + " for connection to the server");

            //Output text stream to send data to the server.
            //The output text stream connects to the socket of the server to send characters.
            outToServer = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("Defined output text stream to the server using the socket");

            //Input text stream to get data from the server.
            //This input text stream connects to the socket of the server to receive characters.
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.out.println("Can not locate host/port " + host + "/" + port);
            return;
        }catch (IOException e) {
            System.out.println("Could not establish connection to: " + host + "/" + port);
            return;
        }

        System.out.println("<-- Connection established  -->");


        //Read a text string from the keyboard until you press Enter (new line character).
        try {
            while (true) {
                System.out.println("test4543");

                System.out.print("Write a phrase to send to the server:");
                phrase = inFromUser.readLine();

                if (phrase.equalsIgnoreCase("exit")) {
                    break;
                }
                String variabletoSend = phrase;

//            if (phrase.equalsIgnoreCase("login")) {
//                System.out.println("Enter username");
//                variabletoSend += " " + inFromUser.readLine();
//
//                System.out.println("Enter password");
//                variabletoSend += " " + inFromUser.readLine();
//                //ask the user to enter String username and String password
//                //Send the data to the server
//            }
                if (phrase.equalsIgnoreCase("register")) {
                    //ask the user to enter String username, String password, int phoneNum, String city, String fullName
                    //Send the data to the server
                    System.out.println("Enter username");
                    variabletoSend += " " + inFromUser.readLine();

                    System.out.println("Enter phone number");
                    variabletoSend += " " + inFromUser.readLine();

                    System.out.println("Enter city");
                    variabletoSend += " " + inFromUser.readLine();

                    System.out.println("Enter full name");
                    variabletoSend += " " + inFromUser.readLine();

                } else if (phrase.equalsIgnoreCase("getAvailableSeats")) {
                    variabletoSend = "getAvailableSeats";
                } else if (phrase.equalsIgnoreCase("perprice")) {
                    //prompt user to enter price
                    //store the variable
                    System.out.println("Enter price to search by");
                    variabletoSend = "getAvailableSeats " + inFromUser.readLine();
                } else if (phrase.equalsIgnoreCase("reserve")) {
                    //prompt user to enter the seat ID they want, customer phone, customer Name
                    //convert seat ID to capitals
                    System.out.println("Enter your username");
                    variabletoSend += " " + inFromUser.readLine();

                    System.out.println("Enter seat ID");
                    variabletoSend += " " + inFromUser.readLine();
                    System.out.println("Enter your customer's phone number");
                    variabletoSend += " " + inFromUser.readLine();
                    System.out.println("Enter your customer's name");
                    variabletoSend += " " + inFromUser.readLine();
                } else if (phrase.equalsIgnoreCase("reserverAnon")) {
                    //TODO: venue.reserverSeat(true)
                    //idk how
                    variabletoSend = "reserverAnon";
                }

                System.out.println(phrase);
                System.out.println("Sending the phrase to the server");

                //Send data (text string) to the server
                System.out.println(variabletoSend);
                outToServer.writeBytes(variabletoSend + "\n");

                System.out.println("Getting server's response");

                //Consume the reply/input from the server.
                modifiedPhrase = inFromServer.readLine();

                //Print the reply/input from the server to the terminal.
                System.out.println("Response from the Server: " + modifiedPhrase);
                outToServer.flush();
            }

            System.out.println("Closing the TCP connection to the server");
            outToServer.close();
            inFromServer.close();
            //The connection socket and the TCP connection are terminated by the client
            clientSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String server;
        int port;

        server = "127.0.0.1";
        port = 6999;

        TCPClient myclient = new TCPClient();

        myclient.Connect(server,port);
        System.out.println("<-- Client has exited -->");
    }

}