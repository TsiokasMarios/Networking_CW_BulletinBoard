import java.util.ArrayList;
import java.util.List;

public class Row {
    private char RowID;
    private List<Seat> seats = new ArrayList<>();

    public Row(char rowID) {
        RowID = rowID;
    }



    public char getRowID() {
        return RowID;
    }

    public List<Seat> getSeats() {
        return seats;
    }


    @Override
    public String toString() {
        return "Row{" +
                "RowID=" + RowID +
                ", seats=" + seats +
                '}';
    }
}
