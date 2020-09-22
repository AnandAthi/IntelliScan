package com.org.iscan.servlet.handler;

import java.io.InputStream;


public abstract class RequestActionHandler extends ActionHandler{
	
	String action;
	int responseType=ActionHandler.XML_RESPONSE;

	StringBuffer responseXML;
	InputStream stream;
	
	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public RequestActionHandler(String action) {
		// TODO Auto-generated constructor stub
		this.action=action;
	}

	@Override
	public Object getResponse() {
		// TODO Auto-generated method stub
		if(getResponseType()==XML_RESPONSE)
			return responseXML.toString();
		else if(getResponseType()==STREAM_RESPONSE)
			return stream;
		return null;
	}
	
	protected void openTag(String tagName){
		responseXML.append("<"+tagName+">");
	}
	
	protected void closeTag(String tagName){
		responseXML.append("</"+tagName+">");
	}
	
	public int getResponseType() {
		return responseType;
	}

	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}
}
