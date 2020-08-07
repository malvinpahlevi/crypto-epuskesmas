package com.je.transaction.service;

import com.je.transaction.model.ResepObat;
import java.util.List;

public interface ResepObatService {
    public List<ResepObat> getAll();
    public ResepObat getById(int id_resepobat);
    public long insert(ResepObat resepObat);
    public long update(ResepObat resepObat);
    public long delete(ResepObat resepObat);
    public long count();
    public List<ResepObat> searchKeyword(String keyword);    
}
