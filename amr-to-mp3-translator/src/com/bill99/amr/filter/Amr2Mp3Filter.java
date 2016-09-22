package com.bill99.amr.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bill99.amr.util.AudioUtils;

/**
 * 此过滤器用来拦截所有以amr后缀结尾的请求，并转换成mp3流输出，输出MIME类型为audio/mpeg.
 * @author zhangkenan
 *
 */
public class Amr2Mp3Filter implements Filter{
	
	/**
	 * mp3扩展名对应的MIME类型，值为"audio/mpeg"
	 */
	public final static String MP3_MIME_TYPE = "audio/mpeg";
	
	public void init(FilterConfig filterConfig) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest  request;
	    HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) resp;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
        
		String requstURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String resPath = requstURI;
		//去掉requstURI中contextPath部分和参数部分
		if(contextPath.length() > 0) {
			resPath = resPath.substring(contextPath.length());
		}
		int index = 0;
		if((index = resPath.lastIndexOf("?")) != -1) {
			resPath = resPath.substring(0, index);
		}
		
		String resRealPath = req.getServletContext().getRealPath(resPath);
		String mp3ResRealPath = resRealPath.replaceFirst(".amr$", ".mp3");
		File mp3File = new File(mp3ResRealPath);
		if(!mp3File.exists()) {
			File amrFile = new File(resRealPath);
			if(!amrFile.exists()) {
				filterChain.doFilter(request, response);
				return;
			}
			AudioUtils.amr2mp3(amrFile.getAbsolutePath(), mp3File.getAbsolutePath());
		}
		response.setContentLength((int)mp3File.length());
		response.setContentType(MP3_MIME_TYPE);
		InputStream in = new FileInputStream(mp3File);
		OutputStream out = response.getOutputStream();
		try {
			byte[] buf = new byte[1024];
			int len = -1;
			while((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.flush();
		}
	}

}
