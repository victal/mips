package circuitos;

import java.util.HashMap;
import java.util.Set;

public class Bus {

	private HashMap<String, Object> map;
	
	public Bus() {
		this.map = new HashMap<String, Object>();
	}

	public Object get(String key) {
		return this.map.get(key);
	}

	public void put(String key, Object value) {
		this.map.put(key, value);
		
	}

	public Set<String> keys() {
		return this.map.keySet();
	}
	

}
