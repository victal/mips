package circuitos;

import instrucoes.Instrucao;
import memorias.MemoriaInstrucao;

public class IFCircuit extends Circuit{

	private MemoriaInstrucao mem;
	private int pc;
	
	
	public IFCircuit() {
		this.pc = 0;
	}

	public void setMem(MemoriaInstrucao mem) {
		this.mem = mem;
	}

	public Instrucao fetch() {
		Instrucao instrucao = this.getCurrentInstruction();
		this.incrementPC();
		return instrucao;
	}


	public int getPC() {
		return this.pc;
	}

	public void incrementPC() {
		this.pc += 4;
		
	}

	public Instrucao getCurrentInstruction() {
		return this.mem.get(this.getPC());
	}

	public MemoriaInstrucao getDefaultMem() {
		return this.mem;
	}


	public void run() {
		Instrucao instrucao = this.fetch();
		//System.err.println("fetch "+instrucao.getOpcode());
		this.putInOutputBus("instrucao", instrucao);
		this.putInOutputBus("pc", this.getPC());
	}
	

}
