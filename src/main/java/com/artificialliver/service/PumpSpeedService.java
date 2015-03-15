package com.artificialliver.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.PumpSpeedDataMapper;
import com.artificialliver.model.PumpSpeedData;
import com.artificialliver.model.SyncObject;

@Service
public class PumpSpeedService extends BaseService<PumpSpeedData> {

	@Resource
	private PumpSpeedDataMapper pumpSpeedDataMapper;
	@Override
	public BaseMapper<PumpSpeedData> getMapper() {
		// TODO 自动生成的方法存根
		return pumpSpeedDataMapper;
	}

	public List<PumpSpeedData> getAfterPumpSpeedDatas(SyncObject syncObject){
		return pumpSpeedDataMapper.getAfter(syncObject);
	}
}
