package com.example.examen3p01cristofer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class detalle extends AppCompatActivity {

    private Context context;
    private EditText txtDescripcion, txtCantidad, txtTiempo, txtPeriocidad;
    private Button btnRegistrar, btnactualizar;

    private  Medicamentos medicamentos;
    ArrayList<Medicamentos> list;
    public static MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);


        list = new ArrayList<>();
        txtDescripcion = findViewById(R.id.txtDescripciond);
        txtCantidad = findViewById(R.id.txtCantidadd);
        txtTiempo = findViewById(R.id.txtTiempod);
        txtPeriocidad = findViewById(R.id.txtPeriocidadd);
        myAdapter = new MyAdapter(this,list);
        context = getApplicationContext();
        Button actualizar= (Button) findViewById(R.id.btnModificar);
        medicamentos = (Medicamentos) getIntent().getExtras().getSerializable("itemDetalle");


        //Glide.with(this.context).load(user.getUrl()).into(holder.foto);
        // Glide.with (context) .load (user.getUrl()).into(foto);
        txtDescripcion.setText(medicamentos.getDescripcion());
        txtCantidad.setText(medicamentos.getCantidad());
        txtTiempo.setText(medicamentos.getTiempo());
        txtPeriocidad.setText(medicamentos.getPeriocidad());


        Medicamentos user= new Medicamentos() ;
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String  description, cant, time, peri;
            description = txtDescripcion.getText().toString();
            cant = txtCantidad.getText().toString();
            time = txtTiempo.getText().toString();
            peri = txtPeriocidad.getText().toString();

                Map<String, Object> map = new HashMap<>();


                map.put("cantidad", cant);
                map.put("descripcion", description);
                map.put("tiempo", time);
                map.put("periocidad", peri);


                FirebaseDatabase.getInstance().getReference().child("nuevos").child(user.getKey())
                        .updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(detalle.this, "actuializada Correctamente", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(detalle.this,MainActivity.class);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(detalle.this, "Error al actualizar", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        myAdapter.notifyDataSetChanged();
    }

}