package com.je.master.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Pasien implements Serializable {

    private static final long serialVersionUID = -6789148547176166303L;
    
    private int id_pasien;
    private String pasien_ktp;    
    private String pasien_nama;   
    private String pasien_alamat;   
    private String pasien_tempatlahir;
    private Timestamp pasien_tgllahir;   
    private String pasien_telepon;   
    private String pasien_jenkel;  
    private String pasien_agama;  
    private String pasien_noasuransi;  
    private String pasien_jenisasuransi;   
    private String pasien_goldar; 
    private String pasien_kelurahan;    
    private String pasien_kecamatan;   
    private String pasien_kotakabupaten;   
    private String pasien_provinsi;  
    private String pasien_nokk;

    public int getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(int id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getPasien_ktp() {
        return pasien_ktp;
    }

    public void setPasien_ktp(String pasien_ktp) {
        this.pasien_ktp = pasien_ktp;
    }

    public String getPasien_nama() {
        return pasien_nama;
    }

    public void setPasien_nama(String pasien_nama) {
        this.pasien_nama = pasien_nama;
    }

    public String getPasien_alamat() {
        return pasien_alamat;
    }

    public void setPasien_alamat(String pasien_alamat) {
        this.pasien_alamat = pasien_alamat;
    }

    public String getPasien_tempatlahir() {
        return pasien_tempatlahir;
    }

    public void setPasien_tempatlahir(String pasien_tempatlahir) {
        this.pasien_tempatlahir = pasien_tempatlahir;
    }
    
    public Timestamp getPasien_tgllahir() {
        return pasien_tgllahir;
    }

    public void setPasien_tgllahir(Timestamp pasien_tgllahir) {
        this.pasien_tgllahir = pasien_tgllahir;
    }

    public String getPasien_telepon() {
        return pasien_telepon;
    }

    public void setPasien_telepon(String pasien_telepon) {
        this.pasien_telepon = pasien_telepon;
    }

    public String getPasien_jenkel() {
        return pasien_jenkel;
    }

    public void setPasien_jenkel(String pasien_jenkel) {
        this.pasien_jenkel = pasien_jenkel;
    }

    public String getPasien_agama() {
        return pasien_agama;
    }

    public void setPasien_agama(String pasien_agama) {
        this.pasien_agama = pasien_agama;
    }

    public String getPasien_noasuransi() {
        return pasien_noasuransi;
    }

    public void setPasien_noasuransi(String pasien_noasuransi) {
        this.pasien_noasuransi = pasien_noasuransi;
    }

    public String getPasien_jenisasuransi() {
        return pasien_jenisasuransi;
    }

    public void setPasien_jenisasuransi(String pasien_jenisasuransi) {
        this.pasien_jenisasuransi = pasien_jenisasuransi;
    }

    public String getPasien_goldar() {
        return pasien_goldar;
    }

    public void setPasien_goldar(String pasien_goldar) {
        this.pasien_goldar = pasien_goldar;
    }

    public String getPasien_kelurahan() {
        return pasien_kelurahan;
    }

    public void setPasien_kelurahan(String pasien_kelurahan) {
        this.pasien_kelurahan = pasien_kelurahan;
    }

    public String getPasien_kecamatan() {
        return pasien_kecamatan;
    }

    public void setPasien_kecamatan(String pasien_kecamatan) {
        this.pasien_kecamatan = pasien_kecamatan;
    }

    public String getPasien_kotakabupaten() {
        return pasien_kotakabupaten;
    }

    public void setPasien_kotakabupaten(String pasien_kotakabupaten) {
        this.pasien_kotakabupaten = pasien_kotakabupaten;
    }

    public String getPasien_provinsi() {
        return pasien_provinsi;
    }

    public void setPasien_provinsi(String pasien_provinsi) {
        this.pasien_provinsi = pasien_provinsi;
    }

    public String getPasien_nokk() {
        return pasien_nokk;
    }

    public void setPasien_nokk(String pasien_nokk) {
        this.pasien_nokk = pasien_nokk;
    }

    
    
   
}
