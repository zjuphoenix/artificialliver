package com.artificialliver.mapper;

import java.util.List;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.PressureData;
import com.artificialliver.model.SyncObject;

public interface PressureDataMapper extends BaseMapper<PressureData> {
	public List<PressureData> getAll(String surgery_no);
	public List<PressureData> getAfter(SyncObject syncObject);
	public void deleteBefore(String time_stamp);
}
