/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates persistence using serialization and recovery using deserialization.
 * Ensures booking and inventory state survives application restarts.
 *
 * @author Paras
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// Reservation Class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Inventory Class (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Wrapper Class for Full System State
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookings;
    RoomInventory inventory;

    public SystemState(List<Reservation> bookings, RoomInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("\nState saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("State loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No saved state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }
        return null;
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v12.0 =====");

        // Try loading previous state
        SystemState state = PersistenceService.load();

        List<Reservation> bookings;
        RoomInventory inventory;

        if (state != null) {
            bookings = state.bookings;
            inventory = state.inventory;
        } else {
            // Fresh start
            bookings = new ArrayList<>();
            inventory = new RoomInventory();
        }

        // Display current state
        inventory.display();

        System.out.println("\n--- Booking History ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Reservation r : bookings) {
                System.out.println(r);
            }
        }

        // Simulate new booking
        Reservation newBooking = new Reservation("SI101", "Alice", "Single Room");
        bookings.add(newBooking);
        System.out.println("\nNew Booking Added: " + newBooking);

        // Save state before shutdown
        SystemState newState = new SystemState(bookings, inventory);
        PersistenceService.save(newState);

        System.out.println("Application terminated.");
    }
}