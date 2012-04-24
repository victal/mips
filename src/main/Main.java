package main;

import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.List;

import memorias.MemBuilder;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import mips.MIPS;
import mips.MIPSBuilder;

public class Main {


	public static void main(String[] args) {
		
		List<String> instrucoes = lerInstrucoes(); 
		MemoriaInstrucao memInstruction = MemBuilder.buildMemInstruction(instrucoes);

		MemoriaDados memData = new MemoriaDados();
		
		MIPS mips = MIPSBuilder.build(memInstruction, memData);

		
		
	}

	
	
	private static List<String> lerInstrucoes() {
		// TODO : LER ALGUM ARQUIVO E PREENCHER UMA LISTA DE STRINGS COM AS INSTRUCOES EM BINARIO
		return null;
	}

	
	
}
