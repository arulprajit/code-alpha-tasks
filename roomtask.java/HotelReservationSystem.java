import java.io.*;
import java.util.*;

// Room Class
class Room {
    int roomNumber;
    String category;
    boolean isAvailable;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }

    void displayRoom() {
        System.out.println("Room No: " + roomNumber +
                " | Category: " + category +
                " | Available: " + isAvailable);
    }
}

// Booking Class
class Booking {
    String customerName;
    int roomNumber;
    String category;
    double payment;

    Booking(String customerName, int roomNumber, String category, double payment) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return customerName + "," + roomNumber + "," + category + "," + payment;
    }
}

// Hotel Reservation System
public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {

        // Adding Rooms
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelReservation();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);
    }

    // View Rooms
    static void viewRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            r.displayRoom();
        }
    }

    // Book Room
    static void bookRoom() {
        System.out.print("Enter Customer Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo) {

                if (r.isAvailable) {

                    double payment = getPayment(r.category);

                    r.isAvailable = false;

                    Booking booking = new Booking(name, roomNo, r.category, payment);

                    saveBooking(booking);

                    System.out.println("Room Booked Successfully!");
                    System.out.println("Payment Received: Rs." + payment);

                } else {
                    System.out.println("Room Already Booked!");
                }

                return;
            }
        }

        System.out.println("Room Not Found!");
    }

    // Payment Simulation
    static double getPayment(String category) {

        switch (category) {
            case "Standard":
                return 2000;

            case "Deluxe":
                return 4000;

            case "Suite":
                return 7000;

            default:
                return 0;
        }
    }

    // Save Booking to File
    static void saveBooking(Booking booking) {

        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(booking.toString() + "\n");
            fw.close();

        } catch (IOException e) {
            System.out.println("Error Saving Booking!");
        }
    }

    // View Bookings
    static void viewBookings() {

        System.out.println("\n--- Booking Details ---");

        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                System.out.println("No Bookings Found!");
                return;
            }

            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                System.out.println(fileReader.nextLine());
            }

            fileReader.close();

        } catch (Exception e) {
            System.out.println("Error Reading File!");
        }
    }

    // Cancel Reservation
    static void cancelReservation() {

        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();

        boolean found = false;

        for (Room r : rooms) {

            if (r.roomNumber == roomNo) {

                if (!r.isAvailable) {
                    r.isAvailable = true;
                    found = true;
                    System.out.println("Reservation Cancelled Successfully!");
                } else {
                    System.out.println("Room is already available!");
                }
            }
        }

        if (!found) {
            System.out.println("Booking Not Found!");
        }
    }
}
