package instrucoes;

import java.util.List;

public interface IInstrucao {
	
	public final static String NOP_CODE = "00000000000000000000000000000000";
	
	public String getOpcodeArgs();

	public String getOpcode();
	
	public Integer getRegistradorEscrito();
	
	public List<Integer> getRegistradoresLidos();
	
	public Integer getDadoImediato();
	
	public String getNome();
	
	public String getALUOp();

	public boolean isBranch();

	public boolean isJump();

}
