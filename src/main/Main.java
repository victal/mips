package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import memorias.MemBuilder;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import mips.MIPS;
import mips.MIPSBuilder;

public class Main {


	public static void main(String[] args) {
		File f = new File("/home/guilherme/test.mips");
		List<String> instrucoes = lerInstrucoes(f); 
		MemoriaInstrucao memInstruction = MemBuilder.buildMemInstruction(instrucoes);
		MemoriaDados memData = new MemoriaDados();
		MIPS mips = MIPSBuilder.build(memInstruction, memData);
		mips.run();
		
	}

	
	
	private static List<String> lerInstrucoes(File f) {
		List<String> res = new ArrayList<String>();
		try {
			BufferedReader buffer = new  BufferedReader(new FileReader(f));
			String line;
			while((line=buffer.readLine())!=null){
				res.add(line.split(";")[0].trim());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
		

	}

	
	
}
