package instrucoes;

public class InstrucaoSuperFormatJ {

	private String instrucao;
	private String opcodeArgs;
	private String opcode;

	public InstrucaoSuperFormatJ(String instrucao) {
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
	
	public String getAddressString() {
		int len = this.getOpcodeArgs().length();
		String add = this.getOpcodeArgs().substring(len -26, len);
		return add;
	}

	public Integer getAddress() {
		return Integer.valueOf(this.getAddressString(), 2);
	}
	
	public boolean isBranch() {
		return false;  //override this on ble, bne, etc 
	}
	
	public boolean isJump() {
		return true;
	}


}
