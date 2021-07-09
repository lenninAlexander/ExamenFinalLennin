package com.example.miexamenfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miexamenfinal.clases.Maestro;
import com.example.miexamenfinal.services.MaestroService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class nuevoMaestro extends AppCompatActivity implements View.OnClickListener {

    private Button btnBuscar;
    private Button btnGuardar;

    private ImageView imageView;

    private EditText editTextName;
    private EditText editTextPueblo;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;


    @Override
    public void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_maestro);
        editTextName =(EditText)findViewById(R.id.txtNombreMaestro);
        editTextPueblo =(EditText)findViewById(R.id.txtPueblo);
        imageView  =(ImageView)findViewById(R.id.imageViewPokemon);
        btnBuscar = (Button) findViewById(R.id.btnCargarGaleria);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        Button crear = findViewById(R.id.btnGuardar);
        btnBuscar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombres =editTextName.getText().toString().trim();
                String pueblo = editTextPueblo.getText().toString().trim();
                String imagen = getStringImagen(bitmap);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MaestroService service = retrofit.create(MaestroService.class);
                Maestro maestro = new Maestro();
                maestro.setNombres(nombres);
                maestro.setPueblo(pueblo);
                maestro.setImagen(imagen);
                Call<Maestro> call =service.create(maestro);
                call.enqueue(new Callback<Maestro>() {
                    @Override
                    public void onResponse(Call<Maestro> call, Response<Maestro> response) {
                                if(response.code()==200){
                                        Log.i("Internet","Guardado");
                                }else
                                {
                                    Log.i("Internet","No se puede guardar");
                                }
                    }

                    @Override
                    public void onFailure(Call<Maestro> call, Throwable t) {
                        Log.i("Internet","No se puede conectar al servidor");
                    }
                });

            }
        });

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public void onClick(View v) {

        if(v == btnBuscar){
            showFileChooser();
        }
    }

}
