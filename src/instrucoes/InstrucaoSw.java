package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoSw extends InstrucaoSuperFormatI  implements IInstrucao {

	public InstrucaoSw(String instrucao) {
		super(instrucao);
	}

	public String getNome() {
		return "sw";
	}
	
	public List<Integer> getRegistradoresLidos() {
		List<Integer> registradoresLidos = new ArrayList<Integer>();
		registradoresLidos.add(Integer.valueOf(this.getRs(), 2));	//Rs eh registrador lido 
		registradoresLidos.add(Integer.valueOf(this.getRt(), 2));	//Rt eh registrador lido
		return registradoresLidos;
	}
	
	public Integer getRegistradorEscrito() {
		return null;
	}
	
	public String getALUOp() {
		return "add";
	}

}
