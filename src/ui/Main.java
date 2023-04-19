package ui;
import model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
public class Main {
    static VirtualShop vs = new VirtualShop();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int sel = 0;
        boolean exit = false;
        while(!exit){
            System.out.println("-----MERCADO LIBRE----- \n 1. Inventory \n 2. Orders" );
            sel = sc.nextInt();
            sc.nextLine();
            switch(sel){
                case 1: inventory(sc); break;
                case 2: orders(sc); break;
                default: throw new IllegalArgumentException("Enter a valid option");
            }
        }
        sc.close();
    }

    private static void inventory(Scanner sc) {
        System.out.println("-----MERCADO LIBRE INVENTORY----- \n 1. Register new product \n 2. Delete product");
        int sel = sc.nextInt();
        sc.nextLine();
        switch(sel) {
            case 1:
                registerNewProduct(sc);
                break;
            case 2:
                deleteProduct(sc);
                break;
            default:
                throw new IllegalArgumentException("Invalid option");
        }
    }

    private static void registerNewProduct(Scanner sc)  {
        String nameProduct = "";
        String description = "";
        int price = 0;
        int quantity = 0;
        int category = 0;
        int purchasedNumber = 0;
        System.out.println("-----REGISTER NEW PRODUCT-----");
        System.out.println("Type the name of the product: ");
        nameProduct = sc.nextLine();
        System.out.println("Type the description of the product: "+nameProduct);
        description = sc.nextLine();
        System.out.println("Type the price of the product: "+nameProduct);
        price = sc.nextInt();
        System.out.println("Type the quantity of the product: "+nameProduct);
        quantity = sc.nextInt();
        System.out.println("Type the category of the product: "+nameProduct);
        category = selectCategory(sc);
        System.out.println("Type the number of purchases of the product: "+nameProduct);
        purchasedNumber = sc.nextInt();
        try {
            vs.addProductToInventory(nameProduct, description, price, quantity, purchasedNumber, category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int selectCategory(Scanner sc) {
        int category = 0;
        System.out.println("Select the category of the product:  \n 1. Books \n 2. Electronics \n 3. Clothes and accessories \n 4. Food and drinks \n 5. Stationery \n 6. Sports \n 7. Beauty and personal care products \n 8. Toys and games");
        category = sc.nextInt();
        return category;
    }

    private static void deleteProduct(Scanner sc) {
    }

    private static void orders(Scanner sc) {
        System.out.println("-----MERCADO LIBRE ORDERS----- \n 1. Register new order \n 2. Delete order");
        int sel = sc.nextInt();
        sc.nextLine();
        switch(sel) {
            case 1:
                registerNewOrder(sc);
                break;
            case 2:
                deleteOrder(sc);
                break;
            default:
                throw new IllegalArgumentException("Invalid option");
        }

    }

    private static void registerNewOrder(Scanner sc) {
        ArrayList<Product> products = new ArrayList<>();
        String buyerName = "";
        String date = "";
        String nameProduct = "";
        boolean exit = false;
        int productSel = 0;
        System.out.println("-----REGISTER NEW ORDER-----");
        System.out.println("Type the name of the buyer: ");
        buyerName = sc.nextLine();
        System.out.println("Type the date of the order: ");
        try {
            date = sc.nextLine();
            Date fecha = dateFormat.parse(date);
        } catch (Exception e) {
            System.out.println("Invalid date");;
        }
        while (!exit){
            System.out.println("Add new product to the order: \n 1. Yes \n 2. No");
            switch (productSel){
                case 1: products = addingProductsToOrder(sc); break;
                case 2: exit = true; break;
                default: throw new IllegalArgumentException("Enter a valid option");
            }
        }
        vs.addOrder(buyerName, products, date);
    }

    public static ArrayList<Product> addingProductsToOrder(Scanner sc){
        ArrayList<Product> products = new ArrayList<>();
        String nameProduct = "";
        System.out.println("Type the name of the product: ");
        nameProduct = sc.nextLine();
        Product product = vs.productExists(nameProduct);
        if(product != null){
            products = vs.addProductToOrder(product);
        }else{
            System.out.println("The product does not exist in the inventory.");
        }
        return products;
    }
    private static void deleteOrder(Scanner sc) {
    }

    private static void searchProduct(Scanner sc) {
    }

    private static void searchOrder(Scanner sc) {
    }
}