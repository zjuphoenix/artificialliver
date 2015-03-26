package com.artificialliver.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;
import com.artificialliver.api.ArtificialliverService;
import com.artificialliver.model.GuardianData;
import com.artificialliver.model.PressureData;
import com.artificialliver.model.PumpSpeedData;
import com.artificialliver.model.SyncObject;
import com.artificialliver.model.SyncResult;
import com.artificialliver.service.GuardianService;
import com.artificialliver.service.PressureService;
import com.artificialliver.service.PumpSpeedService;
import com.artificialliver.service.ReporterService;
import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;

public class ArtificialliverServiceImpl implements ArtificialliverService {

	public static Gson gson = new Gson();

	@Resource
	private GuardianService guardianService;
	@Resource
	private PumpSpeedService pumpSpeedService;
	@Resource
	private PressureService pressureService;
	
	
	@Resource
	private ReporterService reporterService;


	@Override
	public String getSyncData(String surgery_no, String time_stamp) {
		SyncResult syncResult = null;
		try {
			SyncObject syncObject = new SyncObject(surgery_no, time_stamp);
			List<GuardianData> guardianDatas = guardianService
					.getAfterGuardianDatas(syncObject);
			List<PressureData> pressureDatas = pressureService
					.getAfterPressureDatas(syncObject);
			List<PumpSpeedData> pumpSpeedDatas = pumpSpeedService
					.getAfterPumpSpeedDatas(syncObject);
			syncResult = new SyncResult(guardianDatas, pumpSpeedDatas,
				pressureDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = gson.toJson(syncResult);
		return str;
	}


	@Override
	public InputStream getReportForm(String operationInfo, String schemestr) {
		try {
			return reporterService.getReporterPdf(operationInfo, schemestr);
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;

	}


	@Override
	public String test(String name) {
		// TODO 自动生成的方法存根
		return "Hello world, hello " + name + "!";
	}

}
