package instrucoes;

public class InstrucaoSuperFormatI {

	private String instrucao;
	private String opcodeArgs;
	private String opcode;

	public InstrucaoSuperFormatI(String instrucao) {
		this.instrucao = instrucao;
		this.opcodeArgs = this.instrucao.substring(6);
		this.opcode = this.instrucao.substring(0, 6);
	}
	
	public String getOpcodeArgs() {
		return this.opcodeArgs;
	}

	public String getOpcode() {
		return this.opcode;
	}
	
	public String getImm() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len -16, len);
	}

	public String getRt() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len-16-5, len-16);
	}

	public String getRs() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len-16-5-5, len-16-5);
	}
	
	public Integer getDadoImediato() {
		return Integer.valueOf(this.getImm(), 2);
	}
	
	public boolean isBranch() {
		return false;  //override this on ble, bne, etc 
	}
	
	public boolean isJump() {
		return false; //override this on jmṕ
	}


}
