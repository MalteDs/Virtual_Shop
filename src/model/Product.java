package model;

public class Product implements productQuantity, Comparable<Product> {

    private String name;
    private String description;
    private double price;
    private int amount;
    private int purchasedNumber;
    private ProductCategory productCategory;

    public Product(String name, String description, double price, int amount, int productCategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.purchasedNumber = 0;
        switch (productCategory){
            case 1: this.productCategory = ProductCategory.BOOKS;
                break;
            case 2: this.productCategory = ProductCategory.ELECTRONICS;
                break;
            case 3: this.productCategory = ProductCategory.CLOTHES_AND_ACCESSORIES;
                break;
            case 4: this.productCategory = ProductCategory.FOOD_AND_DRINKS;
                break;
            case 5: this.productCategory = ProductCategory.STATIONERY;
                break;
            case 6: this.productCategory = ProductCategory.SPORTS;
                break;
            case 7: this.productCategory = ProductCategory.BEAUTY_AND_PERSONAL_CARE_PRODUCTS;
                break;
            case 8: this.productCategory = ProductCategory.TOYS_AND_GAMES;
                break;

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPurchasedNumber() {
        return purchasedNumber;
    }

    public void setPurchasedNumber(int purchasedNumber) {
        this.purchasedNumber = purchasedNumber;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public void addAvailableQuantity(int cantidad) {
        this.amount += cantidad;
    }
    @Override
    public void decreaseAvailableQuantity(int cantidad) {
        this.amount -= cantidad;
    }

    @Override
    public int compareTo(Product product) {
        return this.name.compareTo(product.getName());
    }

    @Override
    public String toString() {
        return "Name product: " + name  +
                ", price: " + price +
                ", amount: " + amount +
                ", number of times purchased" + purchasedNumber +
                ", category : " + productCategory+"\n";
    }
}