package circuitos;

import java.util.Set;

import mips.Controle;


public abstract class Circuit {
	
	private Bus outputBus = new Bus();
	private Bus inputBus = new Bus();
	private Controle control;
	private boolean bypass = false;
		
	public Object getFromOutputBus(String key) {
		return this.outputBus.get(key);
	}
	
	public Object getFromInputBus(String key) {
		return this.inputBus.get(key);
	}
	
	public void putInOutputBus(String key, Object value) {
		this.outputBus.put(key, value);
	}
	
	public void putInInputBus(String key, Object value) {
		this.inputBus.put(key, value);
	}
	
	public void setControl(Controle control) {
		this.control = control;
		
	}
	
	public Controle getControl() {
		return this.control;
		
	}

	public abstract void run();

	public Set<String> getOutputBusKeys() {
		return this.outputBus.keys();
	}

	public Bus getOutputBus() {
		return this.outputBus;
	}
	
	public void setBypass(boolean b){
		this.bypass=b;
	}
	public boolean getBypass(){
		return this.bypass;
	}
	
//	public Set<String> getInputBusKeys() {
//		return this.inputBus.keys();
//	}
	
}
