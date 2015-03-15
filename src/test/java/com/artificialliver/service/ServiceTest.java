package com.artificialliver.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Before;
import org.junit.Test;

import com.artificialliver.model.OperationInfo;
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
		NameValuePair[] param = { new NameValuePair("surgery_no", "Hades"), new NameValuePair("time_stamp", "Hades") };
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
				"治疗方法一", "王医生", "1", "", "2015-03-08"));
		NameValuePair[] param = { new NameValuePair("operationInfo", json)};
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
}
