package com.artificialliver.mapper;

import java.util.List;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.HeartRateData;


public interface HeartRateDataMapper extends BaseMapper<HeartRateData> {
	public List<HeartRateData> getAll(String surgery_no);
}
