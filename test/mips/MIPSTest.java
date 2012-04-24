package mips;

import static org.junit.Assert.*;
import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.List;

import latches.Latch;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;

import org.junit.Before;
import org.junit.Test;

import circuitos.EXCircuit;
import circuitos.IDCircuit;
import circuitos.IDCircuitTest;
import circuitos.IFCircuit;
import circuitos.MEMCircuit;
import circuitos.WBCircuit;

import registradores.Reg;


public class MIPSTest {
	
	private MIPS mips;
	private MemoriaInstrucao memInstruction;
	private MemoriaDados memData;
	private List<Reg> regs;
	

	@Before
	public void setUp() {
		this.mips = new MIPS();
		this.memInstruction = criaMemoriaInstrucao();
		this.memData = criaMemoriaDados();
		this.regs = criaRegs();
		
		this.mips.setMemData(this.memData);
		this.mips.setMemInstruction(this.memInstruction);
		this.mips.setRegs(this.regs);
		
		buildCircuits();
		buildLatches();
				
	}

		
	/*
	 * Varias fabricas.
	 * 
	 */
	
	private void buildLatches() {
		buildLatchIFID();
		buildLatchIDEX();
		buildLatchEXMEM();
		buildLatchMEMWB();
	}

	private void buildLatchMEMWB() {
		Latch latch = new Latch();
		latch.connectInput(this.mips.getMEMCircuit());
		latch.connectOutput(this.mips.getWBCircuit());
		this.mips.setLatchMEMWB(latch);
	}

	private void buildLatchEXMEM() {
		Latch latch = new Latch();
		latch.connectInput(this.mips.getEXCircuit());
		latch.connectOutput(this.mips.getMEMCircuit());
		this.mips.setLatchEXMEM(latch);
	}

	private void buildLatchIDEX() {
		Latch latch = new Latch();
		latch.connectInput(this.mips.getIDCircuit());
		latch.connectOutput(this.mips.getEXCircuit());
		this.mips.setLatchIDEX(latch);
	}

	private void buildLatchIFID() {
		Latch latch = new Latch();
		latch.connectInput(this.mips.getIFCircuit());
		latch.connectOutput(this.mips.getIDCircuit());
		this.mips.setLatchIFID(latch);
	}



	private void buildCircuits() {
		buildIFCircuit();
		buildIDCircuit();
		buildEXCircuit();
		buildMEMCircuit();
		buildWBCircuit();
	}

	private void buildWBCircuit() {
		WBCircuit circuit = new WBCircuit();
		circuit.setControl(new Controle());
		circuit.setMipsRegisters(this.regs);
		this.mips.setWBCircuit(circuit);
	}

	private void buildMEMCircuit() {
		MEMCircuit circuit = new MEMCircuit();
		circuit.setMem(this.memData);		
		circuit.setControl(new Controle());
		this.mips.setMEMCircuit(circuit);
	}

	private void buildEXCircuit() {
		EXCircuit circuit = new EXCircuit();
		circuit.setControl(new Controle());
		this.mips.setEXCircuit(circuit);
	}

	private void buildIDCircuit() {
		IDCircuit circuit = new IDCircuit();
		circuit.setMipsRegisters(this.regs);
		circuit.setControl(new Controle());
		this.mips.setIDCircuit(circuit);
	}

	private void buildIFCircuit() {
		IFCircuit circuit = new IFCircuit();
		circuit.setMem(this.memInstruction);
		circuit.setControl(new Controle());
		this.mips.setIFCircuit(circuit);		
	}

	
	
	private MemoriaInstrucao criaMemoriaInstrucao() {
		List<Instrucao> lista = new ArrayList<Instrucao>();
		lista.add(new Instrucao("00100000000010100000000001100100")); //addi R10, R0, 100

		return new MemoriaInstrucao(lista);
	}

	private MemoriaDados criaMemoriaDados() {
		return new MemoriaDados();
	}

	private List<Reg> criaRegs() {
		List<Reg> regs = new ArrayList<Reg>();
		
		for (int i = 0; i < 32; i++) {
			regs.add(new Reg(i));
		}
		
		regs.get(0).setValue(0);
		
		return regs;
	}

}
