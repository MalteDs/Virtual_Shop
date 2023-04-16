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

    public void addProduct(String name, String description, int price, int amount, int purchasedNumber, int productCategory) throws Exception {
        //Agregar productos al inventario
        for(Product product : products){
            if(product.getName().equalsIgnoreCase(name)){
                throw new Exception("El producto ya existe");
            }
        }
        Product newProduct = new Product(name, description, price, amount, purchasedNumber, productCategory);
        products.add(newProduct);
    }

    public void deleteProduct(String nameProduct) {
        //Eliminar del inventario
        for (Product p : products) {
            if (p.getName().equals(nameProduct)) {
                products.remove(p);
                System.out.println("Producto eliminado exitosamente.");
                return;
            }
        }
        System.out.println("Producto no encontrado en el inventario.");
    }

    public void addProductToOrder(Product product){
        orderProducts.add(product);
    }

    public void addOrder(String buyerName, ArrayList<Product> products, String date) {
        Orders nuevoPedido = new Orders(buyerName, products, date);
        for (Product p : products) {
            p.decreaseAvailableQuantity(1);
        }
        orders.add(nuevoPedido);
    }

    public ArrayList<Product> searchProductsByName(String nameProduct) {
        ArrayList<Product> result = new ArrayList<Product>();
        for(Product product : products){
            if(product.getName().equalsIgnoreCase(nameProduct)){
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> searchProductByPrice(int priceProduct, int minPrice, int maxPrice) {
        ArrayList<Product> result = new ArrayList<Product>();
        for(Product product : products){
            if(product.getPrice() >= minPrice && product.getPrice() <= maxPrice){
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> getProductsByCategory(ProductCategory category) {
        ArrayList<Product> result = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getProductCategory() == category) {
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> getProductsByTimesPurchased(int minTimesPurchased, int maxTimesPurchased) {
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
            if(order.getBuyerName().equalsIgnoreCase(buyerName)){
                return order;
            }
        }
        return null;
    }

}
