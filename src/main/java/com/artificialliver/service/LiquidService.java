package com.artificialliver.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.LiquidDataMapper;
import com.artificialliver.model.LiquidData;

@Service
public class LiquidService extends BaseService<LiquidData> {

	@Resource
	private LiquidDataMapper liquidDataMapper;
	@Override
	public BaseMapper<LiquidData> getMapper() {
		// TODO 自动生成的方法存根
		return liquidDataMapper;
	}

	public List<LiquidData> getLiquidDatas(String surgery_no){
		return liquidDataMapper.getAll(surgery_no);
	}
}
