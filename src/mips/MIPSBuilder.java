package mips;

import instrucoes.IInstrucao;
import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.List;

import latches.Latch;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import registradores.Reg;
import circuitos.EXCircuit;
import circuitos.IDCircuit;
import circuitos.IFCircuit;
import circuitos.MEMCircuit;
import circuitos.WBCircuit;

public class MIPSBuilder {
	
	public static MIPS build(MemoriaInstrucao memInstruction, MemoriaDados memData) {
		MIPS mips = new MIPS();
		
		List<Reg> regs = buildRegs();
		
		mips.setMemData(memData);
		mips.setMemInstruction(memInstruction);
		mips.setRegs(regs);
	
		buildCircuits(mips, memData, memInstruction, regs);
		buildLatches(mips);
				
		return mips;
	}
	
	private static void buildLatches(MIPS mips) {
		buildLatchIFID(mips);
		buildLatchIDEX(mips);
		buildLatchEXMEM(mips);
		buildLatchMEMWB(mips);
	}

	private static void buildLatchMEMWB(MIPS mips) {
		Latch latch = new Latch();
		latch.connectInput(mips.getMEMCircuit());
		latch.connectOutput(mips.getWBCircuit());
		mips.setLatchMEMWB(latch);
	}

	private static void buildLatchEXMEM(MIPS mips) {
		Latch latch = new Latch();
		latch.connectInput(mips.getEXCircuit());
		latch.connectOutput(mips.getMEMCircuit());
		mips.setLatchEXMEM(latch);
	}

	private static void buildLatchIDEX(MIPS mips) {
		Latch latch = new Latch();
		latch.connectInput(mips.getIDCircuit());
		latch.connectOutput(mips.getEXCircuit());
		mips.setLatchIDEX(latch);
	}

	private static void buildLatchIFID(MIPS mips) {
		Latch latch = new Latch();
		latch.connectInput(mips.getIFCircuit());
		latch.connectOutput(mips.getIDCircuit());
		mips.setLatchIFID(latch);
	}



	private static void buildCircuits(MIPS mips, MemoriaDados memData, MemoriaInstrucao memInstruction, List<Reg> regs) {
		buildIFCircuit(mips, memInstruction, new Controle());
		buildIDCircuit(mips, regs, new Controle());
		buildEXCircuit(mips, regs, buildEXControl());
		buildMEMCircuit(mips, memData, regs, buildMEMControl());
		buildWBCircuit(mips, regs, buildWBControl());
	}

	

	private static Controle buildWBControl() {
		Controle c = new Controle();
		c.put("RegWr",0);
		c.put("MemToReg",0);
		return c;
	}
	
	private static Controle buildMEMControl() {
		Controle controle = buildWBControl();
		controle.put("MemRead",0);
		controle.put("MemWr",0);
		controle.put("RegWr",0);
		controle.put("MemToReg",0);
		return controle;
	}
	
	private static Controle buildEXControl(){
		Controle c = buildMEMControl();
		c.put("ALUOp",ALU.NOP_CODE);
		c.put("Branch",0);
		c.put("Jump",0);
		c.put("ALUSrc",0);//needed even if it's a Nop
		return c;
	}

	private static void buildWBCircuit(MIPS mips, List<Reg> regs, Controle controle) {
		WBCircuit circuit = new WBCircuit();
		circuit.setControl(controle);
		circuit.setMipsRegisters(regs);
		circuit.putInInputBus("instrucao", new Instrucao(IInstrucao.NOP_CODE));
		mips.setWBCircuit(circuit);
	}

	private static void buildMEMCircuit(MIPS mips, MemoriaDados memData, List<Reg> regs, Controle controle) {
		MEMCircuit circuit = new MEMCircuit();
		circuit.setMem(memData);		
		circuit.setControl(controle);
		circuit.setRegs(regs);
		circuit.putInInputBus("instrucao", new Instrucao(IInstrucao.NOP_CODE));
		mips.setMEMCircuit(circuit);
	}

	private static void buildEXCircuit(MIPS mips, List<Reg> regs, Controle controle) {
		EXCircuit circuit = new EXCircuit();
		circuit.setControl(controle);
		circuit.putInInputBus("instrucao", new Instrucao(IInstrucao.NOP_CODE));
		circuit.setRegs(regs);
		mips.setEXCircuit(circuit);
	}

	private static void buildIDCircuit(MIPS mips, List<Reg> regs, Controle controle) {
		IDCircuit circuit = new IDCircuit();
		circuit.setMipsRegisters(regs);
		circuit.setControl(controle);
		circuit.putInInputBus("instrucao", new Instrucao(IInstrucao.NOP_CODE));
		mips.setIDCircuit(circuit);
	}

	private static void buildIFCircuit(MIPS mips, MemoriaInstrucao memInstruction, Controle controle) {
		IFCircuit circuit = new IFCircuit();
		circuit.setMem(memInstruction);
		circuit.setControl(controle);
		mips.setIFCircuit(circuit);		
	}
	
	

	private static List<Reg> buildRegs() {
		List<Reg> regs = new ArrayList<Reg>();
		
		for (int i = 0; i < 32; i++) {
			regs.add(new Reg(i));
		}
		
		regs.get(0).setValue(0); //R0 always 0
		
		return regs;
	}


}
