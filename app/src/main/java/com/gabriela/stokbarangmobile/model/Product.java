package com.gabriela.stokbarangmobile.model;

public class Product {

    private int id;
    private String code;
    private String name;
    private String category;
    private String unit;
    private int stock;
    private int minimum_stock;
    private String warehouse_location;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public int getStock() {
        return stock;
    }

    public int getMinimum_stock() {
        return minimum_stock;
    }

    public String getWarehouse_location() {
        return warehouse_location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMinimum_stock(int minimum_stock) {
        this.minimum_stock = minimum_stock;
    }

    public void setWarehouse_location(String warehouse_location) {
        this.warehouse_location = warehouse_location;
    }
}
