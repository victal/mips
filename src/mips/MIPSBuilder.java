package mips;

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
		buildEXCircuit(mips, new Controle());
		buildMEMCircuit(mips, memData, new Controle());
		buildWBCircuit(mips, regs, new Controle());
	}

	private static void buildWBCircuit(MIPS mips, List<Reg> regs, Controle controle) {
		WBCircuit circuit = new WBCircuit();
		circuit.setControl(controle);
		circuit.setMipsRegisters(regs);
		mips.setWBCircuit(circuit);
	}

	private static void buildMEMCircuit(MIPS mips, MemoriaDados memData, Controle controle) {
		MEMCircuit circuit = new MEMCircuit();
		circuit.setMem(memData);		
		circuit.setControl(controle);
		mips.setMEMCircuit(circuit);
	}

	private static void buildEXCircuit(MIPS mips, Controle controle) {
		EXCircuit circuit = new EXCircuit();
		circuit.setControl(controle);
		mips.setEXCircuit(circuit);
	}

	private static void buildIDCircuit(MIPS mips, List<Reg> regs, Controle controle) {
		IDCircuit circuit = new IDCircuit();
		circuit.setMipsRegisters(regs);
		circuit.setControl(controle);
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
