package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoBle extends InstrucaoSuperFormatI  implements IInstrucao {

	public InstrucaoBle(String instrucao) {
		super(instrucao);
	}

	public String getNome() {
		return "ble";
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
	
	public boolean isBranch() {
		return true;
	}
	
	public String getALUOp() {
		return "sub";
	}

	
	

}
