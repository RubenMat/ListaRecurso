package com.example.dm2.listarecurso;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<Web> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listaWeb);

        try{
            Resources res = getResources();
            BufferedReader br = new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.webs)));
            String leido = br.readLine();
            while(leido!=null){
                String[] partes = leido.split(";");
                if(partes[2].trim().equalsIgnoreCase("bing")){
                    datos.add(new Web(partes[0],partes[1],R.drawable.bing,partes[3]));
                } else if(partes[2].trim().equalsIgnoreCase("yahoo")){
                    datos.add(new Web(partes[0],partes[1],R.drawable.yahoo,partes[3]));
                } else {
                    datos.add(new Web(partes[0],partes[1],R.drawable.google,partes[3]));
                }
                leido=br.readLine();
            }
        }catch(IOException e){

        }

        AdaptadorWebs adaptador = new AdaptadorWebs(this,datos);
        listView.setAdapter(adaptador);

    }

    public class Web{

        String nombre,url,indice;
        int imagen;

        public Web(String nombre, String url, int imagen, String indice){
            this.nombre=nombre;
            this.url=url;
            this.imagen=imagen;
            this.indice=indice;
        }

        public String getNombre() {
            return nombre;
        }

        public int getImagen() {
            return imagen;
        }

        public String getIndice() {
            return indice;
        }

        public String getUrl() {
            return url;
        }
    }
    class AdaptadorWebs extends ArrayAdapter<Web> {
        public AdaptadorWebs(Context context, List<Web> datos) {
            super(context, R.layout.listawebs, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listawebs, null);

            TextView lblNombre= (TextView) item.findViewById(R.id.nombreWeb);
            lblNombre.setText(getItem(position).getNombre());
            TextView lblURL = (TextView)item.findViewById(R.id.urlWeb);
            lblURL.setText(getItem(position).getUrl());
            ImageView imagen =(ImageView)item.findViewById(R.id.imgWeb);
            imagen.setImageResource(getItem(position).getImagen());
            TextView indice = (TextView)item.findViewById(R.id.indiceWeb);
            indice.setText(getItem(position).getIndice());
            return (item);
        }
    }
}
