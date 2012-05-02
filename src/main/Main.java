package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import memorias.MemBuilder;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import mips.MIPS;
import mips.MIPSBuilder;

public class Main {


	public static void main(String[] args) {
		File f = new File("resources/teste.mips");
		MIPS mips = buildMIPS(f);
		new MIPSGui(mips);
//		mips.run();
	}

	
	
	public static List<String> lerInstrucoes(File f) {
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
	private static MIPS buildMIPS(File f){
		List<String> instrucoes = lerInstrucoes(f);
		MemoriaInstrucao memInstruction = MemBuilder.buildMemInstruction(instrucoes);
		File datamem = new File("resources/datamem.dat");
		MemoriaDados memData = new MemoriaDados(datamem);
		MIPS mips = MIPSBuilder.build(memInstruction, memData);
		return mips;
	}
	public static MIPS createMIPS(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Mips binary files";
			}
			public boolean accept(File arg0) {
				return arg0.getName().matches(".*[.]mips")||arg0.isDirectory();
			}
		});
		int retval = fc.showOpenDialog(null);
		File f;
		if(retval==JFileChooser.APPROVE_OPTION){
			f = fc.getSelectedFile();
		}
		else  f = new File("resources/teste.mips");
		
		return buildMIPS(f);
	}
	
	
}
