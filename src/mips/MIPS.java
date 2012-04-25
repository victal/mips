package mips;

import instrucoes.IInstrucao;
import instrucoes.Instrucao;

import java.util.List;

import circuitos.Circuit;
import circuitos.EXCircuit;
import circuitos.IDCircuit;
import circuitos.IFCircuit;
import circuitos.MEMCircuit;
import circuitos.WBCircuit;

import registradores.Reg;
import latches.Latch;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;

public class MIPS {

	private Controle control;
	private MemoriaDados memData;
	private MemoriaInstrucao memInstruction;
	private List<Reg> regs;
	private IFCircuit IFCircuit;
	private IDCircuit IDCircuit;
	private EXCircuit EXCircuit;
	private MEMCircuit MEMCircuit;
	private WBCircuit WBCircuit;
	private Latch latchMEMWB;
	private Latch latchEXMEM;
	private Latch latchIDEX;
	private Latch latchIFID;

	
	public void runStep(){
		
		this.WBCircuit.run();
		this.MEMCircuit.run();
		this.latchMEMWB.sendWritePulse();
		this.EXCircuit.run();
		this.latchEXMEM.sendWritePulse();
		if(!this.EXCircuit.isWorking()){
			this.IDCircuit.run();
			this.latchIDEX.sendWritePulse();
			if(!this.IDCircuit.isBlocked()){
				this.IFCircuit.run();
				this.latchIFID.sendWritePulse();
			}
		}
	}		
	
	public void run(){
		boolean finished = false;
		while(!finished){
			this.runStep();
			Instrucao nop = new Instrucao(IInstrucao.NOP_CODE);
			//Run out of instructions;
			finished = this.IFCircuit.getPC()>=this.memInstruction.getNumberOfInstructions()*4;
			//Nothing running on WB
			finished = this.latchMEMWB.getOutput().get("instrucao").equals(nop);
			//Nothing running on MEM
			finished = this.latchEXMEM.getOutput().get("instrucao").equals(nop);
			//Nothing running on EX
			finished = this.latchIDEX.getOutput().get("instrucao").equals(nop);
			//Nothing running on ID
			finished = this.latchIFID.getOutput().get("instrucao").equals(nop);
		}
	}
	public void setControl(Controle control) {
		this.control = control;
		
	}

	public void setMemData(MemoriaDados memData) {
		this.memData = memData;
		
	}

	public void setMemInstruction(MemoriaInstrucao memInstruction) {
		this.memInstruction = memInstruction;
		
	}

	public void setRegs(List<Reg> regs) {
		this.regs = regs;
		
	}

	public void setIFCircuit(IFCircuit circuit) {
		this.IFCircuit = circuit;
		
	}

	public void setIDCircuit(IDCircuit circuit) {
		this.IDCircuit = circuit;
		
	}

	public void setEXCircuit(EXCircuit circuit) {
		this.EXCircuit = circuit;
		
	}

	public void setMEMCircuit(MEMCircuit circuit) {
		this.MEMCircuit = circuit;
	}

	public void setWBCircuit(WBCircuit circuit) {
		this.WBCircuit = circuit;		
	}

	public Controle getControl() {
		return control;
	}

	public IFCircuit getIFCircuit() {
		return IFCircuit;
	}

	public IDCircuit getIDCircuit() {
		return IDCircuit;
	}

	public EXCircuit getEXCircuit() {
		return EXCircuit;
	}

	public MEMCircuit getMEMCircuit() {
		return MEMCircuit;
	}

	public WBCircuit getWBCircuit() {
		return WBCircuit;
	}

	public void setLatchMEMWB(Latch latch) {
		this.latchMEMWB = latch;
	}

	public void setLatchEXMEM(Latch latch) {
		this.latchEXMEM = latch;
	}

	public void setLatchIDEX(Latch latch) {
		this.latchIDEX = latch;
	}

	public void setLatchIFID(Latch latch) {
		this.latchIFID = latch;
	}

	

}


