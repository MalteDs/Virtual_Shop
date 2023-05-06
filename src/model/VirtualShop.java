package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        products.sort(Comparator.comparing(Product::getName));
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
            } else if (midName.compareToIgnoreCase(nameProduct) > 0){
                end = mid - 1;
            } else{
                int left = mid - 1;
                int right = mid + 1;
                while (left >= 0 && products.get(left).getName().equalsIgnoreCase(nameProduct)) {
                    result.add(products.get(left));
                    left--;
                }
                while (right < products.size() && products.get(right).getName().equalsIgnoreCase(nameProduct)) {
                    result.add(products.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByPrice(int minPrice, int maxPrice) {
        ArrayList<Product> result = new ArrayList<Product>();
        products.sort(Comparator.comparing(Product::getPrice));
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
            } else if (midPrice > maxPrice) {
                end = mid - 1;
            } else {
                int left = mid - 1;
                int right = mid + 1;
                while (left >= begin && products.get(left).getPrice() >= minPrice) {
                    result.add(products.get(left));
                    left--;
                }
                while (right <= end && products.get(right).getPrice() <= maxPrice) {
                    result.add(products.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByCategory(ProductCategory category) {
        ArrayList<Product> result = new ArrayList<Product>();
        products.sort(Comparator.comparing(Product::getProductCategory));
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
            }else if (midCategory.compareTo(category) > 0){
                end = mid - 1;
            } else{
                int left = mid - 1;
                int right = mid + 1;
                while (left >= 0 && products.get(left).getProductCategory() == category) {
                    result.add(products.get(left));
                    left--;
                }
                while (right < products.size() && products.get(right).getProductCategory() == category) {
                    result.add(products.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByTimesPurchased(int minTimesPurchased, int maxTimesPurchased) {
        ArrayList<Product> result = new ArrayList<>();
        products.sort(Comparator.comparing(Product::getPurchasedNumber));
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
            } else if (midTimesPurchased > maxTimesPurchased) {
                end = mid - 1;
            } else {
                int left = mid - 1;
                int right = mid + 1;
                while (left >= begin && products.get(left).getPurchasedNumber() >= minTimesPurchased) {
                    result.add(products.get(left));
                    left--;
                }
                while (right <= end && products.get(right).getPurchasedNumber() <= maxTimesPurchased) {
                    result.add(products.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;
    }

    public ArrayList<Order> searchOrderByBuyerName(String  buyerName) {
        ArrayList<Order> result = new ArrayList<>();
        orders.sort(Comparator.comparing(Order::getBuyerName));
        int begin = 0;
        int end = orders.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Order midOrder = orders.get(mid);
            String midBuyerName = midOrder.getBuyerName();
            if (midBuyerName.equalsIgnoreCase(buyerName)) {
                result.add(midOrder);
            }
            if (midBuyerName.compareToIgnoreCase(buyerName) < 0) {
                begin = mid + 1;
            } else if (midBuyerName.compareToIgnoreCase(buyerName) > 0){
                end = mid - 1;
            } else{
                int left = mid - 1;
                int right = mid + 1;
                while (left >= 0 && orders.get(left).getBuyerName().equalsIgnoreCase(buyerName)) {
                    result.add(orders.get(left));
                    left--;
                }
                while (right < orders.size() && orders.get(right).getBuyerName().equalsIgnoreCase(buyerName)) {
                    result.add(orders.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;
    }
    public ArrayList<Order> searchOrderByPrice(int minPrice, int maxPrice) {
        ArrayList<Order> result = new ArrayList<>();
        orders.sort(Comparator.comparing(Order::orderPrice));
        int begin = 0;
        int end = orders.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Order midOrder = orders.get(mid);
            double midPrice = midOrder.orderPrice();
            if (midPrice >= minPrice && midPrice <= maxPrice) {
                result.add(midOrder);
            }
            if (midPrice < minPrice) {
                begin = mid + 1;
            } else if (midPrice > maxPrice) {
                end = mid - 1;
            } else {
                int left = mid - 1;
                int right = mid + 1;
                while (left >= begin && orders.get(left).orderPrice() >= minPrice) {
                    result.add(orders.get(left));
                    left--;
                }
                while (right <= end && orders.get(right).orderPrice() <= maxPrice) {
                    result.add(orders.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;
    }

    public ArrayList<Order> searchOrderByDate(String date) {
        ArrayList<Order> result = new ArrayList<>();
        orders.sort(Comparator.comparing(Order::getDate));
        int begin = 0;
        int end = orders.size() - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            Order midOrder = orders.get(mid);
            String midDate = midOrder.getDate();
            if (midDate.equals(date)) {
                result.add(midOrder);
            }
            if (midDate.compareTo(date) < 0) {
                begin = mid + 1;
            } else if (midDate.compareTo(date) > 0) {
                end = mid - 1;
            } else {
                int left = mid - 1;
                int right = mid + 1;
                while (left >= 0 && orders.get(left).getDate().equals(date)) {
                    result.add(orders.get(left));
                    left--;
                }
                while (right < orders.size() && orders.get(right).getDate().equals(date)) {
                    result.add(orders.get(right));
                    right++;
                }
                return result;
            }
        }
        return result;

    }

    public String printProductsByNameUpWard(ArrayList<Product> products){
        int order = 1;
        String result = "";
        products.sort(Comparator.comparing(Product::getName));
        for (Product product : products) {
            result += order + ". " + product.toString() + "\n";
            order++;
        }
        return result;
    }

    public String printProductsByNameDownWard(ArrayList<Product> products){
        int order = 1;
        String result = "";
        products.sort(Comparator.comparing(Product::getName).reversed());
        for (Product product : products) {
            result += order + ". " + product.toString() + "\n";
            order++;
        }
        return result;
    }

    public String printProductsByPriceUpWard(ArrayList<Product> products){
        int order = 1;
        String result = "";
        products.sort(Comparator.comparing(Product::getPrice));
        for (Product product : products) {
            result += order + ". " + product.toString() + "\n";
            order++;
        }
        return result;
    }

    public String printProductsByPriceDownWard(ArrayList<Product> products){
        int order = 1;
        String result = "";
        products.sort(Comparator.comparing(Product::getPrice).reversed());
        for (Product product : products) {
            result += order + ". " + product.toString() + "\n";
            order++;
        }
        return result;
    }

    public String printProductsByTimesPurchasedUpWard(ArrayList<Product> products){
        int order = 1;
        String result = "";
        products.sort(Comparator.comparing(Product::getPurchasedNumber));
        for (Product product : products) {
            result += order + ". " + product.toString() + "\n";
            order++;
        }
        return result;
    }

    public String printProductsByTimesPurchasedDownWard(ArrayList<Product> products){
        int order = 1;
        String result = "";
        products.sort(Comparator.comparing(Product::getPurchasedNumber).reversed());
        for (Product product : products) {
            result += order + ". " + product.toString() + "\n";
            order++;
        }
        return result;
    }

    public String printOrdersByBuyerNameUpWard(ArrayList<Order> orders){
        int printOrder = 1;
        String result = "";
        orders.sort(Comparator.comparing(Order::getBuyerName));
        for (Order order : orders) {
            result += printOrder + ". " + order.toString() + "\n";
            printOrder++;
        }
        return result;
    }

    public String printOrdersByBuyerNameDownWard(ArrayList<Order> orders){
        int printOrder = 1;
        String result = "";
        orders.sort(Comparator.comparing(Order::getBuyerName).reversed());
        for (Order order : orders) {
            result += printOrder + ". " + order.toString() + "\n";
            printOrder++;
        }
        return result;
    }

    public String printOrdersByPriceUpWard(ArrayList<Order> orders){
        int printOrder = 1;
        String result = "";
        orders.sort(Comparator.comparing(Order::orderPrice));
        for (Order order : orders) {
            result += printOrder + ". " + order.toString() + "\n";
            printOrder++;
        }
        return result;
    }

    public String printOrdersByPriceDownWard(ArrayList<Order> orders){
        int printOrder = 1;
        String result = "";
        orders.sort(Comparator.comparing(Order::orderPrice).reversed());
        for (Order order : orders) {
            result += printOrder + ". " + order.toString() + "\n";
            printOrder++;
        }
        return result;
    }

    public String printOrdersByDateUpWard(ArrayList<Order> orders){
        int printOrder = 1;
        String result = "";
        orders.sort(Comparator.comparing(Order::getDate));
        for (Order order : orders) {
            result += printOrder + ". " + order.toString() + "\n";
            printOrder++;
        }
        return result;
    }

    public String printOrdersByDateDownWard(ArrayList<Order> orders) {
        int printOrder = 1;
        String result = "";
        orders.sort(Comparator.comparing(Order::getDate).reversed());
        for (Order order : orders) {
            result += printOrder + ". " + order.toString() + "\n";
            printOrder++;
        }
        return result;
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
