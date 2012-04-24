package mips;

import java.util.HashMap;
import java.util.Set;

public class Controle {
	
private HashMap<String, Object> map;
	
	public Controle() {
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
