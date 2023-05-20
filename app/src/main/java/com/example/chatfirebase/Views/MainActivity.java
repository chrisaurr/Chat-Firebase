package com.example.chatfirebase.Views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chatfirebase.Adapters.Adapter;
import com.example.chatfirebase.Models.MensajeEmisor;
import com.example.chatfirebase.Models.MensajeReceptor;
import com.example.chatfirebase.Models.Usuario;
import com.example.chatfirebase.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Adapter adapter;
    private FirebaseDatabase database;
    private String urlFotoPerfil = "";
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private FirebaseAuth mAuth;

    private String nameLastUserLogued;

    int code = 0;
   /* private LauncherActivityInfo responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }*/

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                Log.v("Prueba", String.valueOf(code));
                if(result.getResultCode() == RESULT_OK && code == 1){
                    Uri uri = result.getData().getData();
                    storageReference = firebaseStorage.getReference("images");
                    final StorageReference referenceImage = storageReference.child(uri.getLastPathSegment());
                    referenceImage.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            referenceImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri uri1 = uri;
                                    MensajeEmisor mensaje = new MensajeEmisor(nameLastUserLogued + " ha enviado una foto", uri1.toString(),nameLastUserLogued, urlFotoPerfil, "2", ServerValue.TIMESTAMP);
                                    databaseReference.push().setValue(mensaje);
                                }
                            });
                        }
                    });
                }

                if(result.getResultCode() == RESULT_OK && code == 2){
                    Uri uri = result.getData().getData();
                    storageReference = firebaseStorage.getReference("photos");
                    final StorageReference referenceImage = storageReference.child(uri.getLastPathSegment());
                    referenceImage.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            referenceImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri uri1 = uri;
                                    urlFotoPerfil = uri1.toString();
                                    MensajeEmisor mensaje = new MensajeEmisor(nameLastUserLogued + " ha actualizado foto de perfil", uri1.toString(),nameLastUserLogued, urlFotoPerfil, "2", ServerValue.TIMESTAMP);
                                    databaseReference.push().setValue(mensaje);
                                    Picasso.get()
                                            .load(uri1.toString())
                                            .into(binding.fotoPerfil);
                                }
                            });
                        }
                    });
                }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chatV2");
        firebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();



        adapter = new Adapter(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        binding.rvMensajes.setLayoutManager(l);
        binding.rvMensajes.setAdapter(adapter);

        binding.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databaseReference.push().setValue(new MensajeEmisor(binding.txtMensaje.getEditText().getText().toString(),
                       nameLastUserLogued,
                        "",
                        "1",
                       ServerValue.TIMESTAMP));
            }
        });

        binding.cerrarSesionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.accederGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                code = 1;
                //startActivityForResult(Intent.createChooser(intent, "Seleccione imagen a enviar"), 1);
                launcher.launch(Intent.createChooser(intent, "Seleccione imagen a enviar"));
            }
        });

        binding.fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                code = 2;
                //startActivityForResult(Intent.createChooser(intent, "Seleccione imagen a enviar"), 1);
                launcher.launch(Intent.createChooser(intent, "Seleccione imagen a enviar"));
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MensajeReceptor mensaje =  snapshot.getValue(MensajeReceptor.class);
                adapter.addMensaje(mensaje);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setScrollBar(){
        binding.rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            binding.btnEnviar.setEnabled(false);
            DatabaseReference reference = database.getReference("usuarios/"+currentUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario user = snapshot.getValue(Usuario.class);
                    nameLastUserLogued = user.getNombre();
                    binding.nombre.setText(nameLastUserLogued);
                    binding.btnEnviar.setEnabled(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}