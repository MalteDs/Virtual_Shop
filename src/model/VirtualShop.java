package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import exceptions.*;
import com.google.gson.Gson;

public class VirtualShop {
    private ArrayList<Order> orders;
    private ArrayList<Product> products;
    private ArrayList<Product> orderProducts;

    Gson gson;
    File resultProducts;
    File resultOrders;

    public VirtualShop() {
        orders = new ArrayList<>();
        orderProducts = new ArrayList<>();
        products = new ArrayList<>();
        gson = new Gson();
        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir.getAbsolutePath() + "/data");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdir();
        }
        resultProducts = new File(dataDirectory, "resultProducts.json");
        resultOrders = new File(dataDirectory, "resultOrders.json");

    }


    public String addProductToInventory(String name, String description, int price, int amount, int purchasedNumber, int productCategory) throws Exception {
        //Agregar productos al inventario
        String msj="";
        for(Product product : products){
            if(product.getName().equalsIgnoreCase(name)){
                throw new Exception("The product already exists in the inventory.");
            }
        }
        Product newProduct = new Product(name, description, price, amount, purchasedNumber, productCategory);
        products.add(newProduct);
        converToGson(newProduct);
        msj="Product added successfully.";
        return msj;
    }
    
    public void converToGson(Product product){
        String json = gson.toJson(product);
        try (FileWriter fw = new FileWriter(resultProducts, true)) {
            fw.write(json);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo result.json: " + e.getMessage());
        }
    }


    public String deleteProductFromInventory(String nameProduct) {
        String msj="";
        //Eliminar del inventario
        for (Product p : products) {
            if (p.getName().equals(nameProduct)) {
                products.remove(p);
                msj="Producto eliminado exitosamente.";
                }
            }
        if(msj==null){
            msj="Producto no encontrado en el inventario.";
        }
        return msj;
    }

    public Product productExists(String nameProduct){
        Product product = null;
        int begin = 0;
        int end = products.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Product midProduct = products.get(mid);
            String midName = midProduct.getName();
            if (midName.equalsIgnoreCase(nameProduct)) {
                product = midProduct;
            }
            if (midName.compareToIgnoreCase(nameProduct) < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return product;
    }

    public ArrayList<Product> addProductToOrder(Product product, int quantity) throws Exception {
        try{
            if(product.getAmount()<=0){
                throw new quantityException("The product is not available.");
            }
            orderProducts.add(product);
            product.decreaseAvailableQuantity(quantity);
            return orderProducts;
        }catch (quantityException e){
            throw new Exception(e.getMessage());
        }
    }

    public ArrayList<Order> addOrder(String buyerName, ArrayList<Product> products, String date) {
        Order nuevoPedido = new Order(buyerName, products, date);
        for (Product p : products) {
            p.decreaseAvailableQuantity(1);
        }
        orders.add(nuevoPedido);
        return orders;
    }

    public ArrayList<Product> searchProductsByName(String nameProduct) {
        ArrayList<Product> result = new ArrayList<Product>();
        Collections.sort(products);
        int begin = 0;
        int end = products.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Product midProduct = products.get(mid);
            String midName = midProduct.getName();
            if (midName.equalsIgnoreCase(nameProduct)) {
                result.add(midProduct);
            }
            if (midName.compareToIgnoreCase(nameProduct) < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByPrice(int minPrice, int maxPrice) {
        ArrayList<Product> result = new ArrayList<Product>();
        int begin = 0;
        int end = products.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Product midProduct = products.get(mid);
            int midPrice = midProduct.getPrice();
            if (midPrice >= minPrice && midPrice <= maxPrice) {
                result.add(midProduct);
            }
            if (midPrice < minPrice) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByCategory(ProductCategory category) {
        ArrayList<Product> result = new ArrayList<Product>();
        int begin = 0;
        int end = products.size() - 1;
        while (begin <= end){
            int mid = (begin + end) / 2;
            Product midProduct = products.get(mid);
            ProductCategory midCategory = midProduct.getProductCategory();
            if(midCategory == category){
                result.add(midProduct);
            }
            if(midCategory.compareTo(category) < 0){
                begin = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByTimesPurchased(int minTimesPurchased, int maxTimesPurchased) {
        ArrayList<Product> result = new ArrayList<>();
        int begin = 0;
        int end = products.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Product midProduct = products.get(mid);
            int midTimesPurchased = midProduct.getPurchasedNumber();
            if (midTimesPurchased >= minTimesPurchased && midTimesPurchased <= maxTimesPurchased) {
                result.add(midProduct);
            }
            if (midTimesPurchased < minTimesPurchased) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return result;
    }

    public Order searchOrderByBuyerName(String  buyerName) {
        Order order = null;
        int begin = 0;
        int end = orders.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Order midOrder = orders.get(mid);
            String midBuyerName = midOrder.getBuyerName();
            if (midBuyerName.equals(buyerName)) {
                order = midOrder;
                return order;
            }
            if (midBuyerName.compareTo(buyerName) < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return null;
    }
    public boolean validateData(String name, String description, double price, int amount, int purchasedNumber, int productCategory){
        boolean correct=true;
        try{
            if(name==null || description ==null || price <0 || amount<0 || purchasedNumber<0 || productCategory<0 || productCategory>8){
                correct=false;
            }else{
                correct=true;
            }
        }catch(NumberFormatException e){
            System.out.println("Hubo un error");

        }catch(RuntimeException e){
            System.out.println("Ocurrio un error");
        }
        return correct;
    }


}
