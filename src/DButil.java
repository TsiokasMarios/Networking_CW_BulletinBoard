import java.sql.*;

public class DButil {
    public static Connection createCon() {
        try {
            //Creates the connection
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://130.61.239.202:5432/testdb"
                            , "postgres"
                            , "177014");
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

