package model;

import java.util.ArrayList;

public class Order implements  OrderPrice{
    private String buyerName;
    private  String date;
    private ArrayList<Product> products;
    private int id;


    public Order(String buyerName, ArrayList<Product> products, String date, int id) {
        this.buyerName = buyerName;
        this.products= new ArrayList<>();
        this.date = date;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void addProducstOrder(Product product){
        products.add(product);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double orderPrice() {
        int finalPrice=0;
        for(int i=0; i<products.size();i++){
            finalPrice+= products.get(i).getPrice();
        }
        return finalPrice;
    }

    @Override
    public String toString() {
        return
                "[ buyerName = " + buyerName +
                ", date='" + date +
                ", products=" + products +
                "] \n";
    }
}
