/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.dao;

import com.je.transaction.model.RawatKamarPasien;
import java.util.List;
import java.util.Map;

public interface RawatKamarPasienDao {
    
    public List<RawatKamarPasien> getAll(int start, int limit, String order, Map<String, String> params);
    public RawatKamarPasien getById(String id);
    public long insert(RawatKamarPasien rawatKamarPasien);
    public long update(RawatKamarPasien rawatKamarPasien);
    public long delete(RawatKamarPasien rawatKamarPasien);
    
}
