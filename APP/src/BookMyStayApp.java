/**
 * UseCase7AddOnServiceSelection
 *
 * This class demonstrates adding optional services to reservations
 * using Map and List without modifying booking or inventory logic.
 *
 * @author Paras
 * @version 7.0
 */

import java.util.*;

// Service Class (Add-On)
class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

// Manager Class
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName() +
                " to Reservation: " + reservationId);
    }

    // View services for reservation
    public void viewServices(String reservationId) {
        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getPrice() + ")");
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;
        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }
        return total;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Hotel Booking System v7.0 =====");

        String res1 = "SI1";
        String res2 = "DO2";

        AddOnServiceManager manager = new AddOnServiceManager();

        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1500);

        manager.addService(res1, wifi);
        manager.addService(res1, breakfast);

        manager.addService(res2, spa);

        manager.viewServices(res1);
        manager.viewServices(res2);

        // Calculate cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));

        System.out.println("\nCore booking and inventory remain unchanged.");
        System.out.println("Application terminated.");
    }
}