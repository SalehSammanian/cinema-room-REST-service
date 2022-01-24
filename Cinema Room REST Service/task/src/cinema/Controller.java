package cinema;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
public class Controller {

    private volatile Theatre theatre = new Theatre();

    @GetMapping("/seats")
    public Theatre getSeats() {
        return theatre;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody Seat seat) {
        if (seat.row > 9 || seat.column > 9 || seat.row < 1 || seat.column < 1) {
            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        Seat yourSeat = theatre.getSeat(seat.row, seat.column);

        if (yourSeat != null && yourSeat.isAvailable()) {
            UUID uuid = UUID.randomUUID();
            yourSeat.setToken(uuid);
            yourSeat.setAvailable(false);
            Ticket ticket = new Ticket(yourSeat, uuid);
            theatre.putTicket(uuid, ticket);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } else {
            return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Map> returnTicket(@RequestBody Ticket ticket) {
        if(theatre.ticketExists(ticket.getToken())) {
            Ticket t = theatre.getTicket(ticket.getToken());
            Seat seat = t.getSeat();
            theatre.deleteTicket(t.token);
            seat.setAvailable(true);
            seat.setToken(null);
            return new ResponseEntity<>(Map.of("returned_ticket", seat), HttpStatus.OK);
        }

        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<TheatreStats> getStats(@RequestParam(value = "password", required = false) String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        TheatreStats stats = new TheatreStats(theatre);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }


}
