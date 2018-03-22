package com.infogen.hibernate.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author larry
 * @email larrylv@outlook.com
 * @version 创建时间 2017年9月26日 上午10:33:28
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class NE extends Operator {
	private static final long serialVersionUID = 2020502685563180302L;

	public NE(String key, Boolean is_number, String value) {
		super();
		this.type = OperatorTypes.NE.name();
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
		string_builder.append(" ").append(key).append(" != ");
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
