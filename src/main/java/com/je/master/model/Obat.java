/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.model;

/**
 *
 * @author AJ.P
 */
public class Obat {
    
    private int id_obat;
    private String kd_obat;
    private String obat_nama;
    private String obat_jenis;
    private String obat_kadaluarsa;
    private String obat_produsen;

    public int getId_obat() {
        return id_obat;
    }

    public void setId_obat(int id_obat) {
        this.id_obat = id_obat;
    }

    public String getKd_obat() {
        return kd_obat;
    }

    public void setKd_obat(String kd_obat) {
        this.kd_obat = kd_obat;
    }

    public String getObat_nama() {
        return obat_nama;
    }

    public void setObat_nama(String obat_nama) {
        this.obat_nama = obat_nama;
    }

    public String getObat_jenis() {
        return obat_jenis;
    }

    public void setObat_jenis(String obat_jenis) {
        this.obat_jenis = obat_jenis;
    }

    public String getObat_kadaluarsa() {
        return obat_kadaluarsa;
    }

    public void setObat_kadaluarsa(String obat_kadaluarsa) {
        this.obat_kadaluarsa = obat_kadaluarsa;
    }

    public String getObat_produsen() {
        return obat_produsen;
    }

    public void setObat_produsen(String obat_produsen) {
        this.obat_produsen = obat_produsen;
    }
    
}
