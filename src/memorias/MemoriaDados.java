package memorias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaDados {

	private List<Integer> data;
	private static final Integer MAX_POSITION = 32 * 1024;

	public MemoriaDados() {
		this.data = buildEmptyDataMem();
	}
	public MemoriaDados(File f){
		this.data = buildXY(f);
	}
	private List<Integer> buildXY(File f) {
		List<Integer> mem = buildEmptyDataMem();
		int x0=1000/4,y0=5000/4;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = "";
			while((line=reader.readLine())!=null){
				String[] xy = line.split(" ");
				if(xy.length!=2) return mem;
				Integer x=Integer.parseInt(xy[0]);
				Integer y=Integer.parseInt(xy[1]);
				mem.add(x0,x);
				mem.add(y0,y);
				x0++;
				y0++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mem;
	}
	private List<Integer> buildEmptyDataMem() {
		List<Integer> dataMem = new ArrayList<Integer>();
		for (int i = 0; i < MAX_POSITION; ++i)
			dataMem.add(0);
		return dataMem;
	}

	/*
	 * memoria de acesso aleatorio.
	 * Precisa informar a posicao
	 */
	public void setValue(Integer position, Integer value) {
		if (position < 0 || position >= MAX_POSITION||position%4!=0)
			throw new InvalidMemoryAddressException();
		this.data.add(position/4, value);
		
	}

	public Integer getValue(Integer position) {
		if (position < 0 || position >= MAX_POSITION||position%4!=0)
			throw new InvalidMemoryAddressException();
		return this.data.get(position/4);
	}

}
