package com.artificialliver.mapper;

import java.util.List;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.PumpSpeedData;
import com.artificialliver.model.SyncObject;

public interface PumpSpeedDataMapper extends BaseMapper<PumpSpeedData> {
	public List<PumpSpeedData> getAfter(SyncObject syncObject);
	public void deleteBefore(String time_stamp);
}
