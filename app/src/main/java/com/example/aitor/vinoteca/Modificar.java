package com.example.aitor.vinoteca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.IOException;
import java.util.Collections;


public class Modificar extends Activity {
    private EditText etmod1, etmod2, etmod3, etmod4;
    private RadioGroup rgmod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        Bundle b = this.getIntent().getParcelableExtra("objetovino");
        if (b != null) {
            Vino v;
            v = b.getParcelable("vino");
            mostrarValores(v);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modificar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mostrarValores(Vino obj) {
        etmod1 = (EditText) findViewById(R.id.etmodnombre);
        etmod2 = (EditText) findViewById(R.id.etmoddescri);
        etmod3 = (EditText) findViewById(R.id.etmodprecio);
        etmod4 = (EditText) findViewById(R.id.etmodinformcaion);
        rgmod = (RadioGroup) findViewById(R.id.rbmod);
        etmod1.setText(obj.getNombre().toString());
        etmod2.setText(obj.getDescri().toString());
        etmod3.setText(obj.getPrecio().toString());
        etmod4.setText(obj.getInformacion().toString());
        if (obj.getIdradiobuton() == R.id.rb1 || obj.getIdradiobuton() == R.id.rbmod1) {
            rgmod.check(R.id.rbmod1);
        } else if (obj.getIdradiobuton() == R.id.rb2 || obj.getIdradiobuton() == R.id.rbmod2) {
            rgmod.check(R.id.rbmod2);
        } else if (obj.getIdradiobuton() == R.id.rb3 || obj.getIdradiobuton() == R.id.rbmod3) {
            rgmod.check(R.id.rbmod3);
        }
    }

    public void modificar(View v) {
        String nombre, descri,foto, precio, informacion;
        Vino vino;
        nombre = etmod1.getText().toString();
        descri = etmod2.getText().toString();
        precio = etmod3.getText().toString();
        informacion = etmod4.getText().toString();
        int idseleccionado = rgmod.getCheckedRadioButtonId();

        if (Validar.validarDatos(nombre, descri, precio, informacion)) {
            if (idseleccionado == R.id.rbmod1) {
                 foto ="tinto";
                vino = new Vino(nombre, descri, precio, informacion, foto, idseleccionado);

            } else if (idseleccionado == R.id.rbmod2) {
                foto = "rosado";
                vino = new Vino(nombre, descri, precio, informacion, foto, idseleccionado);
            } else if (idseleccionado == R.id.rbmod3) {
                foto = "blanco";
                vino = new Vino(nombre, descri, precio, informacion, foto, idseleccionado);
            } else {
                 foto = "vacio";
                vino = new Vino(nombre, descri, precio, informacion, foto, idseleccionado);
            }
            try {
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("obj", vino);
                i.putExtra("vino",bundle);
                setResult(Activity.RESULT_OK, i);

            }catch (InternalError e){
                Intent i=new Intent();
                setResult(Activity.RESULT_OK,i);
            }
            finish();

        } else {
            //tostada("Datos no validos ");
        }

    }

    public void cancelar(View v){
        Intent i=new Intent();
        setResult(Activity.RESULT_CANCELED,i);
        finish();
    }

}

