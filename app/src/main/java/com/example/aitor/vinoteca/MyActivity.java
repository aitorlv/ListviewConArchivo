package com.example.aitor.vinoteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class MyActivity extends Activity {
    private ArrayList<MiArrayList> valores = new ArrayList<MiArrayList>();
    private Adapter ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        inicializar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      if (id == R.id.action_insertar) {
            insertarItem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        Object o = info.targetView.getTag();
        Adapter.ViewHolder vh = (Adapter.ViewHolder) o;


        if (id == R.id.action_modificar) {
            modificarItem(vh);

        } else if (id == R.id.action_borrar) {
            //tostada(vh.tvnombre.getText().toString());
            borrarItem(vh);
        }
        return super.onContextItemSelected(item);

    }

    /*--------------------------------------------mis metodos----------------------------------------------------*/


    //inicializamos el list view y lo mostramos
    public void inicializar() {
        // Drawable img=this.getResources().getDrawable(R.drawable.tinto);


        MiArrayList objeto = new MiArrayList("Txacoli","Pais Vasco","5.45€","El chacolí (txakolin en euskera) es un vino blanco que se produce en el País Vasco," +
                " con producciones menores en Cantabria y Burgos, " +
                "e incluso en algunos lugares de Chile.Se elabora a partir de uvas verdes," +
                "lo que provoca una cierta acidez.",getResources().getDrawable(R.drawable.blanco)
                ,2131230728);

        MiArrayList objeto1 = new MiArrayList("Lambrusco","Italia","2e","El lambrusco es en primer lugar un tipo de vid con 40 variedades." +
                "La variedad de uva y el vino Lambrusco se dan concretamente en las provincias de Módena, " +
                "Bolonia, Parma, y Reggio Emilia.",getResources().getDrawable(R.drawable.rosado)
                ,2131230729);

        MiArrayList objeto2 = new MiArrayList("Cerrojo","Granada","30€","El cerrojo es un vino tinto que se produce en Granada, se elabora a partir de " +
                "las uvas Tempranillo, cabernet y garnacha tiene como denominacion de origen  Vino de la tierra altiplano de Sierra Nevada ",getResources().getDrawable(R.drawable.tinto)
                ,2131230727);
        valores.add(objeto);
        valores.add(objeto1);
        valores.add(objeto2);

        final ListView lista = (ListView) findViewById(R.id.lista);;
        ad = new Adapter(this, R.layout.fila, valores);
        lista.setAdapter(ad);


            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mostrarInformacion(position);

                }
            });
        registerForContextMenu(lista);

    }


    //metodo para mostrar la informacion al hacer click en un item

    public void mostrarInformacion(int posicion){
        ImageView img;
        TextView tv1,tv2,info;
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        final View vista=inflater.inflate(R.layout.mensaje,null);
        img=(ImageView)vista.findViewById(R.id.imagenmen);
        tv1=(TextView)vista.findViewById(R.id.nombremen);
        tv2=(TextView)vista.findViewById(R.id.preciomen);
        info=(TextView)vista.findViewById(R.id.infomen);
        img.setImageDrawable(valores.get(posicion).getImg());
        tv1.setText(valores.get(posicion).getNombre());
        tv2.setText(valores.get(posicion).getPrecio());
        info.setText(valores.get(posicion).getInformacion());
        alert.setView(vista);

        alert.setNegativeButton("Cerrar",null);
        alert.show();
    }


        //metodo para insnertar valores en el listview
    public void insertarItem(){

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        final View view=inflater.inflate(R.layout.dialogoinsertar, null);
        final EditText etnombre=(EditText)view.findViewById(R.id.etnombre);
        final EditText etdescri=(EditText)view.findViewById(R.id.etdescri);
        final EditText etprecio=(EditText)view.findViewById(R.id.etprecio);
        final EditText etinformacion=(EditText)view.findViewById(R.id.etinformacion);
        final RadioGroup  rb=(RadioGroup)view.findViewById(R.id.rb);
        alert.setView(view);
        alert.setCancelable(false);

        alert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre,descri,precio,informacion;
                Drawable foto;

                MiArrayList obj;
                nombre=etnombre.getText().toString();
                descri=etdescri.getText().toString();
                precio=etprecio.getText().toString()+"€";
                informacion=etinformacion.getText().toString();
                int idseleccionado = rb.getCheckedRadioButtonId();

               if(validarDatos(nombre,descri,precio,informacion)) {
                   if (idseleccionado == 2131230727) {
                       foto = view.getResources().getDrawable(R.drawable.tinto);
                       obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);

                   } else if (idseleccionado == 2131230729) {
                       foto = view.getResources().getDrawable(R.drawable.rosado);
                       obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);
                   } else if (idseleccionado == 2131230728) {
                       foto = view.getResources().getDrawable(R.drawable.blanco);
                       obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);
                   } else {
                       foto = view.getResources().getDrawable(R.drawable.vacio);
                       obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);
                   }
                   valores.add(obj);
                   Collections.sort(valores);
                   ad.notifyDataSetChanged();
                   tostada("Datos insertados");
               }else{
                    tostada("Datos no validos ");
               }

            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();

    }



     // borrar item del listview
    public void borrarItem(final Adapter.ViewHolder vh){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        final View view=inflater.inflate(R.layout.dialogoborrar,null);
        alert.setMessage("¿Borrar " + vh.tvnombre.getText() + " de la lista?");
        alert.setView(view);
        //alert.setCancelable(false);
        alert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valores.remove(vh.posicion);
                ad.notifyDataSetChanged();
                tostada("Datos borrados");
            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();



    }

    public void modificarItem(final Adapter.ViewHolder vh){


        final EditText etmod1,etmod2,etmod3,etmod4;
        final  RadioGroup rgmod;
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogomodificar, null);
        alert.setView(vista);


        etmod1 = (EditText) vista.findViewById(R.id.etmodnombre);
        etmod2 = (EditText) vista.findViewById(R.id.etmoddescri);
        etmod3 = (EditText) vista.findViewById(R.id.etmodprecio);
        etmod4=(EditText)vista.findViewById(R.id.etmodinformcaion);
        rgmod=(RadioGroup)vista.findViewById(R.id.rbmod);
        etmod1.setText(vh.tvnombre.getText().toString());
        etmod2.setText(vh.tvdescri.getText().toString());
        etmod3.setText(vh.tvprecio.getText().toString());
        etmod4.setText(vh.informacion);
        if(vh.idradiobutton== 2131230727 || vh.idradiobutton== 2131230735){
            rgmod.check(R.id.rbmod1);
        }else if(vh.idradiobutton== 2131230729 || vh.idradiobutton== 2131230736){
            rgmod.check(R.id.rbmod2);
        }else if(vh.idradiobutton== 2131230728 || vh.idradiobutton== 2131230737){
            rgmod.check(R.id.rbmod3);
        }
        alert.setPositiveButton("modificar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String nombre,descri,precio,informacion;
                        Drawable foto;
                        MiArrayList obj;
                        nombre=etmod1.getText().toString();
                        descri=etmod2.getText().toString();
                        precio=etmod3.getText().toString();
                        informacion=etmod4.getText().toString();
                        int idseleccionado = rgmod.getCheckedRadioButtonId();

                        if(validarDatos(nombre,descri,precio,informacion)) {


                            if (idseleccionado == 2131230735) {
                                foto = vista.getResources().getDrawable(R.drawable.tinto);
                                obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);

                            } else if (idseleccionado == 2131230736) {
                                foto = vista.getResources().getDrawable(R.drawable.rosado);
                                obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);
                            } else if (idseleccionado == 2131230737) {
                                foto = vista.getResources().getDrawable(R.drawable.blanco);
                                obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);
                            } else {
                                foto = vista.getResources().getDrawable(R.drawable.vacio);
                                obj = new MiArrayList(nombre, descri, precio,informacion, foto,idseleccionado);
                            }
                            int index=vh.posicion;
                            valores.set(index,obj);
                            Collections.sort(valores);
                            ad.notifyDataSetChanged();
                            tostada("datos modificados");
                        }else{
                            tostada("Datos no validos ");
                        }


                    }
                });
        alert.setNegativeButton("cancelar",null);
        alert.show();

    }

        //valida los datos introducidos por el usuario
    public boolean validarDatos(String nombre,String descri,String precio,String informacion){

        nombre=nombre.trim();
        descri=descri.trim();
        precio=precio.trim();
        informacion=informacion.trim();
        if(nombre.length()>0 && descri.length()>0 && precio.length()>0 && informacion.length()>0)
        return true;
        else
        return false;

    }

       //metodo usado para sacar mensajes
    public void tostada(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
