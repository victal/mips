package circuitos;

import instrucoes.Instrucao;

import java.util.List;

import memorias.MemoriaDados;
import registradores.Reg;

public class MEMCircuit extends Circuit{

	private MemoriaDados mem;
	private List<Reg> mipsRegs;

	@Override
	public void run() {
		if (isMemoryRead()) {
			Integer position = (Integer) this.getFromInputBus("address");
			Integer value = this.mem.getValue(position);
			this.putInOutputBus("memdata", value);
			if(this.getBypass()){
				Instrucao i = (Instrucao)this.getFromInputBus("instrucao");
				Integer re = i.getRegistradorEscrito();
				Reg reg = this.mipsRegs.get(re);
				reg.setValue(value);	
				reg.unsetDirty();
			}
		}
		
		if( (isMemoryWrite())) {
			Integer position = (Integer) this.getFromInputBus("address");
			Integer value = (Integer) this.getFromInputBus("writedata");
			this.mem.setValue(position, value);
		}
		this.putInOutputBus("instrucao", this.getFromInputBus("instrucao"));
		this.putInOutputBus("aludata", this.getFromInputBus("address"));
		Instrucao i = (Instrucao) getFromInputBus("instrucao");
		if(i.isBranch()||i.isJump()){
			this.putInOutputBus("newpc", this.getFromInputBus("newpc"));
		}
	}

	private boolean isMemoryWrite() {
		return !this.getControl().get("MemWr").equals(0);
	}

	private boolean isMemoryRead() {
		return !this.getControl().get("MemRead").equals(0);
	}

	public void setMem(MemoriaDados mem) {
		this.mem = mem;
	}
	
	
	public Object getFromInputBus(String key) {
		Object value = super.getFromInputBus(key);
		if (value != null)
			return value;
		
		if (key.equals("address"))
			value = super.getFromInputBus("result"); //pode ter vindo com este nome
		if (key.equals("writedata"))
			value = super.getFromInputBus("rt"); //pode ter vindo com este nome
		
		return value;
	}

	public void setRegs(List<Reg> regs) {
		this.mipsRegs=regs;
	}

}
