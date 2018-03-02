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

/**
 * Created by okurular on 1.03.2018.
 */

public class PersonalAnnouncementsAdapter extends BaseAdapter {

    List<Announcement> list;
    Context context;
    Activity activity;

    public PersonalAnnouncementsAdapter(List<Announcement> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
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
        View layout = LayoutInflater.from(context).inflate(R.layout.personalannouncementcomponent, viewGroup, false);

        TextView title = layout.findViewById(R.id.annotitle2);
        TextView date = layout.findViewById(R.id.date2);
        TextView message = layout.findViewById(R.id.message2);

        title.setText(list.get(i).getTitle().toString());
        date.setText(list.get(i).getDate().toString());
        message.setText(list.get(i).getMessage().toString());

        return layout;
    }
}
