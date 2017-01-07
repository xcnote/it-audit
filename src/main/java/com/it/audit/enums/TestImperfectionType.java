package com.it.audit.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试缺陷类
 * @author wangx
 *
 */
public enum TestImperfectionType {

	ordinary("一般缺陷", "存在可接受的合理差异", "发现一般异常情况"),
	important("重要缺陷", "存在严重差异", "发现严重异常情况"),
	serious("重大缺陷", null, null),
	normal("不适用", "不适用", "不适用");
	
	private String gctext;
	private String actext;
	private String datext;
	
	private TestImperfectionType(String gctext, String actext, String datext) {
		this.gctext = gctext;
		this.actext = actext;
		this.datext = datext;
	}
	
	public static List<TestImperfectionType> getAllType(ObjectTaskType task){
		TestImperfectionType[] types = TestImperfectionType.values();
		List<TestImperfectionType> result = new ArrayList<TestImperfectionType>();
		for (TestImperfectionType type : types) {
			String text = null;
			switch (task) {
			case GC:
				text = type.getGctext();
				break;
			case AC:
				text = type.getActext();
				break;
			case DA:
				text = type.getDatext();
				break;
			default:
				break;
			}
			if(text != null){
				result.add(type);
			}
		}
		return result;
	}

	public String getGctext() {
		return gctext;
	}

	public void setGctext(String gctext) {
		this.gctext = gctext;
	}

	public String getActext() {
		return actext;
	}

	public void setActext(String actext) {
		this.actext = actext;
	}

	public String getDatext() {
		return datext;
	}

	public void setDatext(String datext) {
		this.datext = datext;
	}
}
