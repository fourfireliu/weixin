package com.fourfire.weixin.entity.xml.converter;

import java.lang.reflect.Method;

import com.thoughtworks.xstream.converters.enums.EnumSingleValueConverter;

/**
 * 微信XML-枚举类型转换类 
 * 需枚举类自定义name属性, 方法getName(), getEnumByName(String str)
 * 
 * @author liuyi
 * @date 2016-06-23
 */
public class WeixinEnumToStringConverter extends EnumSingleValueConverter {

	private Class<? extends Enum<?>> enumType;
	
	private static final String ENUM_NAME_METHOD = "getName";
	private static final String ENMU_VALUE_METHOD = "getEnumByName";
	
	public WeixinEnumToStringConverter(Class<? extends Enum<?>> type) {
		super(type);
		this.enumType = type;
	}
	
	@Override
	public Object fromString(String str) {
		Method method = getEnumValueMethod();
		if (method == null) {
			return null;
		}

		try {
			return method.invoke(null, str);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public String toString(Object obj) {
		Method method = getEnumNameMethod();
		if (method != null) {
			try {
				return (String) method.invoke(obj, (Object[]) null);
			} catch (Exception e) {
				return null;
			}
		}
		
		return null;
	}
	
	private Method getEnumNameMethod() {
		try {
			return enumType.getMethod(ENUM_NAME_METHOD, (Class<?>[]) null);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Method getEnumValueMethod() {
		try {
			return enumType.getMethod(ENMU_VALUE_METHOD, String.class);
		} catch (Exception e) {
			return null;
		}
	}

}
