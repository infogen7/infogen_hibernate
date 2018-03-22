package com.infogen.hibernate.sql;

import com.infogen.core.json.JSONArray;
import com.infogen.core.json.JSONObject;

/**
 * @author larry
 * @version 创建时间 2018年3月1日 下午4:59:50
 */
public class SqlGenerate {

	public Operator generate(JSONObject jsonobject) {
		Operator operator = new Empty();

		String type = jsonobject.getAsString("type", "");
		String key = jsonobject.getAsString("key", "");

		if (type.equals("AND")) {
			AND and = new AND();
			JSONArray array = jsonobject.getAsJSONArray("value", new JSONArray());
			for (int i = 0; i < array.size(); i++) {
				JSONObject item = array.getAsJSONObject(i, new JSONObject());
				if (item != null) {
					and.add(generate(item));
				}
			}
			operator = and;
		} else if (type.equals("OR")) {
			OR or = new OR();
			JSONArray array = jsonobject.getAsJSONArray("value", new JSONArray());
			for (int i = 0; i < array.size(); i++) {
				JSONObject item = array.getAsJSONObject(i, new JSONObject());
				if (item != null) {
					or.add(generate(item));
				}
			}
			operator = or;
		} else if (type.equals("JSON")) {// Json
			JSONArray array = jsonobject.getAsJSONArray("value", new JSONArray());
			operator = new Json(key, array);
		} else if (type.equals("JSONARRAY")) {
			JSONArray array = jsonobject.getAsJSONArray("value", new JSONArray());
			operator = new JsonArray(key, array);
		} else if (type.equals("IN")) {
			JSONArray array = jsonobject.getAsJSONArray("value", new JSONArray());
			operator = new IN(key, array);
		} else if (type.equals("NOTIN")) {
			JSONArray array = jsonobject.getAsJSONArray("value", new JSONArray());
			operator = new NOTIN(key, array);
		} else if (type.equals("EQ")) {
			operator = new EQ(key, jsonobject.getAsString("value", null));
		} else if (type.equals("NE")) {
			operator = new NE(key, jsonobject.getAsString("value", null));
		} else if (type.equals("GT")) {
			operator = new GT(key, jsonobject.getAsDouble("value", null));
		} else if (type.equals("LT")) {
			operator = new LT(key, jsonobject.getAsDouble("value", null));
		} else if (type.equals("GE")) {
			operator = new GE(key, jsonobject.getAsDouble("value", null));
		} else if (type.equals("LE")) {
			operator = new LE(key, jsonobject.getAsDouble("value", null));
		} else if (type.equals("BETWEEN")) {
			operator = new Between(key, jsonobject.getAsDouble("min", null), jsonobject.getAsDouble("max", null));
		}
		return operator;
	}
}
