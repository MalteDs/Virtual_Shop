package model;

import java.util.ArrayList;

public class Orders implements OrderPrice {
    private String buyerName;
    private ArrayList<Product> products;
    private String date;

    public Orders(String buyerName, ArrayList<Product> products, String date) {
        this.buyerName = buyerName;
        this.products = products;
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

    @Override
    public double orderPrice() {
        int finalPrice=0;
        for(int i=0; i<products.size();i++){
            finalPrice+= products.get(i).getPrice();
        }
        return finalPrice;
    }
}
