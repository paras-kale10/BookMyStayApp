/**
 * UseCase9ErrorHandlingValidation
 *
 * This class demonstrates input validation and error handling
 * using custom exceptions to ensure system reliability.
 *
 * @author Paras
 * @version 9.0
 */

import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Inventory Class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        // Prevent negative inventory
        if (available <= 0) {
            throw new InvalidBookingException("No available rooms for: " + type);
        }

        inventory.put(type, available - 1);
    }
}

// Validator Class
class InvalidBookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException(
                    "Room not available: " + r.getRoomType());
        }
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {
        try {
            // Fail-fast validation
            InvalidBookingValidator.validate(r, inventory);

            // If valid, update inventory
            inventory.decrement(r.getRoomType());

            // Confirm booking
            System.out.println("Booking Confirmed for " + r.getGuestName() +
                    " (" + r.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v9.0 =====");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Test cases (valid + invalid)
        Reservation r1 = new Reservation("Alice", "Single Room"); // valid
        Reservation r2 = new Reservation("", "Double Room");      // invalid name
        Reservation r3 = new Reservation("Bob", "Suite Room");    // invalid type
        Reservation r4 = new Reservation("Charlie", "Single Room"); // may fail (no availability)

        service.processReservation(r1);
        service.processReservation(r2);
        service.processReservation(r3);
        service.processReservation(r4);

        System.out.println("\nSystem continues running safely.");
        System.out.println("Application terminated.");
    }
}