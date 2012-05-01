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


	public String getInstrucaoMIPS() {
		String i;
		if(Integer.valueOf(getRs())!=0)
			i = getNome().toUpperCase()+" R"+Integer.valueOf(getRt(),2)+",R"+Integer.valueOf(getRs(),2)+","+getDadoImediato();
		else i = "LI R"+Integer.valueOf(getRt(),2)+","+getDadoImediato();
		return i;
	}

	
}
