package circuitos;

import instrucoes.Instrucao;
import mips.ALU;

public class EXCircuit extends Circuit {

	private boolean workingState = false;



	@Override
	public void run() {
		Integer op = (Integer) this.getControl().get("ALUOp");
			
		if (!ALU.isMul(op)) {

			if (this.isBranch())
				runBranch();
			else if(this.isJump())
				runJump();
			else
				runOrdinaryOp();

			copyPasteRT();
			this.putInOutputBus("instrucao", this.getFromInputBus("instrucao"));
			
		} else {
			runMulOpInTwoClocks();
			if(workingState){
				Instrucao nop = new Instrucao(Instrucao.NOP_CODE);
				this.putInOutputBus("instrucao", nop);
			}
			else
				this.putInOutputBus("instrucao", this.getFromInputBus("instrucao"));
		}
		
	}

	

	/*
	 * Pode ser necessario usar o valor de Rt separadamente depois (pense num SW)
	 */
	private void copyPasteRT() {
		Integer rtValue = (Integer) this.getFromInputBus("rt");
		if (rtValue != null)
			this.putInOutputBus("rt", rtValue);
	}



	private Integer runOrdinaryOp() {
		Integer aluResult = this.getALUResult();
		this.putInOutputBus("result", aluResult);
		this.putInOutputBus("address", aluResult);
		this.getControl().put("PCSrc", null);
		this.putInOutputBus("newpc",null);
		return aluResult;
	}

	private void runBranch() {
		Integer aluResult = runOrdinaryOp();
		this.putInOutputBus("newpc", this.getBranchPCResult());
		this.getControl().put("PCSrc", this.resolveBranchPCControlSignal(aluResult));
	}
	
	private void runJump() {
		Integer jumpaddr = (Integer) this.getFromInputBus("imm");
		this.putInOutputBus("newpc",jumpaddr);
		this.getControl().put("PCSrc",1);
	}



	private void runMulOpInTwoClocks() {
		if (!workingState) {
			workingState = true;
			this.putInOutputBus("result", null);
			return;
		} else {
			workingState = false;
			this.putInOutputBus("result", getALUResult());
			return;
		}
	}



	private Integer resolveBranchPCControlSignal(Integer aluResult) {
		if (this.isBranch()) {
			String branchType = (String) this.getControl().get("BranchType");
			
			boolean condition = false;
			
			if (branchType.equals("ble"))
				condition = (aluResult <= 0);
			if (branchType.equals("bne"))
				condition = (aluResult != 0);
			if (branchType.equals("beq"))
				condition = (aluResult == 0);
			
			return condition == true ? 1 : 0;
		}
		return null;
	}



	private boolean isBranch() {
		return this.getControl().get("Branch").equals(1);
	}

	private boolean isJump() {
		return this.getControl().get("Jump").equals(1);
	}

	private Integer getBranchPCResult() {
		if (!this.isBranch())
			return null;
		//return (Integer) this.getFromInputBus("pc") + 4 * (Integer) this.getFromInputBus("imm");
		return (Integer) this.getFromInputBus("imm")+4;
	}



	public Integer getALUInput1() {
		return (Integer) this.getFromInputBus("rs");
	}
	
	public Integer getALUInput2() {
		if ( this.getControl().get("ALUSrc").equals(1))
			return (Integer) this.getFromInputBus("imm");
		return (Integer) this.getFromInputBus("rt");
	}



	private Integer getALUResult() {
		Integer operand1 = this.getALUInput1();
		Integer operand2 = this.getALUInput2();
		Integer operation = (Integer) this.getControl().get("ALUOp");
		
		return ALU.run(operand1, operand2, operation);
	}



	public boolean isWorking() {
		return this.workingState;
	}

}

