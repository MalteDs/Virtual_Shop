package model;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.*;

public class VirtualShop {
    private ArrayList<Order> orders;
    private ArrayList<Product> products;
    private ArrayList<Product> orderProducts;

    public VirtualShop() {
        orders = new ArrayList<>();
        orderProducts = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void addProductToInventory(String name, String description, int price, int amount, int purchasedNumber, int productCategory) throws Exception {
        //Agregar productos al inventario
        for(Product product : products){
            if(product.getName().equalsIgnoreCase(name)){
                throw new Exception("The product already exists in the inventory.");
            }
        }
        Product newProduct = new Product(name, description, price, amount, purchasedNumber, productCategory);
        products.add(newProduct);
    }

    public void deleteProductFromInventory(String nameProduct) {
        //Eliminar del inventario
        for (Product p : products) {
            if (p.getName().equals(nameProduct)) {
                products.remove(p);
//                System.out.println("Producto eliminado exitosamente.");
                return;
            }
        }
    //        System.out.println("Producto no encontrado en el inventario.");
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

}
