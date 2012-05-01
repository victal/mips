package circuitos;

import instrucoes.IInstrucao;
import instrucoes.Instrucao;
import memorias.MemoriaInstrucao;

public class IFCircuit extends Circuit{

	private MemoriaInstrucao mem;
	private int pc;
	private boolean jumpblock;
	
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
		Instrucao i =  this.mem.get(this.getPC());
		if(i==null) i= new Instrucao(IInstrucao.NOP_CODE);
		return i;
	}

	public MemoriaInstrucao getDefaultMem() {
		return this.mem;
	}


	public void run() {
		Instrucao instrucao;
		if(jumpblock) 
			instrucao = new Instrucao(IInstrucao.NOP_CODE);
		else
			instrucao = this.fetch();
		if(instrucao.isBranch()|| instrucao.isJump()){
			jumpblock=true;
		}
		//System.err.println("fetch "+instrucao.getOpcode());
		this.putInOutputBus("instrucao", instrucao);
		this.putInOutputBus("pc", this.getPC());
	}

	public void liftJumpblock() {
		this.jumpblock=false;
	}

	public void setNewPC(Integer newPC) {
		this.pc = newPC;
		this.jumpblock = false;
		
	}
	

}
