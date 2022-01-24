package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Theatre {
    private List<Seat> seats;

    private Map<UUID, Ticket> soldTickets = new ConcurrentHashMap<>();

    @JsonProperty("available_seats")
    private List<Seat> availableSeats;

    private int income;

    @JsonProperty("total_rows")
    private int totalRows = 9;

    @JsonProperty("total_columns")
    private int totalColumns = 9;

    public Theatre() {
        this.seats = generateSeats();
        this.availableSeats = seats;
        this.income = 0;
    }


    public CopyOnWriteArrayList<Seat> generateSeats() {
        List<Seat> available_seats = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                available_seats.add(new Seat(i, j));
            }
        }
        return (CopyOnWriteArrayList<Seat>) available_seats;
    }

    public Seat getSeat(int row, int column) {
        for (Seat seat: seats) {
            if (seat.getRow() == row && seat.getColumn() == column) {
                return seat;
            }
        }
        return null;
    }


    public Ticket getTicket(UUID token) {
        if(!soldTickets.containsKey(token)) {
            return null;
        }
        return soldTickets.get(token);
    }

    public void putTicket(UUID token, Ticket ticket) {
        availableSeats.remove(ticket.getSeat());
        income += ticket.getSeat().getPrice();
        this.soldTickets.put(token, ticket);
    }

    public void deleteTicket(UUID token) {
        Ticket ticket = soldTickets.get(token);
        availableSeats.add(ticket.getSeat());
        income -= ticket.getSeat().getPrice();
        this.soldTickets.remove(token);
    }

    public boolean ticketExists (UUID token) {
        return soldTickets.containsKey(token);
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    @JsonIgnore
    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    @JsonIgnore
    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @JsonIgnore
    public Map<UUID, Ticket> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Map<UUID, Ticket> soldTickets) {
        this.soldTickets = soldTickets;
    }
}
