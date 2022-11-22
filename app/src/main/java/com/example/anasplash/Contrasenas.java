package com.example.anasplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anasplash.Json.MyData;
import com.example.anasplash.Json.MyInfo;
import com.example.anasplash.MyAdapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Contrasenas extends AppCompatActivity {
    private ListView listView;
    Button regresor;
    private TextView usuario;
    private List<MyData> list;
    private int []images = { R.drawable.llave,R.drawable.cerrar};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenas);
        String aux = null;
        MyInfo info = null;
        Object object = null;
        MyData myData = null;
        usuario = findViewById(R.id.txtUser);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<MyData>();
        if( intent != null)
        {
            aux = intent.getStringExtra("Usuario" );
            if( aux != null && aux.length()> 0 )
            {
                usuario.setText(aux);
            }
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("MyInfo");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        info = (MyInfo) object;
                        usuario.setText("Usuario: " + info.getUsuario() + "estas son tus contraseñas");
                        list = info.getContras();
                    }
                }
            }
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
    //de aqui lo del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        MyInfo info = null;
        Object object = null;
        Intent intent = getIntent();
        object = intent.getExtras().get("MyInfo");
        info = (MyInfo) object;
        switch (item.getItemId() ) {
            case R.id.agregarId:
                Intent olvideContra = new Intent(Contrasenas.this, Agregarcontra.class);
                olvideContra.putExtra("MyInfo", info);
                startActivity(olvideContra);
                break;
            case R.id.elimId:
                Intent elimContra = new Intent(Contrasenas.this, eliminacontra.class);
                elimContra.putExtra("MyInfo", info);
                startActivity(elimContra);
                break;
            case R.id.editarId:
                Intent editaContra = new Intent(Contrasenas.this, editacontra.class);
                editaContra.putExtra("MyInfo", info);
                startActivity(editaContra);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
        //fin del menu

    private void toast(int i) {
        Toast.makeText(getBaseContext(), list.get(i).getContra(), Toast.LENGTH_SHORT).show();
    }

}