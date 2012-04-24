package circuitos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mips.ALU;

import registradores.Reg;

import instrucoes.Instrucao;
import instrucoes.InstrucaoNop;

public class IDCircuit extends Circuit {

	private List<Reg> mipsRegs;
	private boolean blockState;


	@Override
	public void run() {
		updateBlockState();
		if (this.isBlocked()){
			//if blocked, coloca um NOP nos próximos estados;
			this.getControl().put("ALUOp", new InstrucaoNop("0000000000000000").getALUOp());
			return;
		}
		
		List<Integer> readRegsValues = this.getRegsValues();
		Integer imm = this.getImmediateData();

		if (readRegsValues.size() > 0)
			this.putInOutputBus("rs", readRegsValues.get(0));
		if (readRegsValues.size() > 1)
			this.putInOutputBus("rt", readRegsValues.get(1));
		if (imm != null)
			this.putInOutputBus("imm", imm);
		
		this.decodeControlSignals();
		
		this.putInOutputBus("pc", this.getFromInputBus("pc"));
				
		this.updateDirtyStateOfWrittenRegs();
	}



	public void setMipsRegisters(List<Reg> regs) {
		this.mipsRegs = regs;
	}

	public List<Reg> getRegsToReadFrom() {
		Instrucao instrucao = this.getInstrucao();
		
		List<Reg> regs = new ArrayList<Reg>();
		for (Integer regId : instrucao.getRegistradoresLidos()) {
			regs.add(this.mipsRegs.get(regId));
		}
		
		return regs;
	}

	public boolean isBlocked() {
		return this.blockState;
	}

	

		
	
	private Instrucao getInstrucao() {
		Instrucao instrucao = (Instrucao) this.getFromInputBus("instrucao");
		return instrucao;
	}
	
	private List<Integer> getRegsValues() {
		List<Integer> readValues = new ArrayList<Integer>();
		for (Reg r : this.getRegsToReadFrom()) {
				readValues.add(r.getValue());
		}
		return readValues;
	}
	
	private Integer getImmediateData() {
		return this.getInstrucao().getDadoImediato();
	}

	/*
	 * Se a instrucao pretende escrever em um registrador,
	 * nos marcamos este registrador como sujo desde esta fase.
	 * 
	 * A fase final (WB) limpara este bit de sujo,
	 * quando a escrita nele concluir de fato.
	 */
	private void updateDirtyStateOfWrittenRegs() {
		Instrucao instrucao = this.getInstrucao();
		Integer regId = instrucao.getRegistradorEscrito();
		if(regId != null) {
			Reg reg = this.mipsRegs.get(regId);
			reg.setDirty();
		}
	}

	
	private void updateBlockState() {
		this.blockState = false;
		if (this.shouldBlock())
			this.blockState = true;
	}

	private boolean shouldBlock() {
		List<Reg> regs = this.getRegsToReadFrom();
		boolean shouldBlock = false;
		for (Reg reg : regs) {
			shouldBlock = shouldBlock || reg.isDirty();
		}
		return shouldBlock;
	}




	private void decodeControlSignals() {
		Instrucao instrucao = this.getInstrucao();
		
		this.setControlSignal("Branch", instrucao.isBranch());
		this.setControlSignal("Jump", instrucao.isJump());

		this.setControlSignal("MemWr", instrucao.getNome().equals("sw"));
		this.setControlSignal("MemToReg", instrucao.getNome().equals("lw"));
		this.setControlSignal("MemRead", instrucao.getNome().equals("lw"));
		
		this.setControlSignal("RegWr", instrucao.getRegistradorEscrito() != null);
		this.getControl().put("RegWrID", instrucao.getRegistradorEscrito());

		this.setControlSignal("ALUSrc", this.getImmediateData() != null && !instrucao.isBranch());
		
		this.getControl().put("ALUOp", ALU.getControlCode(instrucao.getALUOp()));
		
		if (instrucao.isBranch())
			this.getControl().put("BranchType", instrucao.getNome());
		
	}
	
	private void setControlSignal(String signalKey, boolean condition) {
		if (condition)
			this.getControl().put(signalKey, 1);
		else
			this.getControl().put(signalKey, 0);
	}
	
	

	



	

}
