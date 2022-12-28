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

    public static LinkedHashMap getSeats(){
        try{
            Connection conn = createCon();

            Statement statement = conn.createStatement();
            //We retrieve from the table seats, and we only get those which isAvailable is 1 which in our case means available
            String query = "select * from seats where isAvailable = 1";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                seats.put(resultSet.getString(1),new Seat(resultSet.getString(1),resultSet.getInt(2),resultSet.getBoolean(3)));
            }
            seats.forEach((key, value) -> System.out.println(key + " " + value));
            closeConnection(conn);
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void reserveSeat(String clientUsername, String seatID, String custName, int custPhone ){
        try{
            Connection conn = createCon();
            String query = "INSERT INTO seatReservation (seatID,custName,custPhone) values (?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,seatID);
            statement.setString(2,custName);
            statement.setInt(3,custPhone);

            statement.executeQuery();
            statement.clearParameters();

            query = "update seats set isAvailable = ? where seatId = ?";

            statement = conn.prepareStatement(query);
            statement.setInt(1,0);
            statement.setString(2,seatID);

            statement.executeQuery();
            statement.clearParameters();
            closeConnection(conn);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static LinkedHashMap getSeats(int price){
        try{
            System.out.println("connected, getting available seats");
            Connection conn = createCon();
            String query = "SELECT * FROM seats WHERE price = ? and isAvailable = 1 ORDER BY price";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,price);

            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()){
                seats.put(resultSet.getString(1),new Seat(resultSet.getString(1),resultSet.getInt(2),resultSet.getBoolean(3)));
            }
            closeConnection(conn);
//            seats.forEach((key, value) -> System.out.println(key + " " + value));
            return seats;
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
            //Prepare the statement
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

    public static Boolean register(String username, int phoneNum, String city, String fullName){

        //Create the database connection
        Connection conn = createCon();

        //Create the encryptor to encrypt password

        //Create an insert query to insert the values in the agents table
        String query = "insert into agents values (?,?,?,?)";

        //Initialize statement variable
        PreparedStatement statement;

        //TODO: Check if username/user already exists in the database


        try {
            //Prepare the statement
            statement = conn.prepareStatement(query);

            //Insert the values we received from the user
            statement.setString(1,username);
//            statement.setString(2,enc.encryptString(password)); //Gets the password and encrypts it with the enc object
            statement.setInt(2,phoneNum);
            statement.setString(3,city);
            statement.setString(4,fullName);

            //Executes the statement
            statement.executeUpdate();

            //Closes the database connection
            conn.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    public static boolean isUserExists(String username) {
        Connection conn = createCon();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM agents WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

