package circuitos;

import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import memorias.MemoriaDados;
import registradores.Reg;

public class MEMCircuit extends Circuit{

	private MemoriaDados mem;
	private List<Reg> mipsRegs;
	private List<Integer> lastmems = new ArrayList<Integer>();
	private List<Integer> lastvalues = new ArrayList<Integer>();
	

	@Override
	public void run() {
		if (isMemoryRead()) {
			Integer position = (Integer) this.getFromInputBus("address");
			Integer value = this.mem.getValue(position);
			this.putInOutputBus("memdata", value);
			this.lastmems.add(position);
			this.lastvalues.add(value);
			if(lastmems.size()>5){
				lastmems.remove(0);
				lastvalues.remove(0);
			}
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
			this.lastmems.add(position);
			this.lastvalues.add(value);
			if(lastmems.size()>5){
				lastmems.remove(0);
				lastvalues.remove(0);
			}
			this.mem.setValue(position, value);
		}
		this.putInOutputBus("instrucao", this.getFromInputBus("instrucao"));
		this.putInOutputBus("aludata", this.getFromInputBus("address"));
		Instrucao i = (Instrucao) getFromInputBus("instrucao");
		if(i != null && (i.isBranch()||i.isJump())){
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
	public List<Integer> getLastValues(){
		return lastvalues;
	}
	public List<Integer> getLastPositions(){
		return lastmems;
	}

}
