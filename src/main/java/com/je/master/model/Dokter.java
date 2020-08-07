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
public class Dokter {
    
    private int id_dokter;
    private String dokter_nip;
    private String dokter_nama;
    private String dokter_jenkel;
    private String dokter_spesialis;

    public int getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(int id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getDokter_nip() {
        return dokter_nip;
    }

    public void setDokter_nip(String dokter_nip) {
        this.dokter_nip = dokter_nip;
    }

    public String getDokter_nama() {
        return dokter_nama;
    }

    public void setDokter_nama(String dokter_nama) {
        this.dokter_nama = dokter_nama;
    }

    public String getDokter_jenkel() {
        return dokter_jenkel;
    }

    public void setDokter_jenkel(String dokter_jenkel) {
        this.dokter_jenkel = dokter_jenkel;
    }

    public String getDokter_spesialis() {
        return dokter_spesialis;
    }

    public void setDokter_spesialis(String dokter_spesialis) {
        this.dokter_spesialis = dokter_spesialis;
    }
    
    
    
}
