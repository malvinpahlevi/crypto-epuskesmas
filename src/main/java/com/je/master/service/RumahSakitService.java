package com.je.master.service;

import java.util.List;
import java.util.Map;

import com.je.master.model.RumahSakit;

public interface RumahSakitService {

	 public List<RumahSakit> getAll(int start, int limit, String order, Map<String, String> params);
	 public RumahSakit getById(String kode);
	 public long count(Map<String, String> params);
	 public long create(RumahSakit rumahSakit);
	 public long update(RumahSakit rumahSakit);
	 public long remove(RumahSakit rumahSakit);
	
}
