package com.yukselproje.okurular.incompany.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Memento.CareTaker;
import com.yukselproje.okurular.incompany.Memento.Memento;
import com.yukselproje.okurular.incompany.Memento.Originator;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText kullaniciadi, sifre;
    Button giris;
    TextView kayit;
    Kisi kisi;
    ProgressBar progressBarLogin;
    public static SharedPreferences sharedPreferences;
    public static Originator originator = Originator.getInstance();
    public static CareTaker careTaker = CareTaker.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Giriş");
        checkSharedPreferences();
        initialize();
        kayitButtonClick();
        girisButtonClick();
        originator.setState("LoginActivity");
        careTaker.add(originator.saveStateToMemento());
        Log.i("durum", originator.getState().toString());

    }

    private void initialize() {
        kullaniciadi = findViewById(R.id.kullaniciadi);
        sifre = findViewById(R.id.sifre);
        giris = findViewById(R.id.giris);
        kayit = findViewById(R.id.kayit);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.GONE);
    }

    private void checkSharedPreferences() {
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        if (sharedPreferences.getString("id", null) != null && sharedPreferences.getString("kullaniciadi", null) != null)
            passToMainActivity(sharedPreferences.getString("id", null).toString(), sharedPreferences.getInt("yetki", 0));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void girisButtonClick() {
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isConnected()) {
                        checkTextBoxes();
                        if (!(kullaniciadi.getText().toString().isEmpty()) && !(sifre.getText().toString().isEmpty()))
                            loginCheck();
                    } else
                        Toast.makeText(getApplicationContext(), "Lütfen internet bağlantınızı kontrol edin", Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkTextBoxes() {
        if (kullaniciadi.getText().toString().isEmpty())
            kullaniciadi.setError("Lütfen kullanıcı adınızı giriniz!");
        if (sifre.getText().toString().isEmpty())
            sifre.setError("Lütfen şifrenizi giriniz!");
    }

    private void passToMainActivity(String id, int yetki) {
        Intent intent;
        if (yetki == 1)
            intent = new Intent(this, AdminMainActivity.class);
        else
            intent = new Intent(this, UserMainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void loginCheck() {
        progressBarLogin.setVisibility(View.VISIBLE);
        Call<Kisi> x = ManagerAll.getInstance().loginKontrol(kullaniciadi.getText().toString(), sifre.getText().toString());
        x.enqueue(new Callback<Kisi>() {
            @Override
            public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getKullaniciadi() != null && response.body().getId() != null) {
                        setSharedPreferences(response.body().getId().toString(), response.body().getKullaniciadi().toString(), response.body().getYetki());
                        kisi = response.body();
                        progressBarLogin.setVisibility(View.GONE);
                        passToMainActivity(kisi.getId().toString(), kisi.getYetki());
                    } else {
                        Toast.makeText(getApplicationContext(), "Hatalı kullanıcı adı ya da şifre", Toast.LENGTH_LONG).show();
                        progressBarLogin.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Kisi> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());
            }
        });
    }

    private void setSharedPreferences(String id, String kullaniciadi, int yetki) {
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.putString("kullaniciadi", kullaniciadi);
        editor.putInt("yetki", yetki);
        editor.commit();
    }

    private boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }


}
