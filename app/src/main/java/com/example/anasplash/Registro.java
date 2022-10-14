package com.example.anasplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.anasplash.Json.MyInfo;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Registro extends AppCompatActivity implements View.OnClickListener {
    Switch SwitchM;
    EditText NumeroMas;
    Button Regresar;
    Button button4;
    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    byte []res = null;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        SwitchM= (Switch) findViewById(R.id.idswitch);

        NumeroMas = (EditText) findViewById(R.id.NumeroM);
        Regresar = (Button)findViewById(R.id.button5);
        setContentView(R.layout.activity_registro);
        button4 = findViewById(R.id.button4);
        Button button4 = findViewById(R.id.button4);
        List<MyInfo> list =new ArrayList<MyInfo>();
        Regresar = findViewById(R.id.button5);
        Button button2 = findViewById(R.id.button5);
        EditText usuario = findViewById(R.id.TxtUsu);
        EditText pswd = findViewById(R.id.TxtContra);

        EditText mail = findViewById(R.id.TxtCorreo);
        EditText edad = findViewById(R.id.TxtEdad);
        RadioButton r1 = findViewById(R.id.radioBMujer);
        RadioButton r2 = findViewById(R.id.radioBHombre);
        CheckBox tipo = findViewById(R.id.checkBoxEsTra);
        CheckBox tipo2 = findViewById(R.id.checkBoxFam);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Registro.this, Login.class);
                startActivity(registro);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                res = createSha1(String.valueOf(pswd.getText()));
                if( res != null ) {
                    Log.d(TAG, String.format("%s", bytesToHex(res)));
                    pass = bytesToHex(res);
                }

                MyInfo info= new MyInfo();
                info.setUsuario(String.valueOf(usuario.getText()));
                info.setPassword(pass);
                info.setCorreo(String.valueOf(mail.getText()));
                List2Json(info,list);
            }
        }); SwitchM.setOnClickListener(this);
    }

    public void onclick(View view) {


    }
    public void Objet2Json(MyInfo info){
        Gson gson =null;
        String json= null;
        String mensaje = null;
        gson =new Gson();
        json = gson.toJson(info);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "object2Json OK";
        } else {
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.idswitch){
            Log.d(TAG, "Esta entrando al if");

            if (SwitchM.isChecked()){
                Log.d(TAG, "Esta entrando al true");
                NumeroMas.setText("Numero de Mascotas");
                NumeroMas.setEnabled(true);
            }
            else{
                Log.d(TAG, "Esta entrando al false");
                NumeroMas.setText("Campo desabilitado");
                NumeroMas.setEnabled(false);
            }
        }

    }
    public byte[] createSha1( String text )
    {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}