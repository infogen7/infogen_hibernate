package com.infogen.hibernate.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author larry
 * @version 创建时间 2017年9月26日 上午10:33:28
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EQ extends Operator {
	private static final long serialVersionUID = 757844916520032852L;

	public EQ(String key, Boolean is_number, String value) {
		super();
		this.type = OperatorTypes.EQ.name();
		this.key = key;
		this.value = value;
		this.is_number = is_number;
	}

	private String value = "";
	public String key = "";
	public Boolean is_number = false;

	public String to_filter() {
		if (key.isEmpty() || value.isEmpty()) {
			return " 1 = 1 ";
		}
		StringBuilder string_builder = new StringBuilder();
		string_builder.append(" ").append(key).append(" = ");
		if (!is_number) {
			string_builder.append("'");
		}
		string_builder.append(value);
		if (!is_number) {
			string_builder.append("'");
		}
		string_builder.append(" ");
		return string_builder.toString();
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
