import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        String phrase;
        String modifiedPhrase;
        String ip = "127.0.0.1";
        int port = 6999;

        System.out.println("Starting the client application");

        //Input text stream to get data from the user via the keyboard.
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Defined input text stream from the keyboard");

        //Create a new socket named clientSocket for port 6789 of the current IP 127.0.0.1 (current system).
        //This socket will be used to send a connection request to the server running at the provided IP on port 6789.
        //The hostname may be used instead of the loop IP address, or any IP or hostname.
        Socket clientSocket = new Socket(ip, port);

        System.out.println("Defined new socket "+ip+port+" for connection to the server");

        //Output text stream to send data to the server.
        //The output text stream connects to the socket of the server to send characters.
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        System.out.println("Defined output text stream to the server using the socket");

        //Input text stream to get data from the server.
        //This input text stream connects to the socket of the server to receive characters.
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Defined input text stream from the server using the socket");


        //Read a text string from the keyboard until you press Enter (new line character).
        System.out.print("Write a phrase to send to the server:");
        phrase = inFromUser.readLine();

        String variabletoSend = "";

        if (phrase.equalsIgnoreCase("login")){
            //TODO: login stuff
            variabletoSend = "login";
        }
        else if (phrase.equalsIgnoreCase("register")) {
            //TODO: register stuff
            variabletoSend = "register";
        }
        else if (phrase.equalsIgnoreCase("getAvailableSeats")) {
            //TODO: venue.getAvailableSeats();
            variabletoSend = "getavailableseats";
        }
        else if (phrase.equalsIgnoreCase("getAvailableSeatsPerPrice")) {
            //TODO: venue.getAvailableSeats(price);
            variabletoSend = "getavailableseatsPerPrice";
        }
        else if (phrase.equalsIgnoreCase("reserve")) {
            //TODO: venue.reserveSeat();
            variabletoSend = "reserve";
        }
        else if (phrase.equalsIgnoreCase("reserverAnon")) {
            //TODO: venue.reserverSeat(true)
            variabletoSend = "reserverAnon";
        }
//        while (!phrase.equals("exit")) {
//            if (phrase.equalsIgnoreCase("login")) {
//                System.out.print("username:");
//                phrase += " " + inFromUser.readLine() + " ";

//                System.out.print("password:");
//                phrase += inFromUser.readLine();
//            }
        //}

            System.out.println(phrase);
            System.out.println("Sending the phrase to the server");

            //Send data (text string) to the server
            outToServer.writeBytes(variabletoSend + "\n");

            System.out.println("Getting server's response");

            //Consume the reply/input from the server.
            modifiedPhrase = inFromServer.readLine();

            //Print the reply/input from the server to the terminal.
            System.out.println("Response from the Server: " + modifiedPhrase);


        System.out.println("Closing the TCP connection to the server");

        //The connection socket and the TCP connection are terminated by the client
        clientSocket.close();
    }

}