package com.fourfire.weixin.enums;

/**
 * 消息类型
 * 
 * @author liuyi
 * @date 2016-06-23
 */
public enum MsgType {
	TEXT("text"), 
	IMAGE("image"), 
	VOICE("voice"), 
	VIDEO("video"), 
	SHORT_VIDEO("shortvideo"), 
	LOCATION("location"), 
	LINK("link");
	
	private String name;
	
	private MsgType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static MsgType getEnumByName(String name) {
		for (MsgType msgType : MsgType.values()) {
			if (msgType.getName().equalsIgnoreCase(name)) {
				return msgType;
			}
		}
		
		return TEXT;
	}
}