package com.yukselproje.okurular.incompany.RestApi;

import com.yukselproje.okurular.incompany.Models.Announcement;
import com.yukselproje.okurular.incompany.Models.Kisi;
import com.yukselproje.okurular.incompany.Models.Weather.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @FormUrlEncoded
    @POST("kullaniciekle.php")
    Call<Kisi> kisiEkle(@Field("ad") String ad, @Field("soyad") String soyad,
                       @Field("pozisyon") String pozisyon, @Field("kullaniciadi") String kullaniciadi,
                        @Field("sifre") String sifre, @Field("yetki") int yetki);
    @FormUrlEncoded
    @POST("login.php")
    Call<Kisi> loginKontrol(@Field("kullaniciadi") String kullaniciadi, @Field("sifre") String sifre);

    @FormUrlEncoded
    @POST("kisigetir.php")
    Call<Kisi> kisiGetir(@Field("id") String id);

    @GET("kisilistele.php")
    Call<List<Kisi>> kisiListele();

    @FormUrlEncoded
    @POST("kisibilgiguncelle.php")
    Call<Kisi> kisiGuncelle(@Field("id") String id, @Field("ad") String ad, @Field("soyad") String soyad,
                            @Field("pozisyon") String pozisyon, @Field("kullaniciadi") String kullaniciadi,
                            @Field("sifre") String sifre);

    @GET("data/2.5/forecast?id=323786")
    Call<Result> fetchWeatherReport(@Query("APPID") String  apikey);

    @FormUrlEncoded
    @POST("duyurugonder.php")
    Call<Announcement> duyuruEkle(@Field("fromid") String fromid, @Field("toid") String toid, @Field("title") String title,
                                  @Field("message") String message, @Field("date") String date);

    @FormUrlEncoded
    @POST("duyurulistele.php")
    Call<List<Announcement>> duyuruListele(@Field("toid") String toid);

    @FormUrlEncoded
    @POST("duyurusil.php")
    Call<Announcement> duyuruSil(@Field("id") String id);
}
