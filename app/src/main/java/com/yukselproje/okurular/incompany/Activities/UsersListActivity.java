package com.yukselproje.okurular.incompany.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Adapters.UserListAdapter;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

public class UsersListActivity extends AppCompatActivity {

    ListView userListView;
    UserListAdapter userListAdapter;
    List<Kisi> list;
    String rootClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        setTitle("Çalışanlar");
        rootClass = getIntent().getStringExtra("RootClass").toString();
        initialize();
        fetchUsers();
        originator.setState("UsersListActivity");
        careTaker.add(originator.saveStateToMemento());
        Log.i("durum", originator.getState().toString());
    }

    private void initialize() {
        userListView = findViewById(R.id.userlist);
        if (rootClass.equals("AdminMainActivity.java"))
            Toast.makeText(getApplicationContext(), "Mesaj yollamak istediğiniz çalışanın üzerine tıklayın", Toast.LENGTH_SHORT).show();
    }

    private void fillList() {
        userListAdapter = new UserListAdapter(list, getApplicationContext(), UsersListActivity.this, rootClass);
        userListView.setAdapter(userListAdapter);
    }

    private void fetchUsers() {
        Call<List<Kisi>> x = ManagerAll.getInstance().kisiListele();
        x.enqueue(new Callback<List<Kisi>>() {
            @Override
            public void onResponse(Call<List<Kisi>> call, Response<List<Kisi>> response) {
                if (response.isSuccessful()) {
                    Log.i("kisiler", response.body().toString());
                    list = response.body();
                    fillList();
                }
            }

            @Override
            public void onFailure(Call<List<Kisi>> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());
            }
        });
    }
}
