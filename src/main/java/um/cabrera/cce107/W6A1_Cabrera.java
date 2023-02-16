package um.cabrera.cce107;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class W6A1_Cabrera {
    public static void main(String[] args) {
        Scanner InterfaceInput = new Scanner(System.in);
        // Initialize Items in the Store
        StoreItems[] items = {
                new StoreItems("AMD RX 5600", 11000),
                new StoreItems("AMD RX 5600XT", 13500),
                new StoreItems("AMD RX 5700", 14000),
                new StoreItems("AMD RX 5700XT", 14500),
                new StoreItems("AMD RX 6600", 15750),
                new StoreItems("AMD RX 6600XT", 16800),
                new StoreItems("NVIDIA RTX 2060", 15000),
                new StoreItems("NVIDIA RTX 2070", 16500),
                new StoreItems("NVIDIA RTX 2080", 17300),
                new StoreItems("NVIDIA RTX 3050", 18500),
                new StoreItems("NVIDIA RTX 3060", 23000),
                new StoreItems("NVIDIA RTX 3060ti", 27000) ,
                new StoreItems("NVIDIA RTX 4090", 58000) ,
                new StoreItems("NVIDIA RTX 4090ti", 73500)
        };
        // Input the initialized items for the TechStore
        ComputerShop TechStore = new ComputerShop(items);
        // This will display the items that are available
        TechStore.displayItems();
        // Initialize the Cart
        ArrayList<Integer> itemCart = new ArrayList<>();
        // For Order Number
        Random randomizer = new Random();

        System.out.println("Instructions: 0 to Proceed to Checkout. Item number to Add to Cart.\n");
        boolean menuSession = true;
        while (menuSession) {
            System.out.print("Item Number: ");
            int x = InterfaceInput.nextInt();
            if (x == 0) {
                menuSession = false;
            } else {
                itemCart.add(x);
            }
        }

        // Compute the Total Price of the Item Cart
        double totalPrice = TechStore.purchaseItems(itemCart);
        System.out.println("Total Price: Php " + totalPrice);
        System.out.println();

        // Pay using the Credit Card
        System.out.print("Enter Credit Card Number: ");
        int sessionCNumber = InterfaceInput.nextInt();
        System.out.println();

        System.out.print("Enter Credit Card Name: ");
        String sessionCHolder = InterfaceInput.next();
        System.out.println();

        System.out.print("Enter Credit Card Expiry: ");
        String sessionCExpiry = InterfaceInput.next();
        System.out.println();

        System.out.println(
                """
                [0] - Basic
                [1] - Silver
                [2] - Gold
                [3] - Platinum
                [4] - Diamond
                """);

        System.out.print("Enter Credit Card Type: ");
        int sessionCLimitDetermine = InterfaceInput.nextInt();
        System.out.println();

        int sessionCLimit = 0;
        switch (sessionCLimitDetermine) {
            // I used enhanced switch for readability.
            case 0 -> sessionCLimit = 20000;
            case 1 -> sessionCLimit = 50000;
            case 2 -> sessionCLimit = 100000;
            case 3 -> sessionCLimit = 500000;
            case 4 -> sessionCLimit = 1000000;
            default -> {
                System.out.println("Invalid Input!");
                System.exit(404);
            }
        }

        CreditCard card = new CreditCard(sessionCNumber, sessionCHolder, sessionCExpiry, sessionCLimit);

        System.out.println("----------- Credit Card Information -----------");
        System.out.println("Name: " + card.getCHolderName());
        System.out.println("Credit Number: " + card.getCNumber());
        System.out.println("Expiry Date: " + card.getCExpirationDate());
        System.out.println("Balance Limit: Php " + card.getCreditLimit());
        System.out.println("-----------------------------------------------");

        System.out.println();

        System.out.println("----------- Purchase Receipt -----------");
        if (totalPrice > card.getCreditLimit()) {
            System.out.println("Transaction declined. Insufficient credit limit.");
        } else {
            System.out.println("Order Number: " + randomizer.nextInt());
            System.out.println("Transaction successful!");
            System.out.println("Total Price: Php " + totalPrice);
            card.makePayment(totalPrice);
            System.out.println("Remaining balance: Php " + card.getRemainingBalance());
        }
        System.out.println("----------------------------------------");
    }
}

class StoreItems {
    String itemName;
    double itemPrice;

    public StoreItems(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}

class ComputerShop {
    StoreItems[] items;
    public ComputerShop(StoreItems[] items) {
        this.items = items;
    }

    public void displayItems() {
        System.out.println("Welcome to Davao Futurebright Enterprise! Here are our available GPUs:");
        for (int i = 0; i < items.length; i++) {
            System.out.println((i+1) + ". " + items[i].itemName + " - Php " + items[i].itemPrice);
        }
    }

    public double purchaseItems(ArrayList<Integer> itemNumbers) {
        double totalPrice = 0;
        // I used enhanced for loop for efficiency
        for (Integer itemNumber : itemNumbers) {
            totalPrice += items[itemNumber - 1].itemPrice;
        }
        return totalPrice;
    }
}

class CreditCard {
    private int CNumber;
    private String CHolderName;
    private String CExpirationDate;
    private int creditLimit;
    private double remainingBalance;

    public CreditCard(int CNumber, String CHolderName, String CExpirationDate, int creditLimit) {
        this.CNumber = CNumber;
        this.CHolderName = CHolderName;
        this.CExpirationDate = CExpirationDate;
        this.creditLimit = creditLimit;
        this.remainingBalance = creditLimit;
    }

    public int getCNumber() {
        return CNumber;
    }

    public String getCHolderName() {
        return CHolderName;
    }

    public String getCExpirationDate() {
        return CExpirationDate;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void makePayment(double amount) {
        remainingBalance -= amount;
    }
}

