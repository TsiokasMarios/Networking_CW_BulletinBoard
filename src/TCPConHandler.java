import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TCPConHandler implements Runnable{

    //Endpoint socket
    protected Socket echoSocket;

    public TCPConHandler(Socket socketToHandle){
        echoSocket = socketToHandle;
    }

    public void run () {

        //Holds the messages we get from the user
        String clientMessage;

        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        BufferedReader inFromClient;
        BufferedWriter outToClient;

        String peerName;

        System.out.println("Starting the server application");

        // Attach a println/readLine interface to the socketso we can read and write strings to the socket.
        try {
            /* Get the IP address from the client */
            peerName = echoSocket.getInetAddress().getHostAddress();
            /* Create a writing stream to the socket */
//            outToClient = new PrintStream( echoSocket.getOutputStream(), true );
            /* Create a reading stream to the socket */
//            inFromClient = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );

            //Input text stream to get data from the client.
            inputStreamReader = new InputStreamReader(echoSocket.getInputStream());

            //Output text stream to send data to the client.
            outputStreamWriter = new OutputStreamWriter(echoSocket.getOutputStream());

            inFromClient = new BufferedReader(inputStreamReader);
            outToClient = new BufferedWriter(outputStreamWriter);
        }
        catch (IOException e) {
            System.out.println("Error creating buffered handles.");
            return;
        }



        while(true) {

            System.out.println("Waiting for incoming requests");

            //Create a new socket named connectionSocket and assigning to it an incoming connection request to the welcomeSocket.
            //A tunnel is automatically established between the server and the client using the connectionSocket socket.
            //Next, the welcome socket is released.

            System.out.println("Establishing an incoming request");

//            //Input text stream to get data from the client.
//            inputStreamReader = new InputStreamReader(connectionSocket.getInputStream());
//
//            //Output text stream to send data to the client.
//            outputStreamWriter = new OutputStreamWriter(connectionSocket.getOutputStream());
//
//            inFromClient = new BufferedReader(inputStreamReader);
//            outToClient = new BufferedWriter(outputStreamWriter);

            System.out.println("Defined input text stream from the client and output text stream to the client");

            System.out.println("Reading data from the client");

            //Consume the input from the client.
            //Read a text string until the new line character.
            while (true) {
                try {
                    clientMessage = inFromClient.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String variableToSend = "";
                List<String> ar = Arrays.asList(clientMessage.split(" "));


                if (ar.get(0).equalsIgnoreCase("register")) {

                    Boolean registered = DButil.register(ar.get(1), Integer.parseInt(ar.get(3)),ar.get(4),ar.get(5));

                    if (registered){
                        variableToSend = "Registered succesfuly";
                    }
                    else {
                        variableToSend = "Something went wrong.Please try again";
                    }

                }
                else if (ar.get(0).equalsIgnoreCase("getAvailableSeats")) {
                    System.out.println(ar.size());
                    LinkedHashMap<String,Seat> map1= DButil.getSeats();
                    if (ar.size() == 1)
                        variableToSend = String.valueOf(map1);
                    else if (ar.size() == 2) {
                        Map<String, Seat> filtered = map1.entrySet().stream()
                                .filter(map -> map.getValue().getSeatPrice() == 60)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        variableToSend = String.valueOf(filtered);
                    }
                    //TODO: display available seats nicely;

                }
                else if (ar.get(0).equalsIgnoreCase("reserve")) {
                    //TODO: DButil.reserveSeat();
                    DButil.reserveSeat(ar.get(1),ar.get(2),ar.get(4),Integer.parseInt(ar.get(3)));

                    System.out.println(ar);
                    variableToSend = "reserve";
                }
                else if (ar.get(0).equalsIgnoreCase("reserverAnon")) {
                    //TODO: DButil.reserverSeat(true)
                    variableToSend = "reserverAnon";
                }
                //            List<String> ar = Arrays.asList(clientMessage.split(" "));
                //
                //            System.out.println(ar.get(0));
                //            System.out.println(ar.get(1));
                //            System.out.println(ar.get(2));

                //            if (DButil.login(ar.get(1), ar.get(2))) {
                //                outToClient.writeBytes("test34"+"\n");
                //            } else {
                //            Venue venue = new Venue();
                //            final LinkedHashMap seats = venue.getSeats();

                System.out.println("Manipulating client request and preparing response");


                System.out.println("Sending response to the client");

                //Send data to the client using the output text stream.
                try {
                    outToClient.write(variableToSend + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    outToClient.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //The server remains active waiting for incoming connections
        //Each TCP connection originating from a client is terminated by the client itself
    }

}