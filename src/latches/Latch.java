package latches;

import mips.Controle;
import circuitos.Bus;
import circuitos.Circuit;

public class Latch {

	private Bus input;
	private Bus output;
	private Controle controleOutput;
	private Controle controleInput;
	private Circuit outputCircuit;

	
	public void sendWritePulse() {
		
		if (this.input != null)
			writeOutputBus();
		
		if(this.controleInput != null)
			writeOutputControl();
		
	}

	private void writeOutputBus() {
		this.output = new Bus();
		for (String key : this.input.keys()) {
			this.output.put(key, this.input.get(key));
			if (this.isOutputConnected())
				this.outputCircuit.putInInputBus(key, this.input.get(key));
		}

	}

	private void writeOutputControl() {
		this.controleOutput = new Controle();
		for (String key : this.controleInput.keys()) {
			this.controleOutput.put(key, this.controleInput.get(key));
			if (this.isOutputConnected())
				this.outputCircuit.getControl().put(key, this.controleInput.get(key));
		}		
	}


	public void setInput(Bus bus) {
		this.input = bus;
		
	}
	public void addInputControl(Controle controle) {
		this.controleInput = controle;
	}

	public Bus getOutput() {
		return this.output;
	}
	public Controle getOutputControl() {
		return this.controleOutput;
	}


	/*
	 * Conecta a saida de algum circuito na entrada do Latch
	 */
	public void connectInput(Circuit inputCircuit) {
		this.input = inputCircuit.getOutputBus();
		this.controleInput = inputCircuit.getControl();
	}
	
	public void connectOutput(Circuit outputCircuit) {
		this.outputCircuit = outputCircuit;
	}
	
	private boolean isOutputConnected() {
		return this.outputCircuit != null;
	}

}
