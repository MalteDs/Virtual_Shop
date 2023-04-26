package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;


public class VirtualShop {
    private ArrayList<Orders> orders;
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
        for (Product p : products) {
            if (p.getName().equals(nameProduct)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Product> addProductToOrder(Product product){
        orderProducts.add(product);
        return orderProducts;
    }

    public ArrayList<Orders> addOrder(String buyerName, ArrayList<Product> products, String date) {
        Orders nuevoPedido = new Orders(buyerName, products, date);
        for (Product p : products) {
            p.decreaseAvailableQuantity(1);
        }
        orders.add(nuevoPedido);
        return orders;
    }

    public ArrayList<Product> searchProductsByName(String nameProduct) {
        ArrayList<Product> result = new ArrayList<Product>();
        for(Product product : products){
            if(product.getName().contains(nameProduct)){
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByPrice(int minPrice, int maxPrice) {
        ArrayList<Product> result = new ArrayList<Product>();
        for(Product product : products){
            if(product.getPrice() >= minPrice && product.getPrice() <= maxPrice){
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByCategory(ProductCategory category) {
        ArrayList<Product> result = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getProductCategory() == category) {
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductsByTimesPurchased(int minTimesPurchased, int maxTimesPurchased) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPurchasedNumber() >= minTimesPurchased && p.getPurchasedNumber() <= maxTimesPurchased) {
                result.add(p);
            }
        }
        return result;
    }

    public Orders searchOrderByBuyerName(String  buyerName) {
        for(Orders order : orders){
            if(order.getBuyerName().contains(buyerName)){
//                System.out.println("Pedido encontrado");
                return order;
            }
        }
//        System.out.println("Pedido no encontrado");
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
