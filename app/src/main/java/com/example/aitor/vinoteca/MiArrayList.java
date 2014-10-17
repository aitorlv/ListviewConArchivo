package com.example.aitor.vinoteca;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by aitor on 06/10/2014.
 */
public class MiArrayList <ArrayList>  {

        private String nombre,descri, precio;
        private Drawable img;
        private int idradiobuton;
    public MiArrayList (String nombre, String descri, String precio, Drawable img,int idradiobutton) {
        this.nombre=nombre;
        this.descri=descri;
        this.precio=precio;
        this.img=img;
        this.idradiobuton=idradiobutton;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdradiobuton() {
        return idradiobuton;
    }

    public void setIdradiobuton(int idradiobuton) {
        this.idradiobuton = idradiobuton;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
