package com.yukselproje.okurular.incompany.Models;

/**
 * Created by okurular on 23.02.2018.
 */

public class Kisi {
    private String id;
    private String ad;
    private String soyad;
    private String kullaniciadi;
    private String pozisyon;
    private int yetki; // 1 = auth. 0 = unauth.

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getPozisyon() {
        return pozisyon;
    }

    public void setPozisyon(String pozisyon) {
        this.pozisyon = pozisyon;
    }

    public int getYetki() {
        return yetki;
    }

    public void setYetki(int yetki) {
        this.yetki = yetki;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Kisi{" +
                "id='" + id + '\'' +
                ", ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", kullaniciadi='" + kullaniciadi + '\'' +
                ", pozisyon='" + pozisyon + '\'' +
                ", yetki=" + yetki +
                '}';
    }
}
