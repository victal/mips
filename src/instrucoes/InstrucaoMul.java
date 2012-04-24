package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoMul extends InstrucaoSuperFormatR implements IInstrucao {

	public InstrucaoMul(String instrucao) {
		super(instrucao);
	}

	public String getNome() {
		return "mul";
	}
	
	public List<Integer> getRegistradoresLidos() {
		List<Integer> registradoresLidos = new ArrayList<Integer>();
		registradoresLidos.add(Integer.valueOf(this.getRs(), 2));	//Rs eh registrador lido
		registradoresLidos.add(Integer.valueOf(this.getRt(), 2));	//Rt eh registrador lido
		return registradoresLidos;
	}
	
	public Integer getRegistradorEscrito() {
		return Integer.valueOf(this.getRd(), 2); // Rt eh registrador escrito
	}

	public String getALUOp() {
		return "mul";
	}
}
