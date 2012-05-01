package mips;

import instrucoes.Instrucao;

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

public class MIPS {
	private int numcycles=0;
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
	private boolean paused = false;

	
	public void runStep(){
		
		this.WBCircuit.run();
		Integer pcsrc = (Integer) this.WBCircuit.getControl().get("PCSrc");
		if(pcsrc!=null){
			System.err.println("pcsrc="+pcsrc);
			if(pcsrc == 1){
				Integer newpc = (Integer) this.WBCircuit.getFromOutputBus("newpc");
				this.IFCircuit.setNewPC(newpc);
			}
			this.IFCircuit.liftJumpblock();
		}
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
		System.err.println(IFCircuit.getPC()+" "+
	//					  IFCircuit.getCurrentInstruction().getNome()+" "+ //por algum motivo essa linha altera o resultado
						 ((Instrucao)IDCircuit.getFromInputBus("instrucao")).getNome()+" "+
						 ((Instrucao)EXCircuit.getFromInputBus("instrucao")).getNome()+" "+
						 ((Instrucao)MEMCircuit.getFromInputBus("instrucao")).getNome()+" "+
						 ((Instrucao)WBCircuit.getFromInputBus("instrucao")).getNome());
		this.numcycles++;		
	}		
	
	public void run(){
		while(!isFinished()&&!isStopped()){
			this.runStep();
		}
			
	}
	public Integer getCycles(){
		return numcycles;
	}
	public boolean isStopped() {
		return paused;
	}
	public void stop(){
		this.paused=true;
	}
	public void resume(){
		this.paused=false;
	}
	public boolean isFinished(){
		boolean finished = false;
		finished = this.IFCircuit.getPC()>=this.memInstruction.getNumberOfInstructions()*4;
		//Nothing running on WB
		boolean nowb = finished && ((Instrucao)WBCircuit.getFromInputBus("instrucao")).getNome().equals("nop");
		//Branch not followed on WB
		boolean bnf = ((Instrucao)WBCircuit.getFromInputBus("instrucao")).isBranch() &&
					  (Integer) this.WBCircuit.getControl().get("PCSrc")==0;
		finished = finished && (bnf||nowb);
					//Nothing running on MEM
		finished = finished && ((Instrucao)MEMCircuit.getFromInputBus("instrucao")).getNome().equals("nop");
		//Nothing running on EX
		finished = finished && ((Instrucao)EXCircuit.getFromInputBus("instrucao")).getNome().equals("nop");
		//Nothing running on ID
		finished = finished && ((Instrucao)IDCircuit.getFromInputBus("instrucao")).getNome().equals("nop");
		return finished;
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
	public void setBypass(boolean b){
		this.WBCircuit.setBypass(b);
		this.MEMCircuit.setBypass(b);
		this.EXCircuit.setBypass(b);
		this.IDCircuit.setBypass(b);
		this.IFCircuit.setBypass(b);
	}

	public List<Reg> getRegs() {
		return regs;
	}

}


