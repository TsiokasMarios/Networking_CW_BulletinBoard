import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TCPConHandler implements Runnable {

    //Endpoint socket
    protected Socket echoSocket;

    public TCPConHandler(Socket socketToHandle) {
        echoSocket = socketToHandle;
    }


    private Boolean register(List<String> messageList){
        return DButil.register(messageList.get(1), Integer.parseInt(messageList.get(2)), messageList.get(3), messageList.get(4)+messageList.get(5));
    }

    private Boolean login(List<String> messageList){
        return DButil.checkIfUserExists(messageList.get(1));
    }

    //This method returns an int
    //If the insert query is successful it returns 1
    //Otherwise it returns 0
    private int reserveSeat(List<String> messageList){
        return DButil.reserveSeat(messageList.get(2), messageList.get(1), Integer.parseInt(messageList.get(3)));
    }



    public void run() {

        //Stores the messages we get from the user
        String clientMessage;

        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        BufferedReader inFromClient;
        BufferedWriter outToClient;

        String peerName;

        System.out.println("Starting the server application");

        // Attach a println/readLine interface to the socket, so we can read and write strings to the socket.
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
        } catch (IOException e) {
            System.out.println("Error creating buffered handles.");
            return;
        }


        System.out.println("Waiting for incoming requests");

        //Create a new socket named connectionSocket and assigning to it an incoming connection request to the welcomeSocket.
        //A tunnel is automatically established between the server and the client using the connectionSocket socket.
        //Next, the welcome socket is released.

        System.out.println("Establishing an incoming request");

        System.out.println("Defined input text stream from the client and output text stream to the client");

        System.out.println("Reading data from the client");

        //Declare the messagelist and action variables
        List<String> messageList;
        String action;

        //Consume the input from the client.
        //Read a text string until the new line character.
        while (true) {
            try {
                clientMessage = inFromClient.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
            String variableToSend = "";


            //If we received no message it means the client closed the connection
            if (clientMessage == null){
                System.out.println( "Closing connection with " + echoSocket.getInetAddress() + "." );
                break;
            }

            //Array that stores the messages from the client
            messageList = Arrays.asList(clientMessage.split(" "));
            //Gets the first element from the messageList which indicates the action the client took
            action = messageList.get(0).toLowerCase();


            switch (action) {
                case "register":
                    if (register(messageList)) {
                        variableToSend = "Registered successfully";
                    } else {
                        variableToSend = "Something went wrong.Please try again";
                    }
                    break;
                case "login":
                    if (login(messageList)) {
                        variableToSend = "Logged in with name: " + messageList.get(1);
                    } else {
                        variableToSend = "Wrong name";
                    }
                    break;
                case "getavailableseats":
                    //Call the function from the DButil class to get all the available seats
                    LinkedHashMap<String, Seat> map1 = DButil.getSeats();
                    if (map1.isEmpty()) { //If its empty it means there are no available seats
                        variableToSend = "No available seats";
                    }
                    //If the messageList has a size of 1 it means the user did not ask for the seats per price range
                    //Size = 1 get available seats
                    //Size = 2 get available seats per price
                    else if (messageList.size() == 1) {
                        variableToSend = String.valueOf(map1);
                    }
                    else if (messageList.size() == 2) {
                        int price = Integer.parseInt(messageList.get(1));

                        //Make sure the received price to search for is valid
                        if (price == 30 || price == 60 || price == 80) {
                            Map<String, Seat> filtered = map1.entrySet().stream() //Convert the map to a stream
                                    .filter(map -> map.getValue().getSeatPrice() == price) //Filter the map using the seat price with the value we got
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); //Convert it back to a map
                            variableToSend = String.valueOf(filtered); //Converts the map to a string
                        }
                        else {
                            variableToSend = "Wrong price tag given. Seats come in the prices of: 30, 60 or 90";
                        }

                    }
                    break;
                case "reserveaseat":
                    if (reserveSeat(messageList) == 0) {
                        variableToSend = "Something went wrong with the reservation";
                    } else {
                        variableToSend = "Reservation successful";
                    }
                    break;
            }

            System.out.println("Manipulating client request and preparing response");


            System.out.println("Sending response to the client");

            //Send data to the client using the output text stream.
            try {
                outToClient.write(variableToSend + "\n");
                outToClient.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println("Closing " + peerName + " connection");
        // Close all the handles

        try {
            /* Close input stream */
            inFromClient.close();
            /* Close output stream */
            outToClient.close();
            /* Close TCP connection with client on specific port */
            echoSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //The server remains active waiting for incoming connections
        //Each TCP connection originating from a client is terminated by the client itself
    }


}