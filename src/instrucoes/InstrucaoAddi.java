package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoAddi extends InstrucaoSuperFormatI  implements IInstrucao {

	public InstrucaoAddi(String instrucao) {
		super(instrucao);
	}
	

	public String getNome() {		
		return "addi";
	}

	public List<Integer> getRegistradoresLidos() {
		List<Integer> registradoresLidos = new ArrayList<Integer>();
		registradoresLidos.add(Integer.valueOf(this.getRs(), 2));	//Rs eh registrador lido 
		return registradoresLidos;
	}

	public Integer getRegistradorEscrito() {
		return Integer.valueOf(this.getRt(), 2); // Rt eh registrador escrito
	}

	public String getALUOp() {
		return "add";
	}


}
