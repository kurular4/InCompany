package com.yukselproje.okurular.incompany.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by okurular on 27.02.2018.
 */

public class UserListAdapter extends BaseAdapter {

    List<Kisi> list;
    Context context;
    Activity activity;
    String rootClass;

    public UserListAdapter(List<Kisi> list, Context context, Activity activity, String rootClass) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.rootClass = rootClass;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View layout = LayoutInflater.from(context).inflate(R.layout.userlistcomponent, viewGroup, false);

        TextView name = layout.findViewById(R.id.name2);
        TextView surname = layout.findViewById(R.id.surname2);
        TextView pozisyon = layout.findViewById(R.id.pozisyon2);

        name.setText(list.get(i).getAd().toString());
        surname.setText(list.get(i).getSoyad().toString());
        pozisyon.setText(list.get(i).getPozisyon().toString());

        if (rootClass.equals("AdminMainActivity.java")) {
            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openPersonelAnnouncementDialog(i);
                    return true;
                }
            });
        }


        return layout;
    }

    private void openPersonelAnnouncementDialog(final int i) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.personalannouncement, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(true);

        final EditText message = view.findViewById(R.id.messagesendp);
        final EditText title = view.findViewById(R.id.titleSendp);
        Button send = view.findViewById(R.id.sendp);

        final AlertDialog dialog = builder.create();

        dialog.show();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Announcement> x = ManagerAll.getInstance().duyuruEkle("", list.get(i).getId().toString(),
                        title.getText().toString(), message.getText().toString(), getPhoneDate());
                x.enqueue(new Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Duyuru başarıyla iletildi", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<Announcement> call, Throwable t) {

                    }
                });
            }
        });
    }

    private String getPhoneDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c).toString();
        return formattedDate;
    }
}
