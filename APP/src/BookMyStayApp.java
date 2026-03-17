/**
 * UseCase6RoomAllocationService
 *
 * This class demonstrates booking confirmation and safe room allocation
 * using Queue, HashMap, and Set to prevent double-booking.
 *
 * @author Paras
 * @version 6.0
 */

import java.util.*;

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

// Booking Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // dequeue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per type
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness
    private Set<String> allRoomIds = new HashSet<>();

    private int idCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Generate unique room ID
    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 2).toUpperCase() + idCounter++;
        } while (allRoomIds.contains(id));

        return id;
    }

    // Process booking request
    public void processReservation(Reservation r) {

        String type = r.getRoomType();

        // Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("Booking Failed for " + r.getGuestName() +
                    " (No " + type + " available)");
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Store allocation
        allocatedRooms.putIfAbsent(type, new HashSet<>());
        allocatedRooms.get(type).add(roomId);
        allRoomIds.add(roomId);

        // Update inventory (atomic step)
        inventory.decrement(type);

        // Confirm booking
        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + type);
        System.out.println("Room ID: " + roomId + "\n");
    }
}

// Main Class
public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v6.0 =====");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService service = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Double Room"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            service.processReservation(r);
        }

        System.out.println("Application terminated.");
    }
}