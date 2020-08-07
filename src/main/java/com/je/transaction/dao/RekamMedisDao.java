/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.dao;

import com.je.transaction.model.RekamMedis;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ.P
 */
public interface RekamMedisDao {
    
    public List<RekamMedis> getAll();
    public RekamMedis getById(String rekammmedis_id);
    public long insert(RekamMedis rekamMedis);
    public long update(RekamMedis rekamMedis);
    public long delete(RekamMedis rekamMedis);
    public long count();
    public List<RekamMedis> searchData(String keyword);
    
    
}
