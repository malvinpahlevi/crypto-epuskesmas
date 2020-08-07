/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.model;

//import com.je.rumahsakit.master.model.Kamar;
//import com.je.rumahsakit.master.model.Pasien;
import java.sql.Timestamp;

public class RawatKamarPasien {
    
//    private String kode;
//    private Pasien pasien;
//    private Kamar kamar;
    private Timestamp tanggalMasuk;
    private Timestamp tanggalKeluar;
    private String keterangan;

//    public String getKode() {
//        return kode;
//    }
//
//    public void setKode(String kode) {
//        this.kode = kode;
//    }
//
//    public Pasien getPasien() {
//        return pasien;
//    }
//
//    public void setPasien(Pasien pasien) {
//        this.pasien = pasien;
//    }
//
//    public Kamar getKamar() {
//        return kamar;
//    }

//    public void setKamar(Kamar kamar) {
//        this.kamar = kamar;
//    }


    public Timestamp getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Timestamp tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public Timestamp getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(Timestamp tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    
}
