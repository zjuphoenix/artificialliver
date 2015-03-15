package com.artificialliver.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.CumulantMapper;
import com.artificialliver.model.Cumulant;

@Service
public class CumulantService extends BaseService<Cumulant> {

	@Resource
	private CumulantMapper cumulantMapper;
	
	@Override
	public BaseMapper<Cumulant> getMapper() {
		// TODO 自动生成的方法存根
		return cumulantMapper;
	}

	public Cumulant getCumulant(String surgery_no){
		return cumulantMapper.getOne(surgery_no);
	}
}
