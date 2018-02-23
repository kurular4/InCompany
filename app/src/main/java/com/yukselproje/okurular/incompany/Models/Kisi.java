package com.yukselproje.okurular.incompany.Models;

/**
 * Created by okurular on 23.02.2018.
 */

public class Kisi {
    private String isim;
    private String soyisim;
    private String pozisyon;
    private String kullaniciadi;
    private int id;

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getPozisyon() {
        return pozisyon;
    }

    public void setPozisyon(String pozisyon) {
        this.pozisyon = pozisyon;
    }

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Kisi{" +
                "isim='" + isim + '\'' +
                ", soyisim='" + soyisim + '\'' +
                ", pozisyon='" + pozisyon + '\'' +
                ", kullaniciadi='" + kullaniciadi + '\'' +
                ", id=" + id +
                '}';
    }
}
