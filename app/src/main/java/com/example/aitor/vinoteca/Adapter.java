package com.example.aitor.vinoteca;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 05/10/2014.
 */
public class Adapter extends ArrayAdapter <MiArrayList> {

        private Context contexto;
        private ArrayList <MiArrayList> lista;
        private int recurso;
        private static LayoutInflater inflater;

    public static class ViewHolder{
        public TextView tvnombre,tvdescri,tvprecio;
        public int posicion,idradiobutton;
        public ImageView img;
    }

    public Adapter(Context context, int resource, ArrayList <MiArrayList> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.lista=objects;
        this.recurso=resource;
        this.inflater=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       //Log.v("LOG",lista.size()+"");
       ViewHolder vh=null;
        if (convertView==null){
            convertView=inflater.inflate(recurso,null);
            vh=new ViewHolder();
            vh.img=(ImageView)convertView.findViewById(R.id.Image);
           vh.tvnombre=(TextView) convertView.findViewById(R.id.Nombre);
        vh.tvdescri=(TextView) convertView.findViewById(R.id.descri);
        vh.tvprecio=(TextView) convertView.findViewById(R.id.precio);
            convertView.setTag(vh);
        }else{

           vh=(ViewHolder)convertView.getTag();
        }

        MiArrayList obj= (MiArrayList)getItem(position);
       //Log.v("nobmre",obj.getNombre().toString()+"");
        vh.posicion=position;
        vh.img.setImageDrawable(obj.getImg());
        vh.tvnombre.setText(obj.getNombre()+"");
        vh.tvdescri.setText(obj.getDescri()+"");
        vh.tvprecio.setText(obj.getPrecio()+"€");
        vh.idradiobutton=obj.getIdradiobuton();
        return convertView;
    }
}
