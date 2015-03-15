package com.artificialliver.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.PressureDataMapper;
import com.artificialliver.model.PressureData;
import com.artificialliver.model.SyncObject;

@Service
public class PressureService extends BaseService<PressureData> {

	@Resource
	private PressureDataMapper pressureDataMapper;
	
	@Override
	public BaseMapper<PressureData> getMapper() {
		// TODO 自动生成的方法存根
		return pressureDataMapper;
	}
	
	public List<PressureData> getAfterPressureDatas(SyncObject syncObject){
		return pressureDataMapper.getAfter(syncObject);
	}
	
	public List<PressureData> getPressureDatas(String surgery_no){
		return pressureDataMapper.getAll(surgery_no);
	}

}
