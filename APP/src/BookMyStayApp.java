/**
 * UseCase4RoomSearch
 *
 * This class demonstrates read-only room search functionality
 * using centralized inventory without modifying system state.
 *
 * @author Paras
 * @version 4.0
 */

import java.util.*;

// Abstract Room class
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }

    public abstract void displayDetails();
}

// Concrete Rooms
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 2000); }

    public void displayDetails() {
        System.out.println("Type: " + getType() +
                ", Beds: " + getBeds() +
                ", Price: ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 3500); }

    public void displayDetails() {
        System.out.println("Type: " + getType() +
                ", Beds: " + getBeds() +
                ", Price: ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 6000); }

    public void displayDetails() {
        System.out.println("Type: " + getType() +
                ", Beds: " + getBeds() +
                ", Price: ₹" + getPrice());
    }
}

// Inventory (Read-only usage here)
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// Search Service (Read-only)
class RoomSearchService {

    private RoomInventory inventory;
    private List<Room> rooms;

    public RoomSearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main Class
public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v4.0 =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        // Perform search (read-only)
        searchService.searchAvailableRooms();

        System.out.println("Application terminated.");
    }
}
