package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoBne extends InstrucaoSuperFormatI  implements IInstrucao {

	public InstrucaoBne(String instrucao) {
		super(instrucao);
	}

	public String getNome() {
		return "bne";
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

	@Override
	public String getInstrucaoMIPS() {
		String i = getNome().toUpperCase()+" R"+Integer.valueOf(getRs(),2)+",R"+Integer.valueOf(getRt(),2)+","+getDadoImediato();
		return i;
	}
	

}
