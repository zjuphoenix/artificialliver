package com.artificialliver.mapper;

import java.util.List;

import com.artificialliver.base.BaseMapper;
import com.artificialliver.model.LiquidData;

public interface LiquidDataMapper extends BaseMapper<LiquidData> {
	public List<LiquidData> getAll(String surgery_no);
}
