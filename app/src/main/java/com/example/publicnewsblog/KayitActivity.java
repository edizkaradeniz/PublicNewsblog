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

public class KayitActivity extends AppCompatActivity {
 EditText mAd,mSoyad,mEmail,mSifre;
 Button mKayitbuton;
 TextView mGirisbtn;
 FirebaseAuth fAuth;
 ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        mAd = findViewById(R.id.isimId);
        mSoyad = findViewById(R.id.soyadId);
        mEmail = findViewById(R.id.Email);
        mSifre = findViewById(R.id.sifreId);
        mKayitbuton = findViewById(R.id.kayitbuton);
        mGirisbtn = findViewById(R.id.girisyonlendir);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mKayitbuton.setOnClickListener(new View.OnClickListener() {
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


                fAuth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(KayitActivity.this, "Kullanıcı Oluşturuldu", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(KayitActivity.this, "Hata!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });
        mGirisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GirisActivity.class));
            }
        });
    }
}