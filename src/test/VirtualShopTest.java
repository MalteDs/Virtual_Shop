package test;
import junit.framework.TestCase;
import model.*;

import java.util.ArrayList;

public class VirtualShopTest extends TestCase {
    private VirtualShop virtualShop;

    public void setUpStage1() {
        virtualShop = new VirtualShop();
    }

    public void setUpStage2() {
        setUpStage1();
        try {
            virtualShop.addProductToInventory("Ana Frank's diary", "Description of the book Diary of Anne Frank", 50000, 20, 1);
            virtualShop.addProductToInventory("Red T-shirt", "Beautiful red t-shirt", 45000, 30, 3);
            virtualShop.addProductToInventory("Laptop", "Laptop with 16GB RAM", 3000000, 5, 2);
        } catch (Exception e) {
            fail();
        }
    }

    public ArrayList<Product> setUpStage3(){
        setUpStage2();
        Product orderProduct1 = virtualShop.productExists("Laptop");
        Product orderProduct2 = virtualShop.productExists("Red T-shirt");
        Product orderProduct3 = virtualShop.productExists("Ana Frank's diary");
        ArrayList<Product> products = new ArrayList<>();
        products.add(orderProduct1);
        products.add(orderProduct2);
        products.add(orderProduct3);
        Order order = new Order("David M", new ArrayList<>(), "2020-10-10", 1);
        try {
            virtualShop.insertProductsInOrder(orderProduct1, order);
            virtualShop.insertProductsInOrder(orderProduct2, order);
            virtualShop.insertProductsInOrder(orderProduct3, order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public void setUpStage4(){
        setUpStage2();
        Product orderProduct1 = virtualShop.productExists("Laptop");
        Order order = new Order("Alejo", new ArrayList<>(), "2020-10-10", 1);
        try {
            virtualShop.insertProductsInOrder(orderProduct1, order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void setUpStage5(){
        setUpStage3();
        try{
            ArrayList<Product> products1 = new ArrayList<>();
            ArrayList<Product> products2 = new ArrayList<>();
            virtualShop.addOrder("David M", products1, "2020-10-10");
            virtualShop.addOrder("Alejando F", products2, "2020-10-10");
        } catch (Exception e){
            fail();
        }
    }
    public void testAddProductToInventory1() {
        setUpStage1();
        try {
            virtualShop.addProductToInventory("Smart TV", "55-inch Smart TV", 1500000, 10, 2);
        } catch (Exception e) {
            fail();
        }
    }

    public void testAddProductToInventory2() {
        setUpStage2();
        try {
            virtualShop.addProductToInventory("Smart TV", "55-inch Smart TV", 1500000, 10,  2);
        } catch (Exception e) {
            fail();
        }
    }

    public void testDeleteProductFromInventory1() {
        setUpStage2();
        virtualShop.deleteProductFromInventory("Red T-shirt");
        assertNull(virtualShop.productExists("Red T-shirt"));
    }

    public void testDeleteProductFromInventory2() {
        setUpStage2();
        virtualShop.deleteProductFromInventory("Airpods In-ear");
        assertNull(virtualShop.productExists("Airpods In-ear"));
    }

    public void testAddProductToOrder(){
        setUpStage2();
        try{
            Product orderProduct = virtualShop.productExists("Red T-shirt");
            ArrayList<Product> products = new ArrayList<>();
            products.add(orderProduct);
            Order order = new Order("David M", products, "2020-10-10", 1);
            virtualShop.insertProductsInOrder(orderProduct, order);
        } catch (Exception e){
            fail();
        }
    }

    public void testAddProductToOrder2(){
        setUpStage2();
        try{
            Product orderProduct = virtualShop.productExists("Red T-shirt");
            ArrayList<Product> products = new ArrayList<>();
            products.add(orderProduct);
            Order order = new Order("David M", products, "2020-10-10", 1);
            virtualShop.insertProductsInOrder(orderProduct, order);
        } catch (Exception e){
            fail();
        }
    }

    public void testAddOrder1(){
        ArrayList<Product> productsToBuy = setUpStage3();
        try{
            virtualShop.addOrder("David M", productsToBuy, "2020-10-10");
        } catch (Exception e){
            fail();
        }
    }

    public void testAddOrder2(){
        ArrayList<Product> productsToBuy = setUpStage3();
        try{
            virtualShop.addOrder("David M", productsToBuy, "2020-10-10");
        } catch (Exception e){
            fail();
        }
    }

    public void testSearchProductsByName1(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByName("Laptop");
        assertEquals(1, products.size());
    }

    public void testSearchProductsByName2(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByName("Red T-shirt");

        for(Product p : products){
            if(p.getName().equals("Red T-shirt")){
                assertEquals(p.getName().equals("Red T-shirt"), true);
            }
        }
    }

    public void testSearchProductsByPrice1(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByPrice(45000, 100000);
        for(Product p : products){
            if(p.getPrice() >= 45000 && p.getPrice() <= 100000){
                assertEquals(p.getPrice() >= 45000 && p.getPrice() <= 100000, true);
            }
        }
    }

    public void testSearchProductsByPrice2(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByPrice(45000, 100000);
        for(Product p : products){
            if(p.getPrice() >= 0 && p.getPrice() <= 2000000){
                assertEquals(p.getPrice() >= 0 && p.getPrice() <= 2000000, true);
            }
        }
    }

public void testSearchProductsByCategory1(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByCategory(ProductCategory.BOOKS);
        for(Product p : products){
            if(p.getProductCategory().equals("Books")){
                assertEquals(p.getProductCategory().equals("Books"), true);
            }
        }
    }

    public void testSearchProductsByCategory2(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByCategory(ProductCategory.ELECTRONICS);
        for(Product p : products){
            if(p.getProductCategory().equals("Electronics")){
                assertEquals(p.getProductCategory().equals("Electronics"), true);
            }
        }
    }

    public void testSearchProductsByTimesPurchased1(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByTimesPurchased(1, 10);
        for(Product p : products){
            if(p.getPurchasedNumber() >= 1 && p.getPurchasedNumber() <= 10){
                assertEquals(p.getPurchasedNumber() >= 1 && p.getPurchasedNumber() <= 10, true);
            }
        }
    }

    public void testSearchProductsByTimesPurchased2(){
        setUpStage2();
        ArrayList<Product> products = virtualShop.searchProductsByTimesPurchased(1, 10);
        for(Product p : products){
            if(p.getPurchasedNumber() >= 0 && p.getPurchasedNumber() <= 100){
                assertEquals(p.getPurchasedNumber() >= 0 && p.getPurchasedNumber() <= 100, true);
            }
        }
    }


}
