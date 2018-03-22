package com.infogen.hibernate.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author larry
 * @email larrylv@outlook.com
 * @version 创建时间 2017年9月26日 上午11:18:48
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Between extends Operator {
	private static final long serialVersionUID = -4232996750996709020L;

	public Between(String key, Number min, Number max) {
		super();
		this.type = OperatorTypes.BETWEEN.name();
		this.key = key;
		this.min = min;
		this.max = max;
		this.is_number = true;
	}

	private Number min = 0d;
	private Number max = 0d;
	public String key = "";
	public Boolean is_number = false;

	public String to_filter() {
		if (key.isEmpty()) {
			return " 1 = 1 ";
		}
		StringBuilder string_builder = new StringBuilder();
		return string_builder.append(" ").append(key).append(" BETWEEN ").append(min).append(" AND ").append(max).toString();
	}

	/**
	 * @return the min
	 */
	public Number getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(Number min) {
		this.min = min;
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
	 * @return the max
	 */
	public Number getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(Number max) {
		this.max = max;
	}

}
