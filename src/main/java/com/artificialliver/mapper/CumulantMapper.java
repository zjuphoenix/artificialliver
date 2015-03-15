package com.artificialliver.mapper;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.Cumulant;

public interface CumulantMapper extends BaseMapper<Cumulant> {
	public Cumulant getOne(String surgery_no);
}
