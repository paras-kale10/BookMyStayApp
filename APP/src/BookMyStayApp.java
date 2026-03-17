]/**
 * UseCase5BookingRequestQueue
 *
 * This class demonstrates booking request intake using a Queue
 * to ensure FIFO (First-Come-First-Served) behavior.
 *
 * @author Paras
 * @version 5.0
 */

import java.util.*;

// Reservation Class (represents a booking request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void display() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType);
    }
}

// Booking Queue Class
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Request added: " + r.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\n--- Booking Requests Queue ---");
        for (Reservation r : queue) {
            r.display();
        }
    }

    // Get next request (peek only, no removal)
    public Reservation getNextRequest() {
        return queue.peek();
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v5.0 =====");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display all queued requests
        bookingQueue.viewRequests();

        // Show next request to be processed (FIFO)
        Reservation next = bookingQueue.getNextRequest();
        System.out.println("\nNext Request to Process:");
        if (next != null) {
            next.display();
        }

        System.out.println("\nNo inventory changes performed.");
        System.out.println("Application terminated.");
    }
}