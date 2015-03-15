package com.artificialliver.mapper;

import java.util.List;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.GuardianData;
import com.artificialliver.model.SyncObject;

public interface GuardianDataMapper extends BaseMapper<GuardianData> {
	public List<GuardianData> getAfter(SyncObject syncObject);
	public void deleteBefore(String time_stamp);
}
