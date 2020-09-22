package com.org.iscan.servlet.handler;

import com.org.claims.models.ContractBean;
import com.org.iscan.connection.downstream.UploadServerFacade;

public class UploadActionHandler extends RequestActionHandler implements UploadServerFacade {
	
	
	public UploadActionHandler(String action) {
		// TODO Auto-generated constructor stub
		super(action);
	}
	
	@Override
	public void handleRequest(Object obj) throws Exception{
		// TODO Auto-generated method stub
		// Connect to DAO and get contract ID
		if(action.equals("addNewPolicy")){
			addNewPolicy((ContractBean)obj);
		}else if(action.equals("calculateDamage")){
			calculateDamage((ContractBean)obj);
		}
		
	}
	
	@Override
	public Object getResponse() {
		// TODO Auto-generated method stub
		return responseXML.toString();
	}

	@Override
	public String addNewPolicy(ContractBean bean) throws Exception {
		// TODO Auto-generated method stub
		responseXML=new StringBuffer();
		openTag("ContractID");
			((StringBuffer) responseXML).append("Contract_253543");
		closeTag("ContractID");
		return null;
	}
	
	@Override
	public double calculateDamage(ContractBean bean) throws Exception {
		// TODO Auto-generated method stub
		responseXML=new StringBuffer();
		openTag("DamagePercent");
			((StringBuffer) responseXML).append("52");
		closeTag("DamagePercent");
		return 0;
	}
}
