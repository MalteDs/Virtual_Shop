package ui;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static VirtualShop vs = new VirtualShop();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int sel = 0;
        boolean exit = false;
        try {
            while (!exit) {
                print("-----MERCADO LIBRE----- \n 1. Inventory \n 2. Orders \n 3. Save and exit");
                sel = sc.nextInt();
                sc.nextLine();
                switch (sel) {
                    case 1 -> inventory(sc);
                    case 2 -> orders(sc);
                    case 3 -> {
                        exit = true;
                        vs.changeTheJson();
                    }
                    default -> {
                        print("Enter a valid option");
                        System.exit(1);
                    }
                }
            }
        }catch (InputMismatchException e ){
            print("Enter a valid option");
        }
        sc.close();
    }
    private static void inventory(Scanner sc) {
        print("-----MERCADO LIBRE INVENTORY----- \n 1. Register new product \n 2. Delete product \n 3. Search Product \n");
        int sel = sc.nextInt();
        sc.nextLine();
        switch (sel) {
            case 1 -> registerNewProduct(sc);
            case 2 -> deleteProduct(sc);
            case 3 -> searchProduct(sc);
            default -> throw new IllegalArgumentException("Invalid option");
        }
    }
    private static void registerNewProduct(Scanner sc) {
        try {
            String nameProduct = "";
            String description = "";
            double price = 0;
            int quantity = 0;
            int category = 0;
            print("-----REGISTER NEW PRODUCT-----");
            print("Type the name of the product: ");
            nameProduct = sc.nextLine();
            print("Type the description of the product: " + nameProduct);
            description = sc.nextLine();
            print("Type the price of the product: " + nameProduct);
            price = sc.nextDouble();
            print("Type the quantity of the product: " + nameProduct);
            quantity = sc.nextInt();
            sc.nextLine();
            print("Type the category of the product: " + nameProduct);
            category = selectCategory();
            if (vs.validateData(nameProduct, description, price, quantity, category)){
                print(vs.addProductToInventory(nameProduct, description, price, quantity, category));
            } else {
                print("Incorrect data");
            }
        } catch (NumberFormatException e) {
            print("An erros ocurred while entering a number");
        } catch (RuntimeException e) {
            print(e.getMessage());
        } catch (IOException e) {
            print(e.getMessage());
        } catch (Exception e) {
            print(e.getMessage());
        }
    }
    private  static  void searchProduct(Scanner sc){
        print("-----MERCADO LIBRE SEARCH PRODUCT----- \n 1. Search product by name \n 2. Search product by price \n 3. Search product by category \n 4. Search product by times purchased \n");
        int sel = sc.nextInt();
        sc.nextLine();
        switch (sel) {
            case 1 -> {
                print("Enter the name of the product: ");
                String name=sc.nextLine();
                searchProductByName(name);
            }
            case 2 -> {
                print("Enter the min price of the product: ");
                double min = sc.nextDouble();
                print("Enter the max price of the product: ");
                double max = sc.nextDouble();
                searchProductByPrice(min, max);
            }

            case 3 -> {
                int category = selectCategory();
                while(category>8 | category <1){
                    print("Enter a valid option (1-8)");
                    category = selectCategory();
                }
                searchProductByCategory(category);

            }

            case 4 -> {
                print("Enter the min times purchased of the product: ");
                int min = sc.nextInt();
                sc.nextLine();
                print("Enter the max times purchased of the product: ");
                int max=sc.nextInt();
                sc.nextLine();
                while (min >max && min<0 && max<0){
                    print("The min times purchased can't be greater than the max times purchased");
                    print("Enter the min times purchased of the product: ");
                    min = sc.nextInt();
                    sc.nextLine();
                    print("Enter the max times purchased of the product: ");
                    max=sc.nextInt();
                    sc.nextLine();
                }
                searchProductByTimePurchased(min,max);
            }
            default -> throw new IllegalArgumentException("Invalid option");
        }
    }
    private static int selectCategory() {
        int category = 0;
        try {
            print("Select the category of the product:  \n 1. Books \n 2. Electronics \n 3. Clothes and accessories \n 4. Food and drinks \n 5. Stationery \n 6. Sports \n 7. Beauty and personal care products \n 8. Toys and games");
            category = sc.nextInt();
            sc.nextLine();
        }catch (NumberFormatException e){
            print("An erros ocurred while entering a number");
        }
        return category;
    }
    private static void searchProductByName(String name ) {
        if(vs.searchProductsByName(name).isEmpty()){
            print("There are no products with that name");

        }else{
            orderPrintInventory(sc,vs.searchProductsByName(name));
        }
    }
    private static void searchProductByPrice(double min, double max) {
        if (vs.searchProductsByPrice(min, max).isEmpty()==true) {
            print("There are no products with that price");

        } else {
            orderPrintInventory(sc,vs.searchProductsByPrice(min, max));
        }
    }
    private static void searchProductByTimePurchased(int min,int max) {
        if (vs.searchProductsByTimesPurchased(min,max).isEmpty()==true) {
            print("There are no products with that times purchased");

        } else {
            orderPrintInventory(sc,vs.searchProductsByTimesPurchased(min,max));
        }

    }
    private static void searchProductByCategory(int category) {
        if (vs.searchProductsByCategory(vs.selectCategory(category)).isEmpty()==true) {
            print("There are no products with that category");
        } else {
            orderPrintInventory(sc,vs.searchProductsByCategory(vs.selectCategory(category)));
        }
    }
    public static void orderPrintInventory(Scanner sc, ArrayList<Product> products) {
        print("Sort products by: \n 1. Name \n 2. price \n 3. Category \n 4. Times purchased");
        try {
            int select=sc.nextInt();
            sc.nextLine();
            while(select>5 | select<1){
                print("Select a valid option");
                select=sc.nextInt();
                sc.nextLine();
            }
            if (select==1) {
                printByName(products);
            }
            if (select==2) {
                printByPrice(products);
            }
            if (select==3) {
                printByCategory(products);
            }
            if (select==4) {
                printByTimesPurchased(products);
            }
        }catch (NumberFormatException e){
            print("No se admiten otro tipo de caracteres");
        }
    }
    public static int selectOrder(Scanner sc){
        print("Sort in: \n 1. Ascending order \n 2. Descending order");
        int select=sc.nextInt();
        sc.nextLine();
        while(select>2 | select<1){
            print("Select a valid option");
            select=sc.nextInt();
            sc.nextLine();
        }
        return select;
    }
    public static void printByName(ArrayList<Product> products){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printProductsByNameUpWard(products));
        }else if(orderSelect==2){
            print(vs.printProductsByNameDownWard(products));
        }
    }

    public static void printByPrice(ArrayList<Product> products){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printProductsByPriceUpWard(products));
        }else if(orderSelect==2){
            print(vs.printProductsByPriceDownWard(products));
        }
    }

    public static void printByCategory(ArrayList<Product> products){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printProductsByCategoryUpWard(products));
        }else if(orderSelect==2){
            print(vs.printProductsByCategoryDownWard(products));
        }
    }

    public static void printByTimesPurchased(ArrayList<Product> products){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printProductsByTimesPurchasedUpWard(products));
        }else if(orderSelect==2){
            print(vs.printProductsByTimesPurchasedDownWard(products));
        }
    }
    private static void deleteProduct(Scanner sc){
        if(vs.getProducts().isEmpty()==true){
            print("There are no products to delete");
        }else{
            print(vs.getProducts().toString());
            print("Write the name of the product you want to delete:");
            String name = sc.nextLine();
            print(vs.deleteProductFromInventory(name));
        }
    }
    private static void orders(Scanner sc) {
        print("-----MERCADO LIBRE ORDERS----- \n 1. Register new order \n 2. Delete order \n 3. SearchOrder");
        int sel = sc.nextInt();
        sc.nextLine();
        switch (sel) {
            case 1 -> registerNewOrder(sc);
            case 2 -> deleteOrder(sc);
            case 3 -> searchOrder(sc);
            default -> throw new IllegalArgumentException("Invalid option");
        }

    }
    private static void registerNewOrder(Scanner sc) {
        try {
            ArrayList<Product> products = new ArrayList<>();
            String buyerName = "";
            String date = "";
            boolean exit = false;
            int productSel = 0;
            print("-----REGISTER NEW ORDER-----");
            print("Type the name of the buyer: ");
            buyerName = sc.nextLine();
            print("Type the date of the order: ");
            date = sc.nextLine();
            Order order=vs.addOrder(buyerName,null,date);
            while (!exit) {
                print("Add new product to the order: \n 1. Yes \n 2. No");
                productSel = sc.nextInt();
                sc.nextLine();
                switch (productSel) {
                    case 1 -> addingProductsToOrder(sc, order);
                    case 2 -> exit = true;
                    default -> print("Enter a valid option");
                }
            }
            if(order.getProducts().isEmpty()==true){
                print("You must add at least one product to the order");
                print(vs.deleteOrder(order.getId()));
            }
        }catch (NumberFormatException e){
            print("Invalid option");
        }
    }
    public static void addingProductsToOrder(Scanner sc, Order order){
        ArrayList<Product> products = new ArrayList<>();
        print(vs.getProducts().toString());
        System.out.println("Enter the name of the product you want to add: ");
        String nameProduct = sc.nextLine();
        Product product = vs.productExists(nameProduct);
        if(product != null){
            boolean create= vs.insertProductsInOrder(product,order);
            if(create==true){
                print("Product added successfully");
            }else{
                print("The product could not be added");
            }
        }else{
            print("The product does not exist");
        }
    }
    private static void deleteOrder(Scanner sc) {
        if(vs.getOrders().isEmpty()==true){
            print("There are no orders to delete");
        }else{
            print("Enter the id of the order you want to delete:");
            print(vs.getOrders().toString());
            try {
                int id=sc.nextInt();
                sc.nextLine();
                print(vs.deleteOrder(id));

            }catch (NumberFormatException e){
                print("Invalid option");
            }

        }
    }
    private static void searchOrder(Scanner sc) {
        print("-----SEARCH ORDER----- \n 1. Search by buyer name \n 2. Search by price \n 3. Search by date");
        int sel = sc.nextInt();
        sc.nextLine();
        switch (sel) {
            case 1 -> {
                print("Enter the name of the buyer: ");
                String name=sc.nextLine();
                searchOrdenByBuyerName(name);
            }
            case 2 -> {
                print("Enter the minimum price: ");
                double min = sc.nextDouble();
                print("Enter the maximum price: ");
                double max = sc.nextDouble();
                searchOrdertByPrice(min, max);
            }

            case 3 -> {
                print("Enter the date of the order:");
                try {
                    String date = sc.nextLine();
                    searchOrderByDate(date);
                }catch (Exception e){
                    print("Invalid date");
                }

            }
            default -> throw new IllegalArgumentException("Invalid option");
        }
    }
    private static void searchOrderByDate(String date ) {
        if(vs.searchOrderByDate(date).isEmpty()==true){
            print(" No existe el producto ");
        }else{
            orderPrintOrder(sc,vs.searchOrderByDate(date));
        }
    }
    private static void searchOrdertByPrice(double min, double max) {
        if(vs.searchOrderByPrice(min, max).isEmpty()==true){
            print(" No existe el producto ");

        }else{
            orderPrintOrder(sc, vs.searchOrderByPrice(min, max));
        }
    }
    private static void searchOrdenByBuyerName(String name) {
        if(vs.searchOrderByBuyerName(name).isEmpty()==true){
            print(" No existe el producto ");

        }else{
            orderPrintOrder(sc,vs.searchOrderByBuyerName(name));
        }
    }
    private static void orderPrintOrder(Scanner sc, ArrayList<Order> orders) {
        print("Sort the list of orders: \n 1. By buyer name \n 2. By price \n 3. By date");
        try {
            int select=sc.nextInt();
            sc.nextLine();
            while(select>3 || select<1){
                print("Enter a valid option");
                select=sc.nextInt();
                sc.nextLine();
            }
            if(select==1 ){
                printByBuyerName(orders);
            } else if (select==2) {
                printByPriceOrder(orders);
            }
            else{
                printByDateOrder(orders);
            }
        }catch (NumberFormatException e){
            print("No se admiten otro tipo de caracteres");
        }
    }
    public static void printByBuyerName(ArrayList<Order> orders){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printOrdersByBuyerNameUpWard(orders));
        }else if(orderSelect==2){
            print(vs.printOrdersByBuyerNameDownWard(orders));
        }
    }

    public static void printByPriceOrder(ArrayList<Order> orders){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printOrdersByPriceUpWard(orders));
        }else if(orderSelect==2){
            print(vs.printOrdersByPriceDownWard(orders));
        }
    }
    public static void printByDateOrder(ArrayList<Order> orders){
        int orderSelect = selectOrder(sc);
        if(orderSelect==1){
            print(vs.printOrdersByDateUpWard(orders));
        }else if(orderSelect==2){
            print(vs.printOrdersByDateDownWard(orders));
        }
    }
    private static void print(Object o){
        System.out.println(o);
    }
}