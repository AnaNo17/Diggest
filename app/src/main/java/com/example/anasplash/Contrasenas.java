package com.example.anasplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anasplash.Json.MyData;
import com.example.anasplash.Json.MyInfo;
import com.example.anasplash.MyAdapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Contrasenas extends AppCompatActivity {
    private ListView listView;
    Button regresor;
    private List<MyData> list;
    private int[] images = {R.drawable.feisbu, R.drawable.insta, R.drawable.correo};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenas);
        String aux = null;
        MyInfo info = null;
        Object object = null;
        MyData myData = null;
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<MyData>();
        for (int i = 0; i < 3; i++) {
            myData = new MyData();
            myData.setContra(String.format("Contraseña%d", (int) (Math.random() * 10000)));
            if (i == 0) {
                myData.setRed(String.format("Facebook"));
                myData.setImage(images[0]);
            }
            if (i == 1) {
                myData.setRed(String.format("Instagram"));
                myData.setImage(images[1]);
            }
            if (i == 2) {
                myData.setRed(String.format("Correo"));
                myData.setImage(images[2]);
            }
            list.add(myData);
        }

        MyAdapter myAdapter = new MyAdapter(list, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast( i );
            }
        });
        regresor = (Button)findViewById(R.id.regreso);
        regresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresador = new Intent(Contrasenas.this, Principal.class);
                startActivity(regresador);
            }
        });

    }

    private void toast(int i) {
        Toast.makeText(getBaseContext(), list.get(i).getContra(), Toast.LENGTH_SHORT).show();
    }

}