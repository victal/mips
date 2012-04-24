package instrucoes;

import java.util.List;

public interface IInstrucao {
	
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
