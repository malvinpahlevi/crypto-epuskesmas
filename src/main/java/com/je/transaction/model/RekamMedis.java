/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.model;

import com.je.master.model.Dokter;
import com.je.master.model.Pasien;
import com.je.master.model.Penyakit;
import java.sql.Timestamp;

/**
 *
 * @author AJ.P
 */
public class RekamMedis {
    
    
    private String rekammedis_id;
    private Dokter dokter;
    private Pasien pasien;
    private Penyakit penyakit;
    private Timestamp rekammedis_tglkunjungan;
    private String rekammedis_keluhan;
    private String rekammedis_diagnosa;
    private String rekammedis_tindakan;

    public String getRekammedis_id() {
        return rekammedis_id;
    }

    public void setRekammedis_id(String rekammedis_id) {
        this.rekammedis_id = rekammedis_id;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    public Penyakit getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(Penyakit penyakit) {
        this.penyakit = penyakit;
    }

    public Timestamp getRekammedis_tglkunjungan() {
        return rekammedis_tglkunjungan;
    }

    public void setRekammedis_tglkunjungan(Timestamp rekammedis_tglkunjungan) {
        this.rekammedis_tglkunjungan = rekammedis_tglkunjungan;
    }

    public String getRekammedis_keluhan() {
        return rekammedis_keluhan;
    }

    public void setRekammedis_keluhan(String rekammedis_keluhan) {
        this.rekammedis_keluhan = rekammedis_keluhan;
    }

    public String getRekammedis_diagnosa() {
        return rekammedis_diagnosa;
    }

    public void setRekammedis_diagnosa(String rekammedis_diagnosa) {
        this.rekammedis_diagnosa = rekammedis_diagnosa;
    }

    public String getRekammedis_tindakan() {
        return rekammedis_tindakan;
    }

    public void setRekammedis_tindakan(String rekammedis_tindakan) {
        this.rekammedis_tindakan = rekammedis_tindakan;
    }
    
    
    
}
