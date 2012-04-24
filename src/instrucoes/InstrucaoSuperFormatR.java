package instrucoes;

public class InstrucaoSuperFormatR {

	private String instrucao;
	private String opcodeArgs;
	private String opcode;

	public InstrucaoSuperFormatR(String instrucao) {
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

	
	public String getRs() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len-16-5-5, len-16-5);
	}
	
	public String getRt() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len-16-5, len-16);
	}

	public String getRd() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len-16, len-16+5);
	}
	
	public String getFunct() {
		int len = this.getOpcodeArgs().length();
		return this.getOpcodeArgs().substring(len-6, len);
	}
	
	public Integer getDadoImediato() {
		return null;
	}
	
	public boolean isBranch() {
		return false;  //there is no R type operation which is a Branch
	}
	
	public boolean isJump() {
		return false;
	}

}
