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
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Mips binary files";
			}
			
			@Override
			public boolean accept(File arg0) {
				return arg0.getName().matches(".*[.]mips");
			}
		});
		int retval = fc.showOpenDialog(null);
		File f = new File("/home/guilherme/teste.mips");
		if(retval==JFileChooser.APPROVE_OPTION){
			f = fc.getSelectedFile();
		}
		List<String> instrucoes = lerInstrucoes(f);
		MemoriaInstrucao memInstruction = MemBuilder.buildMemInstruction(instrucoes);
		File datamem = new File("resources/datamem.dat");
		MemoriaDados memData = new MemoriaDados(datamem);
		final MIPS mips = MIPSBuilder.build(memInstruction, memData);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MIPSGui(mips);
			}
		});
		//mips.run();
		
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

	
	
}
