package com.yukselproje.okurular.incompany.RestApi;

import com.yukselproje.okurular.incompany.Models.Kisi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {

    @FormUrlEncoded
    @POST("kullaniciekle.php")
    Call<Kisi> kisiEkle(@Field("ad") String ad, @Field("soyad") String soyad,
                       @Field("pozisyon") String pozisyon, @Field("kullaniciadi") String kullaniciadi, @Field("sifre") String sifre);

    @GET("login.php")
    Call<Kisi> loginKontrol(@Query("kullaniciadi") String kullaniciadi, @Query("sifre") String sifre);

}
