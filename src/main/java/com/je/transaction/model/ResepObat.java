package com.je.transaction.model;

import com.je.master.model.Obat;

public class ResepObat {
    
    private int id_resepobat;
    private RekamMedis rekamMedis;
    private Obat obat;
    private String resepobat_jumlah;

    public int getId_resepobat() {
        return id_resepobat;
    }

    public void setId_resepobat(int id_resepobat) {
        this.id_resepobat = id_resepobat;
    }

    public RekamMedis getRekamMedis() {
        return rekamMedis;
    }

    public void setRekamMedis(RekamMedis rekamMedis) {
        this.rekamMedis = rekamMedis;
    }

    public Obat getObat() {
        return obat;
    }

    public void setObat(Obat obat) {
        this.obat = obat;
    }
    
    public String getResepobat_jumlah() {
        return resepobat_jumlah;
    }

    public void setResepobat_jumlah(String resepobat_jumlah) {
        this.resepobat_jumlah = resepobat_jumlah;
    }
        
}
