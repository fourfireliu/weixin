package com.fourfire.weixin.factory;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fourfire.weixin.annotation.XStreamCDATA;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 构建适用于微信消息的XStream
 * 
 * @author liuyi
 * @date 2016-06-23
 */
public class XStreamFactory {
	public static XStream newXStream() {
		return new XStream(new XppDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean needCdata = false;
					Class<?> targetClass = null;
					Map<String, Field> fieldMap = new HashMap<String, Field>();
					
					@Override
					public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
						super.startNode(name, clazz);
						if (!name.equalsIgnoreCase("xml")) {
							needCdata = needCDATA(clazz, name);
						} else {
							if (targetClass == null) {
								targetClass = clazz;
								for (Field field : targetClass.getDeclaredFields()) {
									XStreamCDATA xStreamCDATA = field.getAnnotation(XStreamCDATA.class);
									if (xStreamCDATA != null) {
										XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);	
										if (xStreamAlias != null && 
												!StringUtils.isEmpty(xStreamAlias.value())) {
											fieldMap.put(xStreamAlias.value(), field);
										}
									}
								}
							}
						}
					}
					
					@Autowired
					protected void writeText(QuickWriter writer, String text) {
						if (needCdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
					
					public boolean needCDATA(Class<?> targetClass, String fieldAlias) {
						if (fieldMap.get(fieldAlias) != null) {
							return true;
						}
						
						return false;
					}
				};
			}
			
		});
	}
}
