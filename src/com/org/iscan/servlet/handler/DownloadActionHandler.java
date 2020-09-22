package com.org.iscan.servlet.handler;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.org.claims.models.CatalogBean;
import com.org.claims.models.ContractBean;
import com.org.iscan.connection.upstream.DownloadServerFacade;

public class DownloadActionHandler extends RequestActionHandler implements
		DownloadServerFacade {

	public DownloadActionHandler(String action) {
		super(action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ContractBean getPolicyDetails(String contractID) throws Exception {
		// TODO Auto-generated method stub
		// Connect to DAO and return a xml response with details
		responseXML=new StringBuffer();
		openTag("ContractID");
			responseXML.append(contractID);
		closeTag("ContractID");
		openTag("modelType");
			responseXML.append(contractID);
		closeTag("modelType");
		openTag("vehicleType");
			responseXML.append(contractID);
		closeTag("vehicleType");
		openTag("vehicleYear");
			responseXML.append(contractID);
		closeTag("vehicleYear");
		openTag("frequency");
			responseXML.append(contractID);
		closeTag("frequency");
		openTag("premium");
			responseXML.append(contractID);
		closeTag("premium");
		openTag("coverage");
			responseXML.append(contractID);
		closeTag("coverage");
		openTag("vehicle");
			responseXML.append(contractID);
		closeTag("vehicle");
		openTag("firstName");
			responseXML.append(contractID);
		closeTag("firstName");
		openTag("lastName");
			responseXML.append(contractID);
		closeTag("lastName");
		openTag("gender");
			responseXML.append(contractID);
		closeTag("gender");
		openTag("ssn");
			responseXML.append(contractID);
		closeTag("ssn");
		openTag("state");
			responseXML.append(contractID);
		closeTag("state");
		openTag("dataOfBirth");
			responseXML.append(contractID);
		closeTag("dataOfBirth");
		return null;
	}

	@Override
	public CatalogBean getPolicyCatalog(String contractID, int view)
			throws Exception {
		// TODO Auto-generated method stub
		setResponseType(STREAM_RESPONSE);
		File leftFile=new File("C:\\Users\\Kabilans\\Desktop\\spectrum_of_the_sky_hdtv_1080p-HD.jpg");
		stream=new FileInputStream(leftFile);
		return null;
	}

	@Override
	public List<String> getPolicyList(String customerID) throws Exception {
		// TODO Auto-generated method stub
		// Connect to DAO and return a list
		responseXML=new StringBuffer();
		openTag("ContractID");
			responseXML.append("253543");
		closeTag("ContractID");
		openTag("ContractID");
			responseXML.append("253962");
		closeTag("ContractID");
		openTag("ContractID");
			responseXML.append("253964");
		closeTag("ContractID");
		return null;
	}

	@Override
	public void handleRequest(Object obj) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) obj;
		if(action.equals("getPolicyList")){
			String customerID=request.getParameter("customerID");
			getPolicyList(customerID);
		}else if(action.equals("getPolicyDetails")){
			String contractID=request.getParameter("damagePercent");
			getPolicyDetails(contractID);
		}else if(action.equals("getPolicyCatalog")){
			String contractID=request.getParameter("damagePercent");
			getPolicyCatalog(contractID,0);
		}
	}

}
