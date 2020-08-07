/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.service;

import com.je.transaction.model.RekamMedis;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ.P
 */
public interface RekamMedisService {
    public List<RekamMedis> getAll();
    public RekamMedis getById(String rekammedis_id);
    public long insert(RekamMedis rekamMedis);
    public long update(RekamMedis rekamMedis);
    public long delete(RekamMedis rekamMedis);
    public long count();
    public List<RekamMedis> searchKeyword(String keyword);
}
