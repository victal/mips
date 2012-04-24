package memorias;

import java.util.ArrayList;
import java.util.List;

public class MemoriaDados {

	private List<Integer> data;
	private static final Integer MAX_POSITION = 32 * 1024;

	public MemoriaDados() {
		this.data = buildEmptyDataMem();
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
		if (position < 0 || position >= MAX_POSITION)
			throw new InvalidMemoryAddressException();
		this.data.add(position, value);
		
	}

	public Integer getValue(Integer position) {
		if (position < 0 || position >= MAX_POSITION)
			throw new InvalidMemoryAddressException();
		return this.data.get(position);
	}

}
