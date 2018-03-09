package com.yukselproje.okurular.incompany.Services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.RestApi.ManagerAll;

import java.util.Timer;
import java.util.TimerTask;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService extends Service {

    String id;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = intent.getStringExtra("id");
        checkLocation();
        return START_REDELIVER_INTENT;
        // START_STICKY tells the OS to recreate the service after it has enough memory
        // and call onStartCommand() again with a null intent.
        // START_NOT_STICKY tells the OS to not bother recreating the service again.
        // There is also a third code START_REDELIVER_INTENT that tells
        // the OS to recreate the service and redeliver the same intent to onStartCommand().
    }


    private void checkLocation() {
        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                if (Math.abs(39.875303 - Double.parseDouble(location.getLatitude() + "")) <= 0.001 ||
                        Math.abs(32.879980 - Double.parseDouble(location.getLongitude() + "")) <= 0.001) {
                    Call<Kisi> x = ManagerAll.getInstance().lokasyonGuncelle(id, 1);
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
                    Call<Kisi> x = ManagerAll.getInstance().lokasyonGuncelle(id, 0);
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
                }
            }
        });
    }
}
