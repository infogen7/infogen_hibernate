package com.infogen.hibernate.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author larry
 * @version 创建时间 2017年9月26日 上午11:18:48
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class GT extends Operator {
	private static final long serialVersionUID = -6016383539821071462L;

	public GT(String key, Number value) {
		super();
		this.key = key;
		this.value = value;
	}

	private Number value = 0d;
	public String key = "";

	public String to_filter() {
		if (key == null || key.trim().isEmpty()) {
			return " 1 = 1 ";
		}
		StringBuilder string_builder = new StringBuilder();
		return string_builder.append(" ").append(key).append(" > ").append(value).append(" ").toString();
	}

	/**
	 * @return the value
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Number value) {
		this.value = value;
	}

}