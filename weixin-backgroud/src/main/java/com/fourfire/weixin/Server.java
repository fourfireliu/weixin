package com.fourfire.weixin;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fourfire.weixin.constant.Constant;
import com.fourfire.weixin.entity.xml.InputMessage;
import com.fourfire.weixin.enums.MsgType;
import com.fourfire.weixin.factory.XStreamFactory;
import com.fourfire.weixin.util.StringUtil;
import com.thoughtworks.xstream.XStream;


@RestController
@EnableAutoConfiguration
public class Server implements EmbeddedServletContainerCustomizer {
    private static final Logger logger = Logger.getLogger(Server.class);
   
	@RequestMapping("/abc")
	String test() {
		logger.info("hello world");
		return "Hello world!";
	}
	
	@RequestMapping(value="/bcd", method={RequestMethod.GET, RequestMethod.POST})
	void textResponse(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
			logger.info("get a get request");
			String signature = request.getParameter("signature");
			if (!StringUtils.isEmpty(signature)) {
				String echoStr = request.getParameter("echostr");
				String nonce = request.getParameter("nonce");
				String timestamp = request.getParameter("timestamp");
				
				logger.info("before serverValidate echoStr=" + echoStr);
				if (serverValidate(nonce, timestamp, signature)) {
					try {
						response.getWriter().write(echoStr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
			logger.info("get a post request");
			try {
				dealWithMessage(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void dealWithMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String inputString = readInputStream(request.getInputStream());
		logger.info("inputString = " + inputString);

		String outputString = buildResponse(inputString);
		logger.info("outputString = " + outputString);
		response.getWriter().write(outputString);
	}
	
	private String buildResponse(String inputString) {
		XStream xStream = XStreamFactory.newXStream();
		xStream.autodetectAnnotations(true);
		xStream.alias("xml", InputMessage.class);
		InputMessage request = (InputMessage) xStream.fromXML(inputString);
		logger.info("inputMessage=" + request);
		
		if (MsgType.TEXT.equals(request.getMsgType())) {
			InputMessage response = new InputMessage();
			response.setToUserName(request.getFromUserName());
			response.setFromUserName(request.getToUserName());
			response.setCreateTime(System.currentTimeMillis());
			response.setMsgType(MsgType.TEXT);
			response.setContent(request.getContent() + ", 是这个意思吗?");
			
			return xStream.toXML(response);
		}
		
		return "";
	}
	
	private String readInputStream(ServletInputStream servletInputStream) {
		StringBuilder sBuilder = new StringBuilder();
		byte[] buffer = new byte[4096];
		int result = -1;
		do {
			try {
				result = servletInputStream.read(buffer);
				sBuilder.append(new String(buffer, 0, result, "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (result >= buffer.length);
		
		return sBuilder.toString();
	}
	
	/**
	 * 明文校验Server
	 */
	private boolean serverValidate(String nonce, String timestamp, String signature) {
		logger.info("serverValidate nonce=" + nonce + ", timestamp=" + timestamp + ", signature=" + signature);
		
		if (StringUtils.isEmpty(nonce) || StringUtils.isEmpty(timestamp)) {
			return false;
		}
				
		String sign = DigestUtils.sha1Hex(
				StringUtil.getDictSortStr(nonce, timestamp, Constant.TOKEN));
		logger.info("my sigin=" + sign);
		if (!StringUtils.isEmpty(sign) && sign.equalsIgnoreCase(signature)) {
			return true;
		}
		
		return false;
	}
		
	public static void main(String args[]) throws Exception {
		SpringApplication.run(Server.class, args);
	}
	
	@Override  
    public void customize(ConfigurableEmbeddedServletContainer container) {  
        container.setPort(80);  
    } 
}
