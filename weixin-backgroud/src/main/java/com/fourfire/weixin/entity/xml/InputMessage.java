package com.fourfire.weixin.entity.xml;

import com.fourfire.weixin.annotation.XStreamCDATA;
import com.fourfire.weixin.entity.xml.converter.WeixinEnumToStringConverter;
import com.fourfire.weixin.enums.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class InputMessage {
	@XStreamCDATA
	@XStreamAlias("ToUserName")
	private String toUserName;   //开发者微信号
	
	@XStreamAlias("FromUserName")
	@XStreamCDATA
	private String fromUserName;   //发送方账号(一个OpenID)
	
	@XStreamAlias("CreateTime")
	private Long createTime;   //消息创建时间 （整型）
	
	@XStreamAlias("MsgType")
	@XStreamConverter(value=WeixinEnumToStringConverter.class)
	@XStreamCDATA
	private MsgType msgType;   //消息类型 文本(text) 图片(image) 语音(voice) 视频(video) 小视频(shortvideo) 地理位置(location) 链接(link)
	
	@XStreamAlias("MsgId")
	private Long msgId;   //消息id，64位整型
	
	@Override
	public String toString() {
		return "InputMessage [toUserName=" + toUserName + ", fromUserName="
				+ fromUserName + ", createTime=" + createTime + ", msgType="
				+ msgType + ", msgId=" + msgId + ", content=" + content
				+ ", mediaId=" + mediaId + ", picUrl=" + picUrl + ", format="
				+ format + ", recognition=" + recognition + ", thumbMediaId="
				+ thumbMediaId + ", locationX=" + locationX + ", locationY="
				+ locationY + ", scale=" + scale + ", label=" + label
				+ ", title=" + title + ", description=" + description
				+ ", url=" + url + "]";
	}
	/**
	 * 文本消息
	 */
	@XStreamCDATA
	@XStreamAlias("Content")
	private String content;  //文本消息内容
	
	/*************************/
	@XStreamAlias("MediaId")
	private String mediaId;   //图片语音视频小视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	/**
	 * 图片消息
	 */
	@XStreamAlias("PicUrl")
	private String picUrl;   //图片链接
 	
	/**
	 * 语音消息
	 */
	@XStreamAlias("Format")
	private String format;   //语音格式，如amr，speex等
	@XStreamAlias("Recognition")
	private String recognition;
	
	/**
	 * 视频消息, 小视频消息
	 */
	@XStreamAlias("ThumbMediaId")
	private String thumbMediaId;   //视频消息缩略图的媒体id
	
	/**
	 * 地理位置消息
	 */
	@XStreamAlias("Location_X")
	private String locationX;   //地理位置维度
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public MsgType getMsgType() {
		return msgType;
	}
	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	public String getLocationX() {
		return locationX;
	}
	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}
	public String getLocationY() {
		return locationY;
	}
	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}
	public Integer getScale() {
		return scale;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@XStreamAlias("Location_Y")
	private String locationY;   //地理位置经度
	@XStreamAlias("Scale")
	private Integer scale;   //地图缩放大小
	@XStreamAlias("Label")
	private String label;   //地理位置信息

	/**
	 * 链接消息
	 */
	@XStreamAlias("Title")
	private String title;   //消息标题
	@XStreamAlias("Description")
	private String description;   //消息描述
	@XStreamAlias("Url")
	private String url;   //消息链接
	
//	public static void main(String args[]) {
//		//String xml = "&lt;xml&gt;&lt;/xml&gt;";
//		String xml1 = "<xml><ToUserName><![CDATA[gh_7bfaa5130411]]></ToUserName><FromUserName><![CDATA[olF6nv3lKvo45u4Sb0TqeFBiKCzk]]></FromUserName><CreateTime>1466668029215</CreateTime><MsgType><![CDATA[text]]></MsgType><MsgId>6299285369308992993</MsgId><content><![CDATA[134]]></content></xml>";
//		
//		
//		XStream xstrem = XStreamFactory.newXStream();
//		xstrem.alias("xml", InputMessage.class);
//		xstrem.autodetectAnnotations(true);
//		System.out.println((InputMessage) xstrem.fromXML(xml1));
//		
////		InputMessage input = new InputMessage();
////		input.setToUserName("gh_7bfaa5130411");
////		input.setFromUserName("olF6nv3lKvo45u4Sb0TqeFBiKCzk");
////		input.setCreateTime(System.currentTimeMillis());
////		input.setMsgType(MsgType.TEXT);
////		input.setContent("134");
////		input.setMsgId(6299285369308992993L);
////		System.out.println(xstrem.toXML(input));
//	}
}
