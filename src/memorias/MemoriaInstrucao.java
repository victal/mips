package memorias;

import instrucoes.Instrucao;

import java.util.List;

public class MemoriaInstrucao {

	private List<Instrucao> memoria;

	public MemoriaInstrucao(List<Instrucao> lista) {
		this.memoria = lista;
	}

	public Instrucao get(int pc) {
		if (pc % 4 != 0)
			throw new InvalidMemoryAddressException();
		
		Instrucao instrucao;
		try {
			instrucao = this.memoria.get(pc / 4);
		} catch (IndexOutOfBoundsException e) {
			instrucao = null;
		}
		
		return instrucao;
	}

	public List<Instrucao> getAll() {
		return this.memoria;
	}

	public int getNumberOfInstructions() {
		return this.memoria.size();
	}

}
