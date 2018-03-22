package com.infogen.hibernate.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.infogen.core.json.Jackson;

/**
 * @author larry
 * @email larrylv@outlook.com
 * @version 创建时间 2017年9月26日 上午10:33:28
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Json extends Operator {
	private static final long serialVersionUID = -7495371176994686065L;

	public Json(String key, List<Object> items) {
		super();
		this.type = OperatorTypes.JSON.name();
		this.key = key;
		for (Object string : items) {
			this.items.put(string.toString(), 1);
		}
	}

	private Map<String, Integer> items = new HashMap<>();
	public String key = "";
	public Boolean is_number = false;

	public void add(String item) {
		this.items.put(item, 1);
	}

	public String to_filter() {
		// consumer_preference @> '{"健身":1}'
		// consumer_preference ? '健身'
		// abroad ?| array['德国', '英国']
		// jsonb_exists_any(abroad,array['巴西'])
		if (key.isEmpty() || items.isEmpty()) {
			return " 1 = 1 ";
		}
		StringBuilder string_builder = new StringBuilder();
		return string_builder.append(" jsonb_exists_any(").append(key).append(",array").append(Jackson.toJson(items.keySet()).replaceAll("\"", "'")).append(") ").toString();

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
	 * @return the items
	 */
	public Map<String, Integer> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Map<String, Integer> items) {
		this.items = items;
	}

}
