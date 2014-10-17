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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MyActivity extends Activity {
   private ArrayList <MiArrayList> valores=new ArrayList<MiArrayList>();
    private Adapter ad;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    lista=(ListView)findViewById(R.id.lista);

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
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.action_insertar){
            insertarItem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contextual,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


         Object o= info.targetView.getTag();
        Adapter.ViewHolder vh=(Adapter.ViewHolder)o;


        if(id==R.id.action_modificar){
            modificarItem(vh);

        }else if(id==R.id.action_borrar){
            //tostada(vh.tvnombre.getText().toString());
            borrarItem(vh);
        }
        return super.onContextItemSelected(item);

    }

    /*--------------------------------------------mis metodos----------------------------------------------------*/


        //inicializamos el list view y lo mostramos
    public void inicializar(){
       // Drawable img=this.getResources().getDrawable(R.drawable.tinto);

        //for (int i=0;i<10;i++) {
        //MiArrayList objeto = new MiArrayList("ddd"+i, "ccc"+i, 1,img);
        //valores.add(objeto);
        //}
        ad=new Adapter(this,R.layout.fila,valores);
        lista.setAdapter(ad);
        registerForContextMenu(lista);

    }
        //metodo para insnertar valores en el listview
    public void insertarItem(){

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        final View view=inflater.inflate(R.layout.dialogoinsertar,null);
        final EditText etnombre=(EditText)view.findViewById(R.id.etnombre);
        final EditText etdescri=(EditText)view.findViewById(R.id.etdescri);
        final EditText etprecio=(EditText)view.findViewById(R.id.etprecio);
        final RadioGroup  rb=(RadioGroup)view.findViewById(R.id.rb);
        alert.setView(view);
        alert.setCancelable(false);

        alert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre,descri,precio;
                Drawable foto;

                MiArrayList obj;
                nombre=etnombre.getText().toString();
                descri=etdescri.getText().toString();
                precio=etprecio.getText().toString();
                int idseleccionado = rb.getCheckedRadioButtonId();

               if(validarDatos(nombre,descri,precio)) {

                   if (idseleccionado == 2131230725) {
                       foto = view.getResources().getDrawable(R.drawable.tinto);
                       obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);

                   } else if (idseleccionado == 2131230726) {
                       foto = view.getResources().getDrawable(R.drawable.rosado);
                       obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);
                   } else if (idseleccionado == 2131230727) {
                       foto = view.getResources().getDrawable(R.drawable.blanco);
                       obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);
                   } else {
                       foto = view.getResources().getDrawable(R.drawable.vacio);
                       obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);
                   }
                   valores.add(obj);
                   ad.notifyDataSetChanged();
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
        alert.setMessage("¿Borrar "+vh.tvnombre.getText()+" de la lista?");
       //TextView tvborrar=(TextView)findViewById(R.id.tvborrar);
       //tvborrar.setText("¿Seguro que quieres borrar a "+vh.tvnombre.getText()+"?");
        alert.setView(view);
        //alert.setCancelable(false);
        alert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valores.remove(vh.posicion);
                ad.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();



    }

    public void modificarItem(final Adapter.ViewHolder vh){


        final EditText etmod1,etmod2,etmod3;
        final  RadioGroup rgmod;
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogomodificar, null);
        alert.setView(vista);

        etmod1 = (EditText) vista.findViewById(R.id.etmodnombre);
        etmod2 = (EditText) vista.findViewById(R.id.etmoddescri);
        etmod3 = (EditText) vista.findViewById(R.id.etmodprecio);
        rgmod=(RadioGroup)vista.findViewById(R.id.rbmod);
        etmod1.setText(vh.tvnombre.getText().toString());
        etmod2.setText(vh.tvdescri.getText().toString());
        etmod3.setText(vh.tvprecio.getText().toString());
        if(vh.idradiobutton== 2131230725){
            rgmod.check(R.id.rbmod1);
        }else if(vh.idradiobutton== 2131230726){
            rgmod.check(R.id.rbmod2);
        }else if(vh.idradiobutton== 2131230727){
            rgmod.check(R.id.rbmod3);
        }
        alert.setPositiveButton("modificar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String nombre,descri,precio;
                        Drawable foto;
                        MiArrayList obj;
                        nombre=etmod1.getText().toString();
                        descri=etmod2.getText().toString();
                        precio=etmod3.getText().toString();
                        int idseleccionado = rgmod.getCheckedRadioButtonId();

                        if(validarDatos(nombre,descri,precio)) {

                            tostada(idseleccionado+"");
                            if (idseleccionado == 2131230732) {
                                foto = vista.getResources().getDrawable(R.drawable.tinto);
                                obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);

                            } else if (idseleccionado == 2131230733) {
                                foto = vista.getResources().getDrawable(R.drawable.rosado);
                                obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);
                            } else if (idseleccionado == 2131230734) {
                                foto = vista.getResources().getDrawable(R.drawable.blanco);
                                obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);
                            } else {
                                foto = vista.getResources().getDrawable(R.drawable.vacio);
                                obj = new MiArrayList(nombre, descri, precio, foto,idseleccionado);
                            }
                            int index=vh.posicion;
                            valores.set(index,obj);
                            ad.notifyDataSetChanged();
                        }else{
                            tostada("Datos no validos ");
                        }

                        //String c = et1.getText().toString() + " " + et2.getText().toString();
                        //c = c.trim();
                        //if (c.length() > 0) {
                          //  datosv2.set(index, c);
                            //ad.notifyDataSetChanged();
                            //tostada("elemento modificado");
                    //    }
                    }
                });
        alert.setNegativeButton("cancelar",null);
        alert.show();
        //tostada("elemento añadido");

    }

        //valida los datos introducidos por el usuario
    public boolean validarDatos(String nombre,String descri,String precio){

        nombre=nombre.trim();
        descri=descri.trim();
        precio=precio.trim();

        if(nombre.length()>0 && descri.length()>0 && precio.length()>0)
        return true;
        else
        return false;

    }

       //metodo usado para sacar mensajes
    public void tostada(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
