package com.yukselproje.okurular.incompany.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Adapters.AnnouncementsAdapter;
import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

public class AnnouncementsActivity extends AppCompatActivity {

    List<Announcement> list;
    ListView listView;
    AnnouncementsAdapter announcementsAdapter;
    String rootClass;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        setTitle("Duyurular");
        initialize();
        listAnnouncements();
        originator.setState("AnnouncementsActivity");
        careTaker.add(originator.saveStateToMemento());
        refresh();
    }

    private void initialize() {
        listView = findViewById(R.id.announcements);
        rootClass = getIntent().getStringExtra("RootClass");
        if (rootClass.equals("AdminMainActivity.java"))
            Toast.makeText(getApplicationContext(), "Duyuru silmek için duyurunun üzerine basılı tutun", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listAnnouncements();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listAnnouncements();
    }

    private void fillList() {
        announcementsAdapter = new AnnouncementsAdapter(list, getApplicationContext(), AnnouncementsActivity.this, rootClass);
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
