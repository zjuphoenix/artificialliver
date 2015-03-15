package com.artificialliver.report;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.artificialliver.model.BloodPressureData;
import com.artificialliver.model.Cumulant;
import com.artificialliver.model.HeartRateData;
import com.artificialliver.model.LiquidData;
import com.artificialliver.model.OperationInfo;
import com.artificialliver.model.PressureData;
import com.artificialliver.service.BloodPressureService;
import com.artificialliver.service.CumulantService;
import com.artificialliver.service.HeartRateService;
import com.artificialliver.service.LiquidService;
import com.artificialliver.service.PressureService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class Reporter extends Document {
	BaseFont bfChinese;
	com.itextpdf.text.Font titleFontChinese;
	com.itextpdf.text.Font baseFontChinese;
	com.itextpdf.text.Font baseBoldFontChinese;
	com.itextpdf.text.Font baseTableFontChinese;
	String surgeryNo;
	Chapter chapter;
	OperationInfo operationInfo;
	
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

	
	public Reporter(){
		super(PageSize.A4, 50, 50, 50, 50);
	}
	

	public void initial(String operationInfoStr) throws DocumentException, IOException{
		Gson gson = new Gson();
		OperationInfo operation = gson.fromJson(operationInfoStr, OperationInfo.class);
		this.operationInfo = operation;
		this.surgeryNo = operation.operationNo;
		bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		titleFontChinese = new com.itextpdf.text.Font(bfChinese, 18,
				com.itextpdf.text.Font.BOLDITALIC, new CMYKColor(0, 255, 255,
						17));
		baseFontChinese = new com.itextpdf.text.Font(bfChinese, 10,
				com.itextpdf.text.Font.NORMAL, new CMYKColor(0, 255, 255, 255));
		baseBoldFontChinese = new com.itextpdf.text.Font(bfChinese, 10,
				com.itextpdf.text.Font.BOLD, new CMYKColor(0, 255, 255, 255));
		baseTableFontChinese = new com.itextpdf.text.Font(bfChinese, 8,
				com.itextpdf.text.Font.NORMAL, new CMYKColor(0, 255, 255, 255));
		PdfWriter.getInstance(this, new FileOutputStream("./"+surgeryNo+operation.patientName+operation.time+".pdf"));
		this.open();
	}
	/**
	 * 增加表头
	 */
	public void setHeader() throws DocumentException {
		Paragraph title = new Paragraph("     浙江大学第一附属医院人工肝治疗仪器状态参数报告",
				titleFontChinese);
		chapter = new Chapter(title, 1);
		chapter.setNumberDepth(0);

		// base information
		Paragraph baseInformation = new Paragraph(
				String.format(
						"\n                    姓名 :%s          性别 : %s           年龄 :%s           治疗方法:%s          检验时间 :%s     \n                    手术号 : %s                                            医生 :%s                                     备注：%s\n              --------------------------------------------------------------------------------------------------------\n",
						operationInfo.patientName, operationInfo.gender,
						operationInfo.age, operationInfo.treatMethod,
						operationInfo.time, operationInfo.operationNo,
						operationInfo.doctor, operationInfo.extraInfo),
				baseFontChinese);
		chapter.add(baseInformation);
	}

	public void setCumulantParagraph() throws DocumentException {
		Cumulant cumulant = cumulantService.getCumulant(surgeryNo);

		PdfPTable t = new PdfPTable(3);
		t.setSpacingBefore(5);
		t.setSpacingAfter(25);
		t.setTotalWidth(400);
		t.setLockedWidth(true);
		t.getDefaultCell().setBorder(0);

		String[] strings = new String[] { "治疗时间(累计)", cumulant.cumulative_time,
				"Min", "血液循环量(累计)", cumulant.blood_pump_t, "L", "分离泵(累计)",
				cumulant.separation_pump_t, "L", "透析液泵(累计)",
				cumulant.dialysis_pump_t, "L", "补液泵(累计)",
				cumulant.tripe_pump_t, "L", "滤过泵(累计)",
				cumulant.filtration_pump_t, "L", "循环泵(累计)",
				cumulant.circulating_pump_t, "L", "肝素泵(累计)",
				cumulant.heparin_pump_t, "ml" };
		for (String string : strings) {
			PdfPCell c = new PdfPCell(new Phrase(string, baseTableFontChinese));
			c.setBorder(0);
			t.addCell(c);
		}

		chapter.add(new Paragraph(
				"\n                       基本项目                                            检测值                                                 单位 ",
				baseBoldFontChinese));
		chapter.add(new Paragraph(
				"                        --------------------------------------------------------------------------------------------------------",
				baseTableFontChinese));
		chapter.add(t);
		chapter.add(new Paragraph(
				"              --------------------------------------------------------------------------------------------------------\n",
				baseFontChinese));
	}

	public void publish(String operationInfo) throws DocumentException, ParseException, IOException {
		this.initial(operationInfo);
		this.setHeader();
		this.setCumulantParagraph();
		this.setPressureParagraph();
		this.setLiquidParagraph();
		this.setHeartRateParagraph();
		this.etBloodPressureParagraph();
		this.addTailer();
		this.add(chapter);
		this.close();
	}
	
	public InputStream getReporterPdf(String operationInfoStr) throws DocumentException, ParseException, IOException{
		this.publish(operationInfoStr);
		return new FileInputStream("./"+surgeryNo+operationInfo.patientName+operationInfo.time+".pdf");
	}

	public static JFreeChart createChart(TimeSeriesCollection lineDataset,
			String title, String ylabel, String xlabel) {
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, // 标题
				xlabel, // categoryAxisLabel （category轴，横轴，X轴的标签）
				ylabel, // valueAxisLabel（value轴，纵轴，Y轴的标签）
				lineDataset,// dataset
				true, // legend
				true, // tooltips
				true); // URLs

		// 配置字体（解决中文乱码的通用方法）
		Font xfont = new Font("宋体", Font.PLAIN, 10); // X轴
		Font yfont = new Font("宋体", Font.PLAIN, 10); // Y轴
		Font kfont = new Font("宋体", Font.PLAIN, 8); // 底部
		Font titleFont = new Font("隶书", Font.BOLD, 14); // 图片标题

		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot(); // 获得 plot：XYPlot！

		xyplot.getDomainAxis().setLabelFont(xfont);
		xyplot.getRangeAxis().setLabelFont(yfont);
		jfreechart.getLegend().setItemFont(kfont);
		jfreechart.getTitle().setFont(titleFont);

		// 设置时间格式，同时也解决了乱码问题
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		SimpleDateFormat sfd = new SimpleDateFormat("HH-mm");
		dateaxis.setDateFormatOverride(sfd);
		xyplot.setDomainAxis(dateaxis);

		// 以下的设置可以由用户定制，也可以省略
		XYPlot plot = (XYPlot) jfreechart.getPlot();
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot
				.getRenderer();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 设置曲线图与xy轴的距离
		plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
		// 设置曲线是否显示数据点
		// xylineandshaperenderer.setBaseShapesVisible(true);
		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		// xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
		plot.setRenderer(xyitem);

		return jfreechart;
	}

	public void setLiquidParagraph() throws ParseException, IOException,
			DocumentException {
		List<LiquidData> liquidDatas = liquidService.getLiquidDatas(surgeryNo);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("血液循环累计", Second.class);
		TimeSeries timeSeries2 = new TimeSeries("补液累计", Second.class);
		TimeSeries timeSeries3 = new TimeSeries("废液累计", Second.class);
		for (LiquidData liquidData : liquidDatas) {
			Date date = dateFormat.parse(liquidData.time_stamp);
			Second second = new Second(date);
			timeSeries1
					.add(second, Double.parseDouble(liquidData.blood_pump_t));
			timeSeries2
					.add(second, Double.parseDouble(liquidData.tripe_pump_t));
			timeSeries3.add(second,
					Double.parseDouble(liquidData.filtration_pump_t));

		}
		lineDataset.addSeries(timeSeries1);
		lineDataset.addSeries(timeSeries2);
		lineDataset.addSeries(timeSeries3);
		JFreeChart chart = createChart(lineDataset, "液体累积量变化图", "累积量", "时间");
		BufferedImage bufferedImage = chart.createBufferedImage(400, 200);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", out);
		byte[] bytes = out.toByteArray();
		Image img = Image.getInstance(bytes);
		chapter.add(img);
		chapter.add(new Paragraph(
				"              --------------------------------------------------------------------------------------------------------\n",
				baseFontChinese));
	}

	public void setPressureParagraph() throws ParseException, IOException,
			DocumentException {
		List<PressureData> PressureDatas = pressureService.getPressureDatas(surgeryNo);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("采血压", Second.class);
		TimeSeries timeSeries2 = new TimeSeries("血浆入口压力", Second.class);
		TimeSeries timeSeries3 = new TimeSeries("动脉压", Second.class);
		TimeSeries timeSeries4 = new TimeSeries("静脉压", Second.class);
		TimeSeries timeSeries5 = new TimeSeries("血浆压", Second.class);
		TimeSeries timeSeries6 = new TimeSeries("夸膜压", Second.class);
		for (PressureData pressureData : PressureDatas) {
			Date date = dateFormat.parse(pressureData.time_stamp);
			Second second = new Second(date);
			timeSeries1.add(second,
					Double.parseDouble(pressureData.in_blood_pressure));
			timeSeries2.add(second,
					Double.parseDouble(pressureData.plasma_inlet_pressure));
			timeSeries3.add(second,
					Double.parseDouble(pressureData.arterial_pressure));
			timeSeries4.add(second,
					Double.parseDouble(pressureData.venous_pressure));
			timeSeries5.add(second,
					Double.parseDouble(pressureData.plasma_pressure));
			timeSeries6.add(second,
					Double.parseDouble(pressureData.transmembrane_pressure));
		}
		lineDataset.addSeries(timeSeries1);
		lineDataset.addSeries(timeSeries2);
		lineDataset.addSeries(timeSeries3);
		lineDataset.addSeries(timeSeries4);
		lineDataset.addSeries(timeSeries5);
		lineDataset.addSeries(timeSeries6);
		JFreeChart chart = createChart(lineDataset, "压力变化图", "压力值", "时间");
		BufferedImage bufferedImage = chart.createBufferedImage(400, 200);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", out);
		byte[] bytes = out.toByteArray();
		Image img = Image.getInstance(bytes);
		chapter.add(img);
		chapter.add(new Paragraph(
				"              --------------------------------------------------------------------------------------------------------\n",
				baseFontChinese));
	}

	public void setHeartRateParagraph() throws ParseException, IOException,
			DocumentException {
		List<HeartRateData> heartRateDatas = heartRateService
				.getHeartRateDatas(surgeryNo);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("心率", Second.class);
		for (HeartRateData heartRateData : heartRateDatas) {
			Date date = dateFormat.parse(heartRateData.time_stamp);
			Second second = new Second(date);
			timeSeries1.add(second,
					Double.parseDouble(heartRateData.heart_rate));
		}
		lineDataset.addSeries(timeSeries1);
		JFreeChart chart = createChart(lineDataset, "心率变化图", "心率值", "时间");
		BufferedImage bufferedImage = chart.createBufferedImage(400, 200);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", out);
		byte[] bytes = out.toByteArray();
		Image img = Image.getInstance(bytes);
		chapter.add(img);
		chapter.add(new Paragraph(
				" \n            --------------------------------------------------------------------------------------------------------\n",
				baseFontChinese));
	}

	public void etBloodPressureParagraph() throws ParseException, IOException,
			DocumentException {
		List<BloodPressureData> bloodPressureDatas = bloodPressureService
				.getBloodPressureDatas(surgeryNo);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("舒张压", Second.class);
		TimeSeries timeSeries2 = new TimeSeries("收缩压", Second.class);
		for (BloodPressureData bloodPressureData : bloodPressureDatas) {
			Date date = dateFormat.parse(bloodPressureData.time_stamp);
			Second second = new Second(date);
			timeSeries1.add(second,
					Double.parseDouble(bloodPressureData.diastolic_pressure));
			timeSeries2.add(second,
					Double.parseDouble(bloodPressureData.systolic_pressure));
		}
		lineDataset.addSeries(timeSeries1);
		lineDataset.addSeries(timeSeries2);
		JFreeChart chart = createChart(lineDataset, "血压变化图", "心率值", "时间");
		BufferedImage bufferedImage = chart.createBufferedImage(400, 200);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", out);
		byte[] bytes = out.toByteArray();
		Image img = Image.getInstance(bytes);
		chapter.add(img);
		chapter.add(new Paragraph(
				"             --------------------------------------------------------------------------------------------------------\n",
				baseFontChinese));
	}

	public void addTailer() {
		chapter.add(new Paragraph(
				"                                                                                                                            操作人 :                    审核人 :        ",
				baseFontChinese));
	}

	public static void main(String[] args) throws DocumentException,
			IOException, ParseException {
		//Reporter report = new Reporter(new OperationInfo("张三", "男", "20","治疗方法一", "王医生", "1", "", "2015-03-08"));
		//report.publish();
		OperationInfo op = new OperationInfo("张三", "男", "20","治疗方法一", "王医生", "1", "", "2015-03-08");
		Gson gson = new Gson();
		String json = gson.toJson(op);
		OperationInfo operation = gson.fromJson(json, OperationInfo.class);
		System.out.println("sss");
	}
}
