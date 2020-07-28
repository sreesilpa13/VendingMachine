package com.vendingmachine;

public enum Item {
    CHOCOLATES("Chocolates", 25), CANDY("Candy", 35), COLDDRINK("Cold-drink", 45);

    private String name;
    private int price;

    private Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}

