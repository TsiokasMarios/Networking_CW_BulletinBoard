import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Venue {
    LinkedHashMap<String,Seat> venue = new LinkedHashMap<>();

    //Venue constructor
//    public Venue() {
//        initVenue(venue); //Calls the initVenue method that creates the venue
//    }

    public Seat getSeat(String seatID) {
        return venue.get(seatID);
    }

//    static void initVenue(Map<String,Seat> venue){
//        //Contains all the alphabet letters to create the rows and assign their IDs
//        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();
//
//        for (char character : alphabet){ //Loop through the alphabet array
//
//            for (int i = 1; i < 21; i++) { //Loop 20 times to create the 20 seats
//
//                //Creates a new seat with the character ID of the row + the integer i (1 through 20) for the seat id
//                //And depending on the character
//                //Sets its price accordingly
//                //And it adds it to the venue which is a linked hash map
//                if (character < 'H'){
//                    venue.put(character + Integer.toString(i),new Seat(character + Integer.toString(i),80));
//                }
//                else if (character < 'P') {
//                    venue.put(character + Integer.toString(i),new Seat(character + Integer.toString(i),60));
//                }
//                else{
//                    venue.put(character + Integer.toString(i),new Seat(character + Integer.toString(i),30));
//                }
//            }
//
//        }
//
//        //Prints out all the key-value pairs
//        venue.forEach((key, value) -> System.out.println(key + " " + value));
//    }

//    public void reserveSeat(String seatID,String custName,int custPhone){
//        //Gets the seat from the hashmap with the seatId that was given
//        Seat seat = venue.get(seatID);
//        //Sets its variable "isAvailable" to false
//        seat.setAvailable(false);
//        //Records the transaction in the database
//        DButil.recordReservation(seatID,custName,custPhone);
//    }

    public void getAvailableSeats(){
        //map-forEach function that displays all the available seats
        venue.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public LinkedHashMap<String, Seat> getSeats(){
        return venue;
    }

    public void getAvailableSeats(int price){
        //iterates through the hashmap
        venue.forEach((key, value) -> {
            //Check if the seat price is the same as the price request
            if (value.getSeatPrice() == price) {
                //Print the seat
                System.out.println(key + " " + value);
            }
        });
    }
}
