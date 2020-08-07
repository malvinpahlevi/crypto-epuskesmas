package com.je.master.dao;

import java.util.List;
import java.util.Map;

import com.je.master.model.RumahSakit;

public interface RumahSakitDao {
	
	 public List<RumahSakit> getAll(int start, int limit, String order, Map<String, String> params);
	 public RumahSakit getById(String id);
	 public long create(RumahSakit rumahSakit);
	 public long update(RumahSakit rumahSakit);
	 public long remove(RumahSakit rumahSakit);
	 public long count(Map<String, String> params);
}
