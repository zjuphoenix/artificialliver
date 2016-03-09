package com.artificialliver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Before;
import org.junit.Test;

import com.artificialliver.model.OperationInfo;
import com.artificialliver.model.Scheme;
import com.google.gson.Gson;

public class ServiceTest {
	public final String URL = "http://localhost:8080/webservice/artificialliver/";
	private HttpClient client;

	@Before
	public void setUp() throws Exception {
		client = new HttpClient();
	}
	

	@Test
	public void test() {
		String url = URL + "test";
		System.out.println("request url:\t" + url);
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] param = { new NameValuePair("name", "Hades") };
		postMethod.setRequestBody(param);
		try {
			int statCode = client.executeMethod(postMethod);
			if (statCode == HttpStatus.SC_OK) {
				String response = postMethod.getResponseBodyAsString();
				System.out.println(response);
			} else {
				System.out.println(statCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	@Test
	public void sync() {
		String url = URL + "sync";
		System.out.println("request url:\t" + url);
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] param = { new NameValuePair("surgery_no", "201411180198"),
				new NameValuePair("time_stamp", "2015-07-10 15:25:08") };
		postMethod.setRequestBody(param);
		try {
			int statCode = client.executeMethod(postMethod);
			if (statCode == HttpStatus.SC_OK) {
				String response = postMethod.getResponseBodyAsString();
				System.out.println(response);
			} else {
				System.out.println(statCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	@Test
	public void report() {
		String url = URL + "report";
		System.out.println("request url:\t" + url);
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		Gson gson = new Gson();
		String json = gson.toJson(new OperationInfo("张三", "男", "20",
				"方法一", "王医生", "201411180198", "2015-07-10 15:25:08", "120"));
		List<Scheme> schemes = new ArrayList<Scheme>();
		schemes.add(new Scheme("阶段1", "2015-07-10 15:25:08"));
		schemes.add(new Scheme("阶段2", "2015-07-10 15:39:30 "));
		schemes.add(new Scheme("stop ", "2015-07-10 16:01:57"));
		String jsonSchemes = gson.toJson(schemes);
		NameValuePair[] param = { new NameValuePair("operationInfo", json), new NameValuePair("schemes", jsonSchemes)};
		postMethod.setRequestBody(param);
		try {
			int statCode = client.executeMethod(postMethod);
			if (statCode == HttpStatus.SC_OK) {
				InputStream response = postMethod.getResponseBodyAsStream();
				try {
					String filename = URLDecoder.decode(ServiceTest.class.getResource("/").getPath()+"report.pdf", "UTF-8");
					File file=new File(filename);
					file.createNewFile();
					OutputStream os = new FileOutputStream(file);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = response.read(buffer, 0, 8192)) != -1) {
						os.write(buffer, 0, bytesRead);
					}
					os.close();
					response.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(statCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
}
