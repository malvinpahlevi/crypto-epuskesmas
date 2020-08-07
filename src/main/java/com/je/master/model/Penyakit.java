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
public class Penyakit {
    
    private int id_penyakit;
    private String penyakit_nama;
    private String penyakit_deskripsi;
    private String penyakit_jenis;

    public int getId_penyakit() {
        return id_penyakit;
    }

    public void setId_penyakit(int id_penyakit) {
        this.id_penyakit = id_penyakit;
    }

    public String getPenyakit_nama() {
        return penyakit_nama;
    }

    public void setPenyakit_nama(String penyakit_nama) {
        this.penyakit_nama = penyakit_nama;
    }

    public String getPenyakit_deskripsi() {
        return penyakit_deskripsi;
    }

    public void setPenyakit_deskripsi(String penyakit_deskripsi) {
        this.penyakit_deskripsi = penyakit_deskripsi;
    }

    public String getPenyakit_jenis() {
        return penyakit_jenis;
    }

    public void setPenyakit_jenis(String penyakit_jenis) {
        this.penyakit_jenis = penyakit_jenis;
    }
    
    
    
}
