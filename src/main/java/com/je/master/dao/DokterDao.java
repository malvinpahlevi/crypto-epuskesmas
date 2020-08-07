/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao;

import com.je.master.model.Dokter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ.P
 */
public interface DokterDao {
    
    public List<Dokter>getAll(int start, int limit, String order, Map<String, String> params);
    public Dokter getById(int id_dokter);
    public long insert(Dokter dokter);
    public long update(Dokter dokter);
    public long delete(Dokter dokter);
    public long count();
    public List<Dokter> searchData(String keyword);
    
}
