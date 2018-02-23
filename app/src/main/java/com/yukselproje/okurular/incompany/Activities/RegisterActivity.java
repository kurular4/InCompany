package com.yukselproje.okurular.incompany.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText isim, soyisim, pozisyon;
    Button kayit;
    String generatedkullaniciadi, generatedsifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        kayitButtonClick();
    }

    private void initialize() {
        isim = findViewById(R.id.isim);
        soyisim = findViewById(R.id.soyisim);
        pozisyon = findViewById(R.id.pozisyon);
        kayit = findViewById(R.id.kayit);
    }

    private void kayitButtonClick() {
        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((isim.getText().toString() != "") && (soyisim.getText().toString() != "") && (pozisyon.getText().toString() != ""))
                    register();
                else
                    Toast.makeText(getApplicationContext(), "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register() {
        generatedkullaniciadi = generateUsername();
        generatedsifre = generatePassword();
        Call<Kisi> x = ManagerAll.getInstance().kisiEkle(isim.getText().toString(), soyisim.getText().toString(), pozisyon.getText().toString(),
                generatedkullaniciadi, generatedsifre);
        x.enqueue(new Callback<Kisi>() {
            @Override
            public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                if (response.isSuccessful()) {
                    openInfoDialog();
                }
            }
            @Override
            public void onFailure(Call<Kisi> call, Throwable t) {
                openInfoDialog();
            }
        });
    }

    private String generateUsername() {
        String username = isim.getText().toString().replaceAll("\\s+","");
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * 9 + 1);
            username = username + random;
        }
        return username;
    }

    private String generatePassword() {
        String password = "";
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * 9 + 1);
            password = password + random;
        }
        return password;
    }

    private void openInfoDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.infoalertdialog, null);

        TextView kullaniciadi = view.findViewById(R.id.kullaniciadi);
        TextView sifre = view.findViewById(R.id.sifre);
        Button cikis = view.findViewById(R.id.cikis);

        kullaniciadi.setText("kullanıcı adı: " + generatedkullaniciadi);
        sifre.setText("şifre: " + generatedsifre);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }
}
