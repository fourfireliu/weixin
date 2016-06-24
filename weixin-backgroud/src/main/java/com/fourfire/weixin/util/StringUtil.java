package com.fourfire.weixin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

public class StringUtil {
	/**
	 * 按字典排序并合并
	 */
	public static String getDictSortStr(String... params) {
		List<String> paramList = new ArrayList<String>();
		for (String param : params) {
			if (!StringUtils.isEmpty(param)) {
				paramList.add(param);
			}
		}
		Collections.sort(paramList);
		
		StringBuilder sBuilder = new StringBuilder();
		for (String param : paramList) {
			sBuilder.append(param);
		}
		
		return sBuilder.toString();
	}
}
