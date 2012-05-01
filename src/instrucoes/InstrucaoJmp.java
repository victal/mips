package instrucoes;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoJmp extends InstrucaoSuperFormatJ  implements IInstrucao {

	public InstrucaoJmp(String instrucao) {
		super(instrucao);
	}

	public String getNome() {
		return "jmp";
	}
	
	public List<Integer> getRegistradoresLidos() {
		return new ArrayList<Integer>();
	}

	public Integer getRegistradorEscrito() {
		return null;
	}
	
	public boolean isBranch() {
		return false;
	}
	
	public String getALUOp() {
		return "nop";
	}

	@Override
	public Integer getDadoImediato() {
		return super.getAddress();
	}

	public String getInstrucaoMIPS(){
		return getNome().toUpperCase()+" "+getDadoImediato();
	}
	
	

}
