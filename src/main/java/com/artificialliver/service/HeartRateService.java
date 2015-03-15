package com.artificialliver.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.HeartRateDataMapper;
import com.artificialliver.model.HeartRateData;

@Service
public class HeartRateService extends BaseService<HeartRateData> {

	@Resource
	private HeartRateDataMapper heartRateDataMapper;

	@Override
	public BaseMapper<HeartRateData> getMapper() {
		// TODO 自动生成的方法存根
		return heartRateDataMapper;
	}

	public List<HeartRateData> getHeartRateDatas(String surgery_no) {
		return heartRateDataMapper.getAll(surgery_no);
	}

}
