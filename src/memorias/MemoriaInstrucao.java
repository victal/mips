package memorias;

import instrucoes.Instrucao;

import java.util.List;

public class MemoriaInstrucao {
	
	private final Integer INSTRUCTION_SIZE = 4;  //4 bytes per instruction

	private List<Instrucao> memoria;

	public MemoriaInstrucao(List<Instrucao> lista) {
		this.memoria = lista;
	}

	public Instrucao get(int pc) {
		if (pc % INSTRUCTION_SIZE != 0)
			throw new InvalidMemoryAddressException();
		
		Instrucao instrucao;
		try {
			instrucao = this.memoria.get(pc / INSTRUCTION_SIZE);
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
	
	public Integer getInstructionSize() {
		return this.INSTRUCTION_SIZE;
	}

}
