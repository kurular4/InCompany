package com.yukselproje.okurular.incompany.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText kullaniciadi, sifre;
    Button giris;
    TextView kayit;
    Kisi kisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        kayitButtonClick();
        girisButtonClick();
    }

    private void initialize() {
        kullaniciadi = findViewById(R.id.kullaniciadi);
        sifre = findViewById(R.id.sifre);
        giris = findViewById(R.id.giris);
        kayit = findViewById(R.id.kayit);
    }

    private void kayitButtonClick() {
        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passToRegisterActivity();
            }
        });
    }

    private void passToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void girisButtonClick() {
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passToMainActivity();
            }
        });
    }

    private void passToMainActivity() {
        loginCheck();
        if (isValidUserInfo()) {
            Toast.makeText(this, kisi.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", kisi.getId());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Kullanıcı adı ya da şifreniz hatalı", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidUserInfo() {
        if (kisi != null)
            return true;
        return false;
    }

    private void loginCheck() {
        Call<Kisi> x = ManagerAll.getInstance().loginKontrol(kullaniciadi.getText().toString(), sifre.getText().toString());
        x.enqueue(new Callback<Kisi>() {
            @Override
            public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                kisi = response.body();
            }

            @Override
            public void onFailure(Call<Kisi> call, Throwable t) {
                Log.i("hata", t.getMessage());
            }
        });
    }


}
