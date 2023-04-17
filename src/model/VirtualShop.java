package model;

import java.util.ArrayList;

public class VirtualShop {
    private ArrayList<Orders> orders;
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
                throw new Exception("El producto ya existe");
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

}
