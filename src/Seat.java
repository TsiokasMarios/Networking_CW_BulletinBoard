public class Seat {
    private String seatId;
    private int seatPrice;
    private boolean isAvailable;

    public Seat(String seatId, int seatPrice, Boolean isAvailable) {
        this.seatId = seatId;
        this.seatPrice = seatPrice;
        this.isAvailable = isAvailable;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public int getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(int seatPrice) {
        this.seatPrice = seatPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", seatPrice" + seatPrice +
                ", isAvailable" + isAvailable +
                '}';
    }
}
