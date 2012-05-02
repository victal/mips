package main;

import javax.swing.SwingWorker;

import mips.MIPS;

public class MIPSRunner extends SwingWorker<Object, Void> {

	private MIPSGui gui;
	private MIPS mips;
	private boolean running = false;
	private boolean toRun=false;
	public MIPSRunner(MIPSGui mipsGui, MIPS mips) {
		this.mips=mips;
		this.gui=mipsGui;
	}

	public void setRun(boolean b){
		toRun=b;
	}
	
	public void runMIPSStep(){
		gui.updateInfos();
		mips.runStep();	
	}
	
	public void runMIPS(){
		running=true;
		while(!mips.isStopped()&&!mips.isFinished()){
			runMIPSStep();
		}
		running=false;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		while(!isCancelled()){
	         if(toRun&&!isRunning()){
	        	 this.runMIPS();
	         }
	      }
		return null;
	}
	public boolean isRunning(){
		return running && !mips.isStopped();
	}
	public void resetMips(MIPS mips){
		this.mips=mips;
		this.running=false;
	}

}
