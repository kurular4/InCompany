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
import android.widget.TextView;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

/**
 * Created by okurular on 1.03.2018.
 */

public class AnnouncementsAdapter extends BaseAdapter {

    List<Announcement> list;
    Context context;
    Activity activity;
    String rootClass;

    public AnnouncementsAdapter(List<Announcement> list, Context context, Activity activity, String rootClass) {
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
        View layout = LayoutInflater.from(context).inflate(R.layout.announcementcomponent, viewGroup, false);

        TextView title = layout.findViewById(R.id.annotitle);
        TextView date = layout.findViewById(R.id.date);
        TextView message = layout.findViewById(R.id.message);

        title.setText(list.get(i).getTitle().toString());
        date.setText(list.get(i).getDate().toString());
        message.setText(list.get(i).getMessage().toString());

        if (rootClass.equals("AdminMainActivity.java")) {
            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openDeleteAnnouncementDialog(i);
                    return true;
                }
            });
        }

        return layout;
    }

    private void openDeleteAnnouncementDialog(final int i) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.duyurusildialog, null);

        Button sil = view.findViewById(R.id.duyurusil);
        Button iptal = view.findViewById(R.id.iptal);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();

        dialog.show();

        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Announcement> x = ManagerAll.getInstance().duyuruSil(list.get(i).getId().toString());
                x.enqueue(new Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Duyuru silindi", Toast.LENGTH_LONG).show();
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
}
