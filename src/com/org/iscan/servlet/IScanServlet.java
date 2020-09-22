package com.org.iscan.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.org.claims.models.CatalogBean;
import com.org.claims.models.ContractBean;
import com.org.claims.models.response.XmlResponse;
import com.org.iscan.servlet.handler.RequestActionHandler;
import com.org.iscan.servlet.handler.ActionHandler;

/**
 * Servlet implementation class IScanServlet
 */
public class IScanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final String imageLocation="C:\\Users\\Kabilans\\Desktop\\IntelliScan\\";
    StringBuffer buffer;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IScanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println(ServletFileUpload.isMultipartContent(request));
		
		String requestAction=request.getParameter("requestAction");
		
		buffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		openTag("IntelliScanServlet");
		openTag("IntelliScanServletResponse");

		OutputStream outputStream=response.getOutputStream();
		
		int responseType=ActionHandler.XML_RESPONSE;
		
		try {
			
			Object object=null;
			
			if(ServletFileUpload.isMultipartContent(request)){
				ContractBean bean=getRequestAsBean(request);				
				requestAction=bean.getRequestAction();
				object=bean;
			}else{
				object=request;
			}			

			ActionHandler handler= RequestActionHandler.getActionHandler(requestAction);
			
			handler.handleRequest(object);
			
			Object servResponse=handler.getResponse();
			
			if(handler.getResponseType()==ActionHandler.XML_RESPONSE){
				setResponseStatus(XmlResponse.STATUS_CD_SUCCESS, "Processing Success");
				buffer.append((String)servResponse);
			}else if(handler.getResponseType()==ActionHandler.STREAM_RESPONSE){
				responseType=ActionHandler.STREAM_RESPONSE;
				response.setContentType("image/jpeg");
				/*commented out as no support for ImageIO in GAE
				BufferedImage image=ImageIO.read((InputStream)servResponse);
				ImageIO.write(image, "jpg", outputStream);*/
				((InputStream)servResponse).close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setContentType("text/xml;charset=UTF-8");
			setResponseStatus(XmlResponse.STATUS_CD_ERROR, "Error occured while processing request. Please try again later");
		}

		closeTag("IntelliScanServletResponse");
		closeTag("IntelliScanServlet");
		System.out.println(buffer.toString());
		
		if(responseType==ActionHandler.XML_RESPONSE){
			IOUtils.write(buffer.toString(), outputStream);
		}
		outputStream.close();
	}
	
	private void openTag(String tagName){
		buffer.append("<"+tagName+">");
	}
	
	private void closeTag(String tagName){
		buffer.append("</"+tagName+">");
	}
	
	private void setResponseStatus(int status, String desc){
		XmlResponse response=new XmlResponse(); 
		response.setStatusCode(status);
		response.setErrorMessage(desc);
		buffer.append(response.toString());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse responseXML)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse responseXML)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	private static ContractBean getRequestAsBean(HttpServletRequest request) throws FileNotFoundException, IOException, FileUploadException{
		ContractBean cb=new ContractBean();
		ServletFileUpload handler=new ServletFileUpload();
		FileItemIterator it=handler.getItemIterator(request);
		while (it.hasNext()) {
			FileItemStream item = it.next();
			String name = item.getFieldName();
			try{
				if(item.isFormField()) {
					cb.getClass().getField(name).set(cb,Streams.asString(item.openStream(), "UTF-8"));
				}else {
					CatalogBean ctb=new CatalogBean();
					String fileName=imageLocation+File.separator+Calendar.getInstance().getTimeInMillis()+".JPG";
					File file=new File(fileName);
					/*commented out as File System not supported in GAE
					IOUtils.copy(item.openStream(), new FileOutputStream(file));*/
					ctb.file=file;
					cb.getClass().getField(name).set(cb,ctb);
				}
			}catch(IllegalAccessException e){}catch (NoSuchFieldException e) {}
		}
		return cb;
	}
	
	public static HashMap<String, Object> getRequestParams(HttpServletRequest request) throws FileUploadException, IOException{
		HashMap<String, Object> params=new HashMap<String, Object>();
		ServletFileUpload handler=new ServletFileUpload();
		FileItemIterator it=handler.getItemIterator(request);
		while (it.hasNext()) {
			FileItemStream item = it.next();
			String name = item.getFieldName();
			if(item.isFormField()) {
			   // Normal field
			   String value = Streams.asString(item.openStream());
			   params.put(name, value);
			} else {
			   // File
				params.put(name, item.openStream());
			}
		}
		return params;
	}
}
