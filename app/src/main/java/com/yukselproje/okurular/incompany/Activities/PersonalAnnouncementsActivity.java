package com.yukselproje.okurular.incompany.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.yukselproje.okurular.incompany.Adapters.AnnouncementsAdapter;
import com.yukselproje.okurular.incompany.Adapters.PersonalAnnouncementsAdapter;
import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

public class PersonalAnnouncementsActivity extends AppCompatActivity {

    List<Announcement> list;
    ListView listView;
    PersonalAnnouncementsAdapter announcementsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_announcements);
        setTitle("Kişisel Duyurular");
        initialize();
        listAnnouncements();
        originator.setState("PersonalAnnouncementsActivity");
        careTaker.add(originator.saveStateToMemento());
        Log.i("durum", originator.getState().toString());

    }

    private void initialize() {
        listView = findViewById(R.id.personalannouncements);
    }

    private void fillList() {
        announcementsAdapter = new PersonalAnnouncementsAdapter(list, getApplicationContext(),
                PersonalAnnouncementsActivity.this);
        listView.setAdapter(announcementsAdapter);
    }

    private void listAnnouncements() {
        Call<List<Announcement>> x = ManagerAll.getInstance().duyuruListele(getIntent().getStringExtra("id").toString());
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
