package com.artificialliver.api;

import java.io.InputStream;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.artificialliver.model.OperationInfo;

@Path("/")
@WebService
public interface ArtificialliverService {

	@POST
	@Path("/report")
	@Consumes("application/x-www-form-urlencoded")
	public InputStream getReportForm(@FormParam("operationInfo") String operationInfo);

	@POST
	@Path("/sync")
	@Consumes("application/x-www-form-urlencoded")
	public String getSyncData(@FormParam("surgery_no") String surgery_no, @FormParam("time_stamp") String time_stamp);
	
	@POST
	@Path("/test")
	@Consumes("application/x-www-form-urlencoded")
	String test(@FormParam("name") String name);
}
