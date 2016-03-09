package com.artificialliver.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artificialliver.report.Reporter;
import com.itextpdf.text.DocumentException;

@Service
public class ReporterService {

	@Resource
	private CumulantService cumulantService;
	@Resource
	private BloodPressureService bloodPressureService;
	@Resource
	private HeartRateService heartRateService;
	@Resource
	private LiquidService liquidService;
	@Resource
	private PressureService pressureService;
	
	public ReporterService() {
		super();
		// TODO 自动生成的构造函数存根
	}



	public InputStream getReporterPdf(String operationInfoStr, String schemestr) throws DocumentException, ParseException, IOException{
		return new Reporter(cumulantService,bloodPressureService,heartRateService,liquidService,pressureService).getReporterPdf(operationInfoStr, schemestr);
	}
}
