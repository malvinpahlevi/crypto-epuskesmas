
package com.je.transaction.dao;

import com.je.transaction.model.ResepObat;
import java.util.List;

public interface ResepObatDao {
    
    public List<ResepObat> getAll();
    public ResepObat getById(int id_resepobat);
    public long insert(ResepObat resepObat);
    public long update(ResepObat resepObat);
    public long delete(ResepObat resepObat);
    public long count();
    public List<ResepObat> searchData(String keyword);
    
}
