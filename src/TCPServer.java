import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        String clientPhrase;
        String capitalizedPhrase;
        int port = 6999;

        System.out.println("Starting the server application");

        //Create a new socket named welcomeSocket for port 6789 of the current IP.
        //This socket will be waiting for incoming connection request by a client.
        ServerSocket welcomeSocket = new ServerSocket(port);

        System.out.println("Defined new socket "+port+" for connection from clients");

        while(true) {

            System.out.println("Waiting for incoming requests");

            //Create a new socket named connectionSocket and assigning to it an incoming connection request to the welcomeSocket.
            //A tunnel is automatically established between the server and the client using the connectionSocket socket.
            //Next, the welcome socket is released.
            Socket connectionSocket = welcomeSocket.accept();

            System.out.println("Establishing an incoming request");

            //Input text stream to get data from the client.
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            //Output text stream to send data to the client.
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            System.out.println("Defined input text stream from the client and output text stream to the client");

            System.out.println("Reading data from the client");

            //Consume the input from the client.
            //Read a text string until the new line character.
            clientPhrase = inFromClient.readLine();
            String variabletoSend = "";

            if (clientPhrase.equalsIgnoreCase("login")){
                //TODO: login stuff
                variabletoSend = "login";
            }
            else if (clientPhrase.equalsIgnoreCase("register")) {
                //TODO: register stuff
                variabletoSend = "register";
            } 
            else if (clientPhrase.equalsIgnoreCase("getAvailableSeats")) {
                //TODO: venue.getAvailableSeats();
                variabletoSend = "getavailableseats";
            }
            else if (clientPhrase.equalsIgnoreCase("getAvailableSeatsPerPrice")) {
                //TODO: venue.getAvailableSeats(price);
                variabletoSend = "getavailableseatsPerPrice";
            }
            else if (clientPhrase.equalsIgnoreCase("reserve")) {
                //TODO: venue.reserveSeat();
                variabletoSend = "reserve";
            }
            else if (clientPhrase.equalsIgnoreCase("reserverAnon")) {
                //TODO: venue.reserverSeat(true)
                variabletoSend = "reserverAnon";
            }
//            List<String> ar = Arrays.asList(clientPhrase.split(" "));
//
//            System.out.println(ar.get(0));
//            System.out.println(ar.get(1));
//            System.out.println(ar.get(2));

//            if (DButil.login(ar.get(1), ar.get(2))) {
//                outToClient.writeBytes("test34"+"\n");
//            } else {
//            Venue venue = new Venue();
//            final LinkedHashMap seats = venue.getSeats();



                System.out.println("test4");

                System.out.println("Manipulating client request and preparing response");

                //Capitalize the letters of the read text string.
//                capitalizedPhrase = clientPhrase + '\n';

                System.out.println("Sending response to the client");

                //Send data to the client using the output text stream.
                outToClient.writeBytes(variabletoSend + "\n");
//            }

        }


        //The server remains active waiting for incoming connections
        //Each TCP connection originating from a client is terminated by the client itself

    }

}