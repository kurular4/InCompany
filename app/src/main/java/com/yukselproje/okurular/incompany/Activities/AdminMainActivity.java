package com.yukselproje.okurular.incompany.Activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Adapters.AnnouncementsAdapter;
import com.yukselproje.okurular.incompany.Adapters.AnnouncementsAdapterMain;
import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.Models.Weather.Result;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;
import com.yukselproje.okurular.incompany.Services.LocationService;

import java.text.DecimalFormat;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Kisi kisi;
    SharedPreferences.Editor editor;
    TextView derece;
    ImageView havadurumuicon;
    ListView listView;
    List<Announcement> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        setTitle("Ana sayfa");
        getById(getIntent().getStringExtra("id").toString());
        initialize();
        getWeatherReport();
        listAnnouncements();
        setMemento();
        Log.i("durum", originator.getState().toString());
        updateListView();
        checkLocation();
        checkService();
    }



    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        derece = findViewById(R.id.derece);
        havadurumuicon = findViewById(R.id.havadurumuicon);
        listView = findViewById(R.id.sonduyurular);

    }

    private void setMemento(){
        originator.setState("AdminMainActivity");
        careTaker.add(originator.saveStateToMemento());
    }

    private void checkService(){
        if (!isServiceOn()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.putExtra("id", getIntent().getStringExtra("id").toString());
            startService(intent);
        }
    }

    public boolean isServiceOn() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (LocationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void checkLocation() {

        if (Math.abs(39.875303 - Double.parseDouble(SmartLocation.with(this).location().getLastLocation().getLatitude() + "")) <= 0.001 ||
                Math.abs(32.879980 - Double.parseDouble(SmartLocation.with(this).location().getLastLocation().getLongitude() + "")) <= 0.001) {
            Call<Kisi> x = ManagerAll.getInstance().lokasyonGuncelle(getIntent().getStringExtra("id"), 1);
            x.enqueue(new Callback<Kisi>() {
                @Override
                public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                    if (response.isSuccessful()) {
                    }
                }

                @Override
                public void onFailure(Call<Kisi> call, Throwable t) {
                }
            });
        } else {
            Call<Kisi> x = ManagerAll.getInstance().lokasyonGuncelle(getIntent().getStringExtra("id"), 0);
            x.enqueue(new Callback<Kisi>() {
                @Override
                public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                    if (response.isSuccessful()){
                    }
                }

                @Override
                public void onFailure(Call<Kisi> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    private void updateListView() {
        Log.i("durum", originator.getState().toString());
        if (originator.getState().toString().equals("PublishAnnouncementsActivity") || originator.getState().toString().equals("AnnouncementsActivity"))
            listAnnouncements();
    }

    private void getById(String id) {
        Call<Kisi> x = ManagerAll.getInstance().kisitGetir(id);
        x.enqueue(new Callback<Kisi>() {
            @Override
            public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                if (response.isSuccessful()) {
                    kisi = response.body();
                    writeNameToNavigationHeader();
                }
            }

            @Override
            public void onFailure(Call<Kisi> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());
            }
        });
    }

    private void writeNameToNavigationHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.textView);
        text.setText(kisi.getAd() + " " + kisi.getSoyad());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
            //super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_duyurular) {
            startAnnouncementsActivity();
        } else if (id == R.id.nav_yayinla) {
            startPublishAnnouncementsActivity();
        } else if (id == R.id.nav_ayarlar) {
            openSettingsDialog();
        } else if (id == R.id.nav_calisanlar) {
            startUsersListActivity();
        } else if (id == R.id.nav_cikis) {
            logOutAccount();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void startAnnouncementsActivity() {
        Intent intent = new Intent(AdminMainActivity.this, AnnouncementsActivity.class);
        intent.putExtra("RootClass", "AdminMainActivity.java");
        startActivity(intent);
    }

    private void startPublishAnnouncementsActivity() {
        Intent intent = new Intent(AdminMainActivity.this, PublishAnnouncementsActivity.class);
        intent.putExtra("id", kisi.getId().toString());
        startActivity(intent);
    }

    private void startUsersListActivity() {
        Intent intent = new Intent(AdminMainActivity.this, UsersListActivity.class);
        intent.putExtra("RootClass", "AdminMainActivity.java");
        startActivity(intent);
    }

    private void logOutAccount() {
        editor = LoginActivity.sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void openSettingsDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.settingsdialog, null);

        final EditText ad = view.findViewById(R.id.namechange);
        final EditText soyad = view.findViewById(R.id.surnamechange);
        final EditText pozisyon = view.findViewById(R.id.pozisyonchange);
        final EditText kullaniciadi = view.findViewById(R.id.kullaniciadichange);
        final EditText sifre = view.findViewById(R.id.sifrechange);
        Button guncelle = view.findViewById(R.id.guncelle);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();

        dialog.show();

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTextBoxesOnUpdate(ad, soyad, pozisyon, kullaniciadi, sifre);
            }
        });

    }

    private void checkTextBoxesOnUpdate(EditText ad, EditText soyad, EditText pozisyon, EditText kullaniciadi, EditText sifre) {
        String ad2;
        String soyad2;
        String pozisyon2;
        String kullaniciadi2;
        String sifre2 = sifre.getText().toString();

        if (ad.getText().toString().isEmpty())
            ad2 = kisi.getAd().toString();
        else
            ad2 = ad.getText().toString();

        if (soyad.getText().toString().isEmpty())
            soyad2 = kisi.getSoyad().toString();
        else
            soyad2 = ad.getText().toString();

        if (pozisyon.getText().toString().isEmpty())
            pozisyon2 = kisi.getPozisyon().toString();
        else
            pozisyon2 = ad.getText().toString();

        if (kullaniciadi.getText().toString().isEmpty())
            kullaniciadi2 = kisi.getKullaniciadi().toString();
        else
            kullaniciadi2 = ad.getText().toString();

        if (sifre.getText().toString().isEmpty())
            sifre.setError("Bu kısım boş bırakılamaz");
        else
            kisiUpdate(ad2, soyad2, pozisyon2, kullaniciadi2, sifre2);
    }

    private void kisiUpdate(String ad, String soyad, String pozisyon, String kullaniciadi, String sifre) {
        Call<Kisi> x = ManagerAll.getInstance().kisiGuncelle(kisi.getId(), ad, soyad, pozisyon, kullaniciadi, sifre);
        x.enqueue(new Callback<Kisi>() {
            @Override
            public void onResponse(Call<Kisi> call, Response<Kisi> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Kişi başarıyla güncellendi, lütfen tekrar giriş yapınız", Toast.LENGTH_LONG).show();
                    logOutAccount();
                }
            }

            @Override
            public void onFailure(Call<Kisi> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());
            }
        });
    }

    private void getWeatherReport() {
        Call<Result> x = ManagerAll.getInstance().fetchWeatherReport("382f885e256f39205313510357190e22");
        x.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                setIconAndDegree(response.body().getList().get(0).getMain().getTemp() - 273.5);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());
            }
        });
    }

    private void setIconAndDegree(Double degree) {
        DecimalFormat df = new DecimalFormat("#.#");
        derece.setText(df.format(degree) + " °C");
        if (degree > 15)
            havadurumuicon.setBackgroundResource(R.drawable.ic_wb_sunny_black_24dp);
        if (degree > 5 && degree <= 15)
            havadurumuicon.setBackgroundResource(R.drawable.ic_cloud_black_24dp);
        if (degree < 5)
            havadurumuicon.setBackgroundResource(R.drawable.ic_grain_black_24dp);
    }

    private void fillList() {
        AnnouncementsAdapterMain announcementsAdapter = new AnnouncementsAdapterMain(list, getApplicationContext(),
                AdminMainActivity.this);
        listView.setAdapter(announcementsAdapter);
    }

    private void listAnnouncements() {
        Call<List<Announcement>> x = ManagerAll.getInstance().duyuruListele("All");
        x.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    fillList();
                }
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());

            }
        });
    }
}
