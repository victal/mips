package memorias;

import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.List;

public class MemBuilder {
	
	public static MemoriaInstrucao buildMemInstruction(List<String> instrucoesBin) {
		List<Instrucao> instrucoes = new ArrayList<Instrucao>();
		for (String instrucao : instrucoesBin) {
			instrucoes.add(new Instrucao(instrucao));
		}
		return new MemoriaInstrucao(instrucoes);
	}


}
