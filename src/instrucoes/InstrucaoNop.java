package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoNop extends InstrucaoSuperFormatR implements IInstrucao {

	public InstrucaoNop(String instrucao) {
		super(instrucao);
	}

	@Override
	public Integer getRegistradorEscrito() {
		return null;
	}

	@Override
	public List<Integer> getRegistradoresLidos() {
		return new ArrayList<Integer>();  //lista vazia
	}
	
	@Override
	public Integer getDadoImediato() {
		return null;
	}

	@Override
	public String getNome() {
		return "nop";
	}
	
	public String getALUOp() {
		return "nop";
	}

}
