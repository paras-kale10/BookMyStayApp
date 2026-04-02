/**
 * UseCase11ConcurrentBookingSimulation
 *
 * This class demonstrates thread-safe booking using synchronization
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

// Thread-Safe Booking Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println(Thread.currentThread().getName() +
                " added request: " + r.getGuestName());
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// Thread-Safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

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
    }
}

// Booking Processor (Runnable)
class BookingProcessor implements Runnable {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            // Critical section: fetch request
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            // Critical section: allocate room
            synchronized (inventory) {
                boolean success = inventory.allocateRoom(r.getRoomType());

                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " CONFIRMED booking for " + r.getGuestName());
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " FAILED booking for " + r.getGuestName());
                }
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v11.0 =====");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Simulate concurrent requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // Create multiple threads
        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");

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

        System.out.println("\nFinal Availability: " +
                inventory.getAvailability("Single Room"));

        System.out.println("Application terminated.");
    }
}