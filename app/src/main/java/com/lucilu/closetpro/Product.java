package com.lucilu.closetpro;

public class Product {

    private String color;
    private String img;
    private String marca;
    private String nombre;
    private double precio;
    private String username;

    public Product() {
    }

    public Product(String color, String img, String marca, String nombre, double precio, String username) {
        this.color = color;
        this.img = img;
        this.marca = marca;
        this.nombre = nombre;
        this.precio = precio;
        this.username = username;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
