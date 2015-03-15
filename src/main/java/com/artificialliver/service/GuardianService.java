package com.artificialliver.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.base.BaseService;
import com.artificialliver.mapper.GuardianDataMapper;
import com.artificialliver.model.GuardianData;
import com.artificialliver.model.SyncObject;

@Service
public class GuardianService extends BaseService<GuardianData> {

	@Resource
	private GuardianDataMapper guardianDataMapper;
	
	@Override
	public BaseMapper<GuardianData> getMapper() {
		// TODO 自动生成的方法存根
		return guardianDataMapper;
	}

	public List<GuardianData> getAfterGuardianDatas(SyncObject syncObject){
		return guardianDataMapper.getAfter(syncObject);
	}
}
