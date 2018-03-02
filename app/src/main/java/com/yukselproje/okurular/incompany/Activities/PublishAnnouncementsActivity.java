package com.yukselproje.okurular.incompany.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.R;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yukselproje.okurular.incompany.Activities.LoginActivity.careTaker;
import static com.yukselproje.okurular.incompany.Activities.LoginActivity.originator;

public class PublishAnnouncementsActivity extends AppCompatActivity {

    EditText titlesend, messagesend;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_announcements);
        setTitle("Duyuru Yayınla");
        initialize();
        sendButtonClick();
        originator.setState("PublishAnnouncementsActivity");
        careTaker.add(originator.saveStateToMemento());
        Log.i("durum", originator.getState().toString());

    }

    private void initialize() {
        titlesend = findViewById(R.id.titleSend);
        messagesend = findViewById(R.id.messagesend);
        send = findViewById(R.id.send);
    }

    private void sendButtonClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTextBoxes();
                if (!titlesend.getText().toString().isEmpty() && !messagesend.getText().toString().isEmpty())
                    publishAnnouncement();
            }
        });
    }

    private void checkTextBoxes() {
        if (titlesend.getText().toString().isEmpty())
            titlesend.setError("Lütfen duyuru başlığını giriniz!");
        if (messagesend.getText().toString().isEmpty())
            messagesend.setError("Lütfen duyuru giriniz!");
    }

    private String getPhoneDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c).toString();
        return formattedDate;
    }

    private void publishAnnouncement() {
        Call<Announcement> x = ManagerAll.getInstance().duyuruEkle("afaf",
                "All", titlesend.getText().toString(), messagesend.getText().toString(), getPhoneDate());
        x.enqueue(new Callback<Announcement>() {
            @Override
            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Duyuru başarı ile yayınlandı", Toast.LENGTH_LONG).show();
                    emptyTextBoxes();
            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {
                Log.i("hata", t.getMessage().toString());
            }
        });
    }

    private void emptyTextBoxes(){
        titlesend.setText("");
        messagesend.setText("");
    }
}
