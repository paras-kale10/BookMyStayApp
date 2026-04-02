/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe booking using synchronized methods
 * to prevent race conditions and double booking.
 *
 * @author Paras
 * @version 11.0
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

// Thread-Safe Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Added: " + r.getGuestName());
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Thread-Safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // Critical section: allocation
    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public synchronized int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
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

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {

            Reservation r = queue.getRequest(); // already synchronized

            if (r == null) break;

            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(getName() + " CONFIRMED: " + r.getGuestName());
            } else {
                System.out.println(getName() + " FAILED: " + r.getGuestName());
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

        System.out.println("===== Hotel Booking System v11.0 =====");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Simulate concurrent booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // Create threads
        BookingProcessor t1 = new BookingProcessor("Thread-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        System.out.println("\nFinal Availability: " +
                inventory.getAvailability("Single Room"));

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