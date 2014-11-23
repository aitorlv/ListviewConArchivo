package com.example.aitor.vinoteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.util.Log;
import android.util.Xml;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MyActivity extends Activity {
    private static final int MODIFICAR=1;
    private ArrayList<Vino> valores = new ArrayList<Vino>();
    private Adapter ad;
    private ListView lista;
    private int indicemod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        inicializar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        leerArchivo();
    }


    @Override
    protected void onPause() {
        super.onPause();
        guardarArchivo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        leerArchivo();
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


          //Vino vino=new Vino("nombre","descsri","precio","info","img",1);
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
        //Vino vh=(Vino)o;
        Adapter.ViewHolder vh = (Adapter.ViewHolder) o;


        if (id == R.id.action_modificar) {
            modificarItem(vh);

        } else if (id == R.id.action_borrar) {
            tostada(vh.tvnombre.getText().toString());
           borrarItem(vh);
        }
        return super.onContextItemSelected(item);

    }


    /*--------------------------------------------mis metodos----------------------------------------------------*/


    //inicializamos el list view y lo mostramos
    public void inicializar() {

        valores=leerArchivo();
        Collections.sort(valores);
        lista = (ListView) findViewById(R.id.lista);
        ad = new Adapter(this, R.layout.fila, valores);
        lista.setAdapter(ad);
        ad.notifyDataSetChanged();
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
        if(valores.get(posicion).getImg().compareTo("tinto")==0) {
            img.setImageDrawable(getResources().getDrawable(R.drawable.tinto));
        }else if(valores.get(posicion).getImg().compareTo("rosado")==0){
            img.setImageDrawable(getResources().getDrawable(R.drawable.rosado));
        }else if(valores.get(posicion).getImg().compareTo("blanco")==0){
            img.setImageDrawable(getResources().getDrawable(R.drawable.blanco));
        }else{
            img.setImageDrawable(getResources().getDrawable(R.drawable.vacio));
        }
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
                //Drawable foto;
                Vino obj;
                nombre=etnombre.getText().toString();
                descri=etdescri.getText().toString();
                precio=etprecio.getText().toString()+"€";
                informacion=etinformacion.getText().toString();
                int idseleccionado = rb.getCheckedRadioButtonId();
               if(Validar.validarDatos(nombre,descri,precio,informacion)) {
                   if (idseleccionado == R.id.rb1) {
                       String foto="tinto";
                       obj = new Vino(nombre, descri, precio,informacion, foto,R.id.rb1);

                   } else if (idseleccionado == R.id.rb2) {
                       String foto="rosado";
                       obj = new Vino(nombre, descri, precio,informacion, foto,R.id.rb2);
                   } else if (idseleccionado == R.id.rb3) {
                       String foto="blanco";
                       obj = new Vino(nombre, descri, precio,informacion, foto,R.id.rb3);
                   } else {
                       String foto="vacio";
                       obj = new Vino(nombre, descri, precio,informacion, foto,idseleccionado);
                   }
                   if(!valores.contains(obj)){
                     valores.add(obj);
                     Collections.sort(valores);
                     ad.notifyDataSetChanged();
                     tostada("Datos insertados");
                   }else{
                     tostada("Ese vino ya existe");
                   }

               }else{
                    tostada("Datos no validos ");
               }

            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();

    }

    //Si tenemos permisos crea el archivo en la memoria
    public void guardarArchivo(){
        if(Validar.isModificable()) {
            FileOutputStream fxml = null;
            try {
                fxml = new FileOutputStream(new File(getExternalFilesDir(null), "archivo.xml"));
                XmlSerializer docxml = Xml.newSerializer();
                docxml.setOutput(fxml, "UTF-8");
                docxml.startDocument(null, Boolean.valueOf(true));
                docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                docxml.startTag(null, "vinos");
                for (int i = 0; i < valores.size(); i++) {
                    Vino vino = (Vino) valores.get(i);
                    docxml.startTag(null, "vino");
                    docxml.startTag(null, "nombre");
                    docxml.text(vino.getNombre());
                    docxml.endTag(null, "nombre");
                    docxml.startTag(null, "descripcion");
                    docxml.text(vino.getDescri());
                    docxml.endTag(null, "descripcion");
                    docxml.startTag(null, "foto");
                    docxml.text(vino.getImg());
                    docxml.endTag(null, "foto");
                    docxml.startTag(null, "informacion");
                    docxml.text(vino.getInformacion());
                    docxml.endTag(null, "informacion");
                    docxml.startTag(null, "precio");
                    docxml.text(vino.getPrecio());
                    docxml.endTag(null, "precio");
                    docxml.startTag(null, "idradio");
                    docxml.text(vino.getIdradiobuton() + "");
                    docxml.endTag(null, "idradio");
                    docxml.endTag(null, "vino");
                }
                docxml.endDocument();
                docxml.flush();
                fxml.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //si tenemos permisos lee el archivo creado anterioromente
    public ArrayList<Vino> leerArchivo() {
        if (Validar.isModificable()) {
            String nombre = "", descri = "", img = "", info = "", pre = "", idradio = "";
            int radio = 0;
            ArrayList<Vino> valoresArchivo = new ArrayList<Vino>();
            XmlPullParser leerxml = Xml.newPullParser();
            try {
                leerxml.setInput(new FileInputStream(new File(getExternalFilesDir(null), "archivo.xml")), "utf-8");
                int evento = leerxml.getEventType();

                while (evento != XmlPullParser.END_DOCUMENT) {
                    if (evento == XmlPullParser.START_TAG) {
                        String etiqueta = leerxml.getName();
                        if (etiqueta.compareTo("nombre") == 0) {
                            nombre = leerxml.nextText();
                        } else if (etiqueta.compareTo("descripcion") == 0) {
                            descri = leerxml.nextText();
                        } else if (etiqueta.compareTo("foto") == 0) {
                            img = leerxml.nextText();
                        } else if (etiqueta.compareTo("informacion") == 0) {
                            info = leerxml.nextText();
                        } else if (etiqueta.compareTo("precio") == 0) {
                            pre = leerxml.nextText();
                        } else if (etiqueta.compareTo("idradio") == 0) {
                            idradio = leerxml.nextText();
                            radio = Integer.parseInt(idradio);
                            valoresArchivo.add(new Vino(nombre, descri, pre, info, img, radio));
                        }
                    }
                    evento = leerxml.next();
                }


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return valoresArchivo;
        }
        tostada("Permisos no validos");
        return null;
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


    public void modificarItem(Adapter.ViewHolder vh){
        Intent i =new Intent(this,Modificar.class);
        Bundle b=new Bundle();
        indicemod=vh.posicion;
        Vino v=(Vino)valores.get(vh.posicion);
        b.putParcelable("vino",v);
        i.putExtra("objetovino",b);
        startActivityForResult(i,MODIFICAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            switch (requestCode){
                case MODIFICAR:
                    Bundle b=data.getParcelableExtra("vino");
                    Vino objmod=b.getParcelable("obj");
                    if(!valores.contains(objmod)){
                           valores.set(indicemod, objmod);
                           Collections.sort(valores);
                           ad.notifyDataSetChanged();
                           tostada("datos modificados");
                    }else{
                      tostada("el vino ua existe");
                   }
                break;
            }
        }else{
            tostada("modificacion cancelada");
        }




    }

    //metodo usado para sacar mensajes
    public void tostada(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
