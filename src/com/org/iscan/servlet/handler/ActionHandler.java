package com.org.iscan.servlet.handler;


public abstract class ActionHandler {
	public static final int XML_RESPONSE=1;
	public static final int STREAM_RESPONSE=2;
	public abstract void handleRequest(Object obj) throws Exception;
	public abstract int getResponseType();
	public abstract Object getResponse();
	
	public static ActionHandler getActionHandler(String action){
		ActionHandler handler=null;
		if(action.equalsIgnoreCase("addNewPolicy")||action.equalsIgnoreCase("calculateDamage")){
			handler=new UploadActionHandler(action);
		}else if(action.equalsIgnoreCase("getPolicyList")||action.equalsIgnoreCase("getPolicyDetails")||action.equalsIgnoreCase("getPolicyCatalog")){
			handler=new DownloadActionHandler(action);
		}
		return handler;
	}
}
