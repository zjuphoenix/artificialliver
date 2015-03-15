package com.artificialliver.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.BloodPressureDataMapper;
import com.artificialliver.model.BloodPressureData;

@Service
public class BloodPressureService extends BaseService<BloodPressureData> {

	@Resource
	private BloodPressureDataMapper bloodPressureDataMapper;
	
	@Override
	public BaseMapper<BloodPressureData> getMapper() {
		// TODO 自动生成的方法存根
		return bloodPressureDataMapper;
	}
	
	public List<BloodPressureData> getBloodPressureDatas(String surgery_no){
		return bloodPressureDataMapper.getAll(surgery_no);
	}

}
