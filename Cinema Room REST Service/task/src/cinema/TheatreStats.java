package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TheatreStats {
    @JsonProperty("current_income")
    private int currentIncome;

    @JsonProperty("number_of_available_seats")
    private int numOfAvailableSeats;

    @JsonProperty("number_of_purchased_tickets")
    private int numOfPurchasedTickets;

    private Theatre theatre;

    public TheatreStats(Theatre theatre) {
        this.theatre = theatre;
        this.currentIncome = theatre.getIncome();
        this.numOfAvailableSeats = theatre.getAvailableSeats().size();
        this.numOfPurchasedTickets = theatre.getSoldTickets().size();
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getNumOfAvailableSeats() {
        return numOfAvailableSeats;
    }

    public void setNumOfAvailableSeats(int numOfAvailableSeats) {
        this.numOfAvailableSeats = numOfAvailableSeats;
    }

    public int getNumOfPurchasedTickets() {
        return numOfPurchasedTickets;
    }

    public void setNumOfPurchasedTickets(int numOfPurchasedTickets) {
        this.numOfPurchasedTickets = numOfPurchasedTickets;
    }
}
