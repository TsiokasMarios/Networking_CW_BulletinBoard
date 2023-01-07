import java.sql.*;
import java.util.LinkedHashMap;

public class DButil {
    static LinkedHashMap<String, Seat> seats = new LinkedHashMap<>();

    public static Connection createCon() {
        try {
            //Creates the connection
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:sqlite:board.db");
            return c;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Connection failed");
            System.exit(0);
            return null;
        }
    }

    public static LinkedHashMap<String, Seat> getSeats() {
        try {
            Connection conn = createCon();

            Statement statement = conn.createStatement();
            //We retrieve from the table seats, and we only get those which isAvailable is 1 which in our case means available
            String query = "select * from seats where isAvailable = 1";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                seats.put(resultSet.getString(1), new Seat(resultSet.getString(1), resultSet.getInt(2), resultSet.getBoolean(3)));
            }
            conn.close();
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static int reserveSeat(String seatID, String custName, int custPhone) {
        int isSuccessful = 0;
        try {
            Connection conn = createCon();
            String query = "INSERT INTO seatReservation (seatID,custName,custPhone) values (?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, seatID);
            statement.setString(2, custName);
            statement.setInt(3, custPhone);

            statement.executeUpdate();
            statement.clearParameters();

            query = "update seats set isAvailable = ? where id = ?";

            statement = conn.prepareStatement(query);
            statement.setInt(1, 0);
            statement.setString(2, seatID);

            isSuccessful = statement.executeUpdate();
            statement.clearParameters();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    public static Boolean register(String username, int phoneNum, String city, String fullName) {

        if (checkIfUserExists(username)){
            return false;
        }
        //Create the database connection
        Connection conn = createCon();

        //Create an insert query to insert the values in the agents table
        String query = "insert into agents values (?,?,?,?)";

        //Initialize statement variable
        PreparedStatement statement;

        try {
            //Prepare the statement
            statement = conn.prepareStatement(query);

            //Insert the values we received from the user
            statement.setString(1, username);
            statement.setInt(2, phoneNum);
            statement.setString(3, city);
            statement.setString(4, fullName);

            //Executes the statement
            statement.executeUpdate();

            //Closes the database connection
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkIfUserExists(String username) {
        Connection conn = createCon();
        PreparedStatement stmt;
        ResultSet rs;
        try {
            String query = "SELECT * FROM agents WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                conn.close();
                return true;
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }
}

