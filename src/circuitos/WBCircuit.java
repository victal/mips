package circuitos;

import instrucoes.Instrucao;

import java.util.List;

import mips.Controle;

import registradores.Reg;

public class WBCircuit extends Circuit{

	private List<Reg> regs;

	@Override
	public void run() {
		Integer data = this.getData();
		this.putInOutputBus("data", data);
		if (!this.getControl().get("RegWr").equals(0)) {
			Integer index = (Integer) this.getControl().get("RegWrID");
			Reg reg = this.regs.get(index);
			reg.setValue(data);	
			reg.unsetDirty();
		}
		Instrucao i = (Instrucao) getFromInputBus("instrucao");
		if(i != null && (i.isBranch()||i.isJump())){
			this.putInOutputBus("newpc", this.getFromInputBus("newpc"));
		}
		

	}

	public Integer getData() {
		if (!this.getControl().get("MemToReg").equals(0)) {
			return (Integer) this.getFromInputBus("memdata");
		}
		return (Integer) this.getFromInputBus("aludata");
	}

	
	public void setMipsRegisters(List<Reg> regs) {
		this.regs = regs;
	}

}
