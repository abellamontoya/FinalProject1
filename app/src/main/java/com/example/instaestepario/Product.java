package com.example.instaestepario;

public class Product {
    public String Author;
    public String ImageURL;
    public String Name;
    public int Quantity;
    public int Price;


    // Empty constructor required by Firestore
    public Product() {}

    public Product(String imageURL, String name, String author, int price, int quantity) {
        this.ImageURL = imageURL;
        this.Name = name;
        this.Author = author;
        this.Price = price;
        this.Quantity = quantity;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
