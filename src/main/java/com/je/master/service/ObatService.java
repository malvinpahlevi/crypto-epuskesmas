/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service;

import com.je.master.model.Obat;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ.P
 */
public interface ObatService {
   
    public List<Obat> getAll(int start, int limit, String order, Map<String, String> params);
    public Obat getById(int id_obat);
    public long insert(Obat obat);
    public long update(Obat obat);
    public long delete(Obat obat);
    public long count();
    public List<Obat> searchKeyword(String keyword);
}
