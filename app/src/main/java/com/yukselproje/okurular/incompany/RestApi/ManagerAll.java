package com.yukselproje.okurular.incompany.RestApi;

import com.yukselproje.okurular.incompany.Models.Kisi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;


public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance(){
        return ourInstance;
    }

    public Call<Kisi> kisiEkle(String ad, String soyad, String pozisyon, String kullaniciadi, String sifre){
        Call<Kisi> call = getRestApiClient().kisiEkle(ad, soyad, pozisyon, kullaniciadi, sifre);
        return call;
    }

    public Call<Kisi> loginKontrol(String kullaniciadi, String sifre){
        Call<Kisi> call = getRestApiClient().loginKontrol(kullaniciadi, sifre);
        return call;
    }


}
