import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class main {


    public static void main(String[] args) {

//        Venue venue = new Venue();

//        System.out.println(venue.getSeat("X8"));

//        DButil.initSeats();
//        DButil.getSeats();
//        LinkedHashMap<String,Seat> map1;
//
//        Map<String, Seat> collect = map1.entrySet().stream()
//                .filter(map -> map.getValue().getSeatPrice() == 60)
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

//        System.out.println("Filtered map \n " + collect);
//        DButil.register("Bob",8484,"wah","Timythy tim");
//        System.out.println(DButil.checkIfUserExists("Tim"));
        DButil.reserveSeat("A3","tim",6481712);

//        venue.getAvailableSeats();

//        String username;
//        String password;
//        int phoneNum;
//        String city;
//        String fullName;
//
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("enter username");
//        username = sc.nextLine();
////
//        System.out.println("enter password");
//        password = sc.nextLine();
//
//        System.out.println("enter phone num");
//        phoneNum = sc.nextInt();
//        sc.nextLine();
//
//        System.out.println("enter city");
//        city = sc.nextLine();

//        System.out.println("enter full name");
//        fullName = sc.nextLine();

//        System.out.println("username: " + username + "\n password: " + password + "\n phone num: " + phoneNum + "\n city: " + city + "\n full name: " + fullName);

//        DButil.register(username,password,phoneNum,city,fullName);
//        DButil.login(username,password);

    }
}

