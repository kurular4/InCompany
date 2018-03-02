package com.yukselproje.okurular.incompany.RestApi;

import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.Models.Weather.Result;

import java.util.List;

import retrofit2.Call;


public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<Kisi> kisiEkle(String ad, String soyad, String pozisyon, String kullaniciadi, String sifre, int yetki) {
        Call<Kisi> call = getRestApiClient().kisiEkle(ad, soyad, pozisyon, kullaniciadi, sifre, yetki);
        return call;
    }

    public Call<Kisi> loginKontrol(String kullaniciadi, String sifre) {
        Call<Kisi> call = getRestApiClient().loginKontrol(kullaniciadi, sifre);
        return call;
    }

    public Call<Kisi> kisitGetir(String id) {
        Call<Kisi> call = getRestApiClient().kisiGetir(id);
        return call;
    }

    public Call<List<Kisi>> kisiListele() {
        Call<List<Kisi>> call = getRestApiClient().kisiListele();
        return call;
    }

    public Call<Kisi> kisiGuncelle(String id, String ad, String soyad, String pozisyon, String kullaniciadi, String sifre) {
        Call<Kisi> call = getRestApiClient().kisiGuncelle(id, ad, soyad, pozisyon, kullaniciadi, sifre);
        return call;
    }

    public Call<Result> fetchWeatherReport(String apikey) {
        Call<Result> call = getRestApiClientWeatherService().fetchWeatherReport(apikey);
        return call;
    }

    public Call<Announcement> duyuruEkle(String fromid, String toid, String title, String message, String date) {
        Call<Announcement> call = getRestApiClient().duyuruEkle(fromid, toid, title, message, date);
        return call;
    }

    public Call<List<Announcement>> duyuruListele(String toid) {
        Call<List<Announcement>> call = getRestApiClient().duyuruListele(toid);
        return call;
    }

    public Call<Announcement> duyuruSil(String id){
        Call<Announcement> call = getRestApiClient().duyuruSil(id);
        return call;
    }

}
