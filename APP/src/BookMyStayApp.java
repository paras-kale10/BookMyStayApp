/**
 * UseCase8BookingHistoryReport
 *
 * This class demonstrates booking history tracking and reporting
 * using List to maintain ordered records of confirmed reservations.
 *
 * @author Paras
 * @version 8.0
 */

import java.util.*;

// Reservation Class (Confirmed Booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType);
    }
}

// Booking History (Storage)
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\n--- Booking History ---");

        for (Reservation r : history.getAllReservations()) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary() {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n--- Booking Summary Report ---");
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v8.0 =====");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("SI1", "Alice", "Single Room"));
        history.addReservation(new Reservation("SI2", "Bob", "Single Room"));
        history.addReservation(new Reservation("DO3", "Charlie", "Double Room"));

        // Initialize report service
        BookingReportService reportService = new BookingReportService(history);

        // Display booking history
        reportService.displayAllBookings();

        // Generate summary
        reportService.generateSummary();

        System.out.println("\nReporting completed (no data modified).");
        System.out.println("Application terminated.");
    }
}