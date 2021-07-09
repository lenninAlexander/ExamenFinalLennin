package com.example.miexamenfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.miexamenfinal.clases.Pokemon;
import com.example.miexamenfinal.services.PokemonService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class nuevoPokemon extends AppCompatActivity implements View.OnClickListener {
    private Button btnGaleria;
    private Button btnGuardar;

    private ImageView imageView;

    private EditText editTextNombre;
    private EditText editTextTipo;
    private EditText editTextLatitude;
    private EditText editTextLongitude;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;


    @Override
    public void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_pokemon);
        editTextNombre =(EditText)findViewById(R.id.txtNombrePokemon);
        editTextTipo =(EditText)findViewById(R.id.txttipo);
        editTextLatitude =(EditText)findViewById(R.id.txtlatitude);
        editTextLongitude=(EditText)findViewById(R.id.txtlongitude);
        editTextTipo =(EditText)findViewById(R.id.txttipo);
        imageView  =(ImageView)findViewById(R.id.imageViewPokemon);
        btnGaleria = (Button) findViewById(R.id.btnCargarG);
        btnGuardar = (Button) findViewById(R.id.btnGuardarPokemon);

        Button guardar = findViewById(R.id.btnGuardarPokemon);
        btnGaleria.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre =editTextNombre.getText().toString().trim();
                String tipo = editTextTipo.getText().toString().trim();
                double latitude = Double.parseDouble(editTextLatitude.getText().toString().trim());
                double longitude = Double.parseDouble(editTextLongitude.getText().toString().trim());
                String imagen = getStringImagen(bitmap);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PokemonService  service = retrofit.create(PokemonService.class);
                Pokemon pokemon = new Pokemon();
                pokemon.setNombre(nombre);
                pokemon.setTipo(tipo);
                pokemon.setUrl_imagen(imagen);
                pokemon.setLatitude(latitude);
                pokemon.setLongitude(longitude);
                Call<Pokemon> call2 =service.create(pokemon);
                call2.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call2, Response<Pokemon> response) {
                        if(response.code()==200){
                            Log.i("Internet","Guardado");
                        }else
                        {
                            Log.i("Internet","No Guardado");
                        }
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call2, Throwable t) {
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

        if(v == btnGaleria){
            showFileChooser();
        }
    }
}
