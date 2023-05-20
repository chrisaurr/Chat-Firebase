package com.example.chatfirebase.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chatfirebase.Models.Usuario;
import com.example.chatfirebase.databinding.ActivityMainBinding;
import com.example.chatfirebase.databinding.ActivityRegistroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    ActivityRegistroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        binding.registrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = binding.editRegistroEmail.getEditText().getText().toString();
                String nombre = binding.editRegistroNombre.getEditText().getText().toString();
                if(isValidEmail(correo) && validatePass() && validateName(nombre)){
                    String contra = binding.editRegistroContra.getEditText().getText().toString();

                    mAuth.createUserWithEmailAndPassword(correo, contra)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                        Usuario user = new Usuario();
                                        user.setCorreo(correo);
                                        user.setNombre(nombre);
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        DatabaseReference reference = db.getReference("usuarios/"+currentUser.getUid());
                                        //refUsers.push().setValue(user);
                                        reference.setValue(user);
                                        finish();
                                    } else {
                                        Toast.makeText(RegistroActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegistroActivity.this, "Validando", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validatePass(){
        String contra, repeatContra;
        contra = binding.editRegistroContra.getEditText().getText().toString();
        repeatContra = binding.editRegistroRepetirContra.getEditText().getText().toString();

        if(contra.equals(repeatContra)){
            if(contra.length() >= 6 && contra.length() <= 16){
                return true;
            }else{
                return false;
            }
        }

        return false;

    }

    public boolean validateName(String nombre){
        return !nombre.isEmpty();
    }

}