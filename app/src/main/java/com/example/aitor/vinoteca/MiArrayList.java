package com.example.aitor.vinoteca;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by aitor on 06/10/2014.
 */
public class MiArrayList <ArrayList>  implements Comparable<MiArrayList> {

        private String nombre,descri, precio,informacion;
        private Drawable img;
        private int idradiobuton;
    public MiArrayList (String nombre, String descri, String precio, String informacion, Drawable img,int idradiobutton) {
        this.nombre=nombre;
        this.descri=descri;
        this.precio=precio;
        this.img=img;
        this.informacion=informacion;
        this.idradiobuton=idradiobutton;
    }

    public Drawable getImg() {
        return img;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
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



        public int compareTo(MiArrayList vino) {
            int ct1 = this.nombre.compareTo(vino.nombre);
            if(ct1==0){
                Collator coll = Collator.getInstance(Locale.getDefault());
                ct1 = coll.compare(this.nombre, vino.nombre);
            }
            return ct1; }


}
