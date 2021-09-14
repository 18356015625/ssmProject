package com.liyan.junit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestUnit {

	@Test
	public void test1() {
		String parentids = "1,2,3,4,5,6";
		List<String> lists = new ArrayList<>();
		String[] ids = parentids.split(",");//[1,2,3,4,5,6]
		for (String id: ids) {
			String d = "'" + id + "'";
			lists.add(d);
		}
		//将数组或集合以某拼接符拼接到一起形成新的字符串
		String str = StringUtils.join(lists,",");
		System.out.println(str);
	}
	
	//@Test
	public void test2() {
		BigDecimal a = new BigDecimal("1.50");
		BigDecimal b = new BigDecimal("0.10");
		System.out.println(a.add(b));
	}

}
