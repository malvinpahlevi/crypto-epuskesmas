/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao;

import com.je.master.model.Penyakit;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ.P
 */
public interface PenyakitDao {
    
    public List<Penyakit>getAll(int start, int limit, String order, Map<String, String> params);
    public Penyakit getById(int id_penyakit);
    public long insert(Penyakit penyakit);
    public long update(Penyakit penyakit);
    public long delete(Penyakit penyakit);
    public long count();
    public List<Penyakit> searchData(String keyword);
}
