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
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized room inventory management
 * using HashMap as a single source of truth.
 *
 * @author Paras
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
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
    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled method)
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

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
        System.out.println("===== Hotel Booking System v3.1 =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Access availability (O(1) lookup)
        System.out.println("\nSingle Room Available: " +
                inventory.getAvailability("Single Room"));

        // Update availability (controlled)
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}