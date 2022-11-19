import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.LinkedHashMap;

public class DButil {
    static LinkedHashMap<String,Seat> seats = new LinkedHashMap<>();

    public static Connection createCon() {
        try {
            //Creates the connection
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:sqlite:src/board.db");
            System.out.println("connected");
            return  c;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Connection failed");
            System.exit(0);
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getSeats(){
        try{
            Connection conn = createCon();

            Statement statement = conn.createStatement();
            String query = "select * from seats";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                seats.put(resultSet.getString(1),new Seat(resultSet.getString(1),resultSet.getInt(2),resultSet.getBoolean(3)));
            }
            seats.forEach((key, value) -> System.out.println(key + " " + value));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getSeats(int price){
        try{
        Connection conn = createCon();
        String query = "SELECT * FROM seats WHERE price = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,price);

        ResultSet resultSet = statement.executeQuery();


        while (resultSet.next()){
//            seats.put(resultSet.getString(1),new Seat(resultSet.getString(1),resultSet.getInt(2),resultSet.getBoolean(3)));
            System.out.println(resultSet.getString(1));
        }
//        seats.forEach((key, value) -> System.out.println(key + " " + value));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    public static void initSeats(){

        try {
            //Create the database connection
            Connection conn = createCon();
            //Define the query
            String query = "insert into seats values (?,?,?)";
            //Prepare the staement
            PreparedStatement statement = conn.prepareStatement(query);

            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

            for (char character : alphabet){ //Loop through the alphabet array

                for (int i = 1; i < 21; i++) { //Loop 20 times to create the 20 seats

                    //Creates a new seat with the character ID of the row + the integer i (1 through 20) for the seat id
                    //And depending on the character
                    //Sets its price accordingly
                    //And sets the values for the statement
                    //1 is the seatID
                    //2 is the seatPrice
                    //3 is isAvailable
                    if (character <= 'H'){
                        statement.setString(1,character + Integer.toString(i));
                        statement.setString(2,"80");
                        statement.setBoolean(3,true);
                        statement.addBatch();
                    }
                    else if (character <= 'P') {
                        statement.setString(1,character + Integer.toString(i));
                        statement.setString(2,"60");
                        statement.setBoolean(3,true);
                        statement.addBatch();
                    }
                    else if (character <= 'Z'){
                        statement.setString(1,character + Integer.toString(i));
                        statement.setString(2,"30");
                        statement.setBoolean(3,true);
                        statement.addBatch();
                    }
                }
            }
            statement.executeBatch();
            statement.clearBatch();
            closeConnection(conn);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void register(String username, String password, int phoneNum, String city, String fullName){

        //Create the database connection
        Connection conn = createCon();

        //Create the encryptor to encrypt password
        Encryptor enc = new Encryptor();

        //Create an insert query to insert the values in the agents table
        String query = "insert into agents values (?,?,?,?,?)";

        //Initialize statement variable
        PreparedStatement statement;

        //TODO: Check if username/user already exists in the database


        try {
            //Prepare the statement
            statement = conn.prepareStatement(query);

            //Insert the values we received from the user
            statement.setString(1,username);
            statement.setString(2,enc.encryptString(password)); //Gets the password and encrypts it with the enc object
            statement.setInt(3,phoneNum);
            statement.setString(4,city);
            statement.setString(5,fullName);

            //Executes the statement
            statement.executeUpdate();

            //Closes the database connection
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Boolean login(String username, String password){
        //Create the database connection
        Connection conn = createCon();

        //Create the encryptor to encrypt password
        Encryptor enc = new Encryptor();

        //Initialize the variables
        PreparedStatement statement;
        ResultSet resultSet;

        //Create a select query to check if the username and the password exist in the database
        String query = "select * from agents where username = ? and password = ?";

        try{
            //Prepare the statement
            statement = conn.prepareStatement(query);

            //Insert the values we received from the user
            statement.setString(1,username);
            statement.setString(2,enc.encryptString(password)); //Gets the password and encrypts it with the enc object

            //Executes the statement
            resultSet = statement.executeQuery();

            if (resultSet.next()){ //Checks if there are any records that match the data in the database with the data we got
                System.out.println("Poggers, logged in");
                conn.close();
                return true;
            }
            else {
                System.out.println("Something went wrong");
                conn.close();
                return false;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void recordReservation(String seatID,String custName,int custPhone){
        //Create the database connection
        Connection conn = createCon();

        //Create an insert query to insert the values in the agents table
        String query = "insert into seatReservation values (?,?,?)";

        //Initialize statement variable
        PreparedStatement statement;



        try {
            //Prepare the statement
            statement = conn.prepareStatement(query);

            //Insert the values we received from the user
            statement.setString(1,seatID);
            statement.setString(2,custName);
            statement.setInt(3,custPhone);
            //Executes the statement
            statement.executeUpdate();

            //Closes the database connection
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

