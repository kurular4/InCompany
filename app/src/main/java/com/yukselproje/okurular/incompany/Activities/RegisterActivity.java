package com.yukselproje.okurular.incompany.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

public class RegisterActivity extends AppCompatActivity {

    EditText isim, soyisim, pozisyon;
    Button kayit;
    String generatedkullaniciadi, generatedsifre;
    RotateLoading progressBarRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Kayıt");
        initialize();
        kayitButtonClick();
        originator.setState("RegisterActivity");
        careTaker.add(originator.saveStateToMemento());
        Log.i("durum", originator.getState().toString());

    }

    private void initialize() {
        isim = findViewById(R.id.isim);
        soyisim = findViewById(R.id.soyisim);
        pozisyon = findViewById(R.id.pozisyon);
        kayit = findViewById(R.id.kayit);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        progressBarRegister.stop();
    }

    private void kayitButtonClick() {
        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isConnected()) {
                        checkTextBoxes();
                        if (!(isim.getText().toString().isEmpty()) && !(soyisim.getText().toString().isEmpty()) && !(pozisyon.getText().toString().isEmpty()))
                            register();
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
        if (isim.getText().toString().isEmpty())
            isim.setError("Lütfen isminizi giriniz!");
        if (isim.getText().toString().isEmpty())
            soyisim.setError("Lütfen soyisminizi giriniz!");
        if (isim.getText().toString().isEmpty())
            pozisyon.setError("Lütfen pozisyonunuzu giriniz!");
    }

    private void register() {
        progressBarRegister.start();
        generatedkullaniciadi = generateUsername();
        generatedsifre = generatePassword();
        Call<Kisi> x = ManagerAll.getInstance().kisiEkle(isim.getText().toString(), soyisim.getText().toString(), pozisyon.getText().toString(),
                generatedkullaniciadi, generatedsifre, 0, 0);
        x.enqueue(new Callback<Kisi>() {
            @Override
            public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                if (response.isSuccessful()) {
                    progressBarRegister.stop();
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
        String username = isim.getText().toString().replaceAll("\\s+", "");
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 9 + 1);
            username = username + random;
        }
        return username;
    }

    private String generatePassword() {
        String password = "";
        for (int i = 0; i < 4; i++) {
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

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }
}