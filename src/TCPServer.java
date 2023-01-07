import java.io.*;
import java.net.*;

public class TCPServer
{
    /* This is the port on which the server is running */
    private int serverPort;

    /* Constructor Method */
    public TCPServer( int port )
    {
        serverPort = port;
    }  /* End Contructor Method */

    /* Listen Method */
    public void listen()
    {
        /* Socket for connections made */
        Socket connectionSocket;

        /* Server's listening socket */
        ServerSocket welcomeSocket;

        // Create a socket to listen on.
        try {
            welcomeSocket = new ServerSocket( serverPort );
        }
        catch (IOException e) {
            System.out.println("Could not use server port " + serverPort);
            return;
        }

        // Listen forever for new connections.  When a new connection
        // is made a new socket is created to handle it.
        while ( true )
        {
            System.out.println("<-- Server listening on socket " + serverPort + " -->");
            /* Try and accept the connection */
            try {
                connectionSocket = welcomeSocket.accept();
            }
            catch (IOException e) {
                System.out.println("Error accepting connection.");
                continue;
            }

            /* A connection was made successfully */
            System.out.println("<-- Made connection on server socket -->");

            /* Create a thread to handle it. */
            handleClient( connectionSocket );
        }
    }  /* End listen method */

    public void handleClient(Socket clientConnectionSocket)
    {
        System.out.println("<-- Starting thread to handle connection -->");
        new Thread(new TCPConHandler(clientConnectionSocket)).start();
    }  /* End handleClient method */

    public static void main(String[] args) {
        /* The port the server is listening on */

        /* Parse the port which is passed to program as an arguement */
//        port = Integer.parseInt( argv[0] );

        /* Create a new instance of the echo server */
        TCPServer myServer = new TCPServer( 8504 );
        /* Listen for connections. It can not return until the server is shut down. */
        myServer.listen();
        /* Display message of server shutting down */
        System.out.println("<-- Server exiting -->");
    }   /* End main method */
}


