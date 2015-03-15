package com.artificialliver.mapper;

import java.util.List;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.BloodPressureData;

public interface BloodPressureDataMapper extends BaseMapper<BloodPressureData> {
	public List<BloodPressureData> getAll(String surgery_no);
}
