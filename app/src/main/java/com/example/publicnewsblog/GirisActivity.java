package com.example.publicnewsblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GirisActivity extends AppCompatActivity {
    EditText mEmail,mSifre;
    Button mGirisbuton;
    TextView mKayitbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        mEmail = findViewById(R.id.Email);
        mSifre = findViewById(R.id.sifreId);
        mGirisbuton = findViewById(R.id.girisbuton);
        mKayitbtn = findViewById(R.id.kayityonlendir);
        progressBar=findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();



        mGirisbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String sifre = mSifre.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email gerekli");
                    return;
                }

                if (TextUtils.isEmpty(sifre)){
                    mSifre.setError("Şifre gerekli");
                    return;
                }

                if (sifre.length()<6){
                    mSifre.setError("Şifre 6 karakterden kısa olamaz");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                fAuth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(GirisActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(GirisActivity.this, "Hata!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });
        mKayitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),KayitActivity.class));
            }
        });
    }
}