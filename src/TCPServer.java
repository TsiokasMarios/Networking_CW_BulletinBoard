import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        String clientMessage;
        int port = 6999;
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        BufferedReader inFromClient;
        BufferedWriter outToClient;

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
            inputStreamReader = new InputStreamReader(connectionSocket.getInputStream());

            //Output text stream to send data to the client.
            outputStreamWriter = new OutputStreamWriter(connectionSocket.getOutputStream());

            inFromClient = new BufferedReader(inputStreamReader);
            outToClient = new BufferedWriter(outputStreamWriter);

            System.out.println("Defined input text stream from the client and output text stream to the client");

            System.out.println("Reading data from the client");

            //Consume the input from the client.
            //Read a text string until the new line character.
            while (true) {
                clientMessage = inFromClient.readLine();

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
                outToClient.write(variableToSend + "\n");
                outToClient.flush();
            }
        }

        //The server remains active waiting for incoming connections
        //Each TCP connection originating from a client is terminated by the client itself
    }

}