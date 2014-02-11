package pk.ssi.dedlain.utils;

import java.util.HashMap;
import java.util.Map;

public class Data {

	private Map<String, Object> map = new HashMap<String, Object>();

	public Data set(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public Object get(String key) {
		return map.get(key);
	}

	public Map<String, Object> getMap() {
		return map;
	}
}
