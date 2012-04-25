package instrucoes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class Instrucao implements IInstrucao {

	private String instrucao;
	private String opcodeArgs;
	private String opcode;
	private IInstrucao instance;

	public Instrucao(String instrucao) {
		this.instrucao = instrucao;
		
		this.opcodeArgs = this.instrucao.substring(6);
		this.opcode = this.instrucao.substring(0, 6);
		
		HashMap<String, String> instrucaoMapaStringXClasse  = constroiMapaInstrucoes();
		
		String key = getKey(opcode, opcodeArgs);
		String className = instrucaoMapaStringXClasse.get(key);
					
		this.instance = constroiObjetoInstrucao(className);
	}
	
	
	
	
	private IInstrucao constroiObjetoInstrucao(String className) {

		Object instrucaoObjeto = null;
		try {
			Class klass = Class.forName("instrucoes." + className);
			Constructor ctor = klass.getConstructor(String.class);
			instrucaoObjeto = ctor.newInstance(new Object[] {instrucao});
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return (IInstrucao) instrucaoObjeto;
	}


	private String getKey(String opcode, String opcodeArgs) {
		String key = opcode;
		if (opcode.equals("000000")) {
			key += getFunct(); 
		}
		return key;
	}
	
	private String getFunct() {
		int len = this.opcodeArgs.length();
		String funct = opcodeArgs.substring(len-6, len);
		return funct;
	}
	
	private HashMap<String, String> constroiMapaInstrucoes() {
		HashMap<String, String> instrucaoMapaStringXClasse  = new HashMap<String, String>();
		
		instrucaoMapaStringXClasse.put("000000" + "100000", "InstrucaoAdd");
		instrucaoMapaStringXClasse.put("000000" + "011000", "InstrucaoMul");
		instrucaoMapaStringXClasse.put("001000", "InstrucaoAddi");
		instrucaoMapaStringXClasse.put("000111", "InstrucaoBle");
		instrucaoMapaStringXClasse.put("100011", "InstrucaoLw");
		instrucaoMapaStringXClasse.put("101011", "InstrucaoSw");
		instrucaoMapaStringXClasse.put("000000" + "000000", "InstrucaoNop");
		
		return instrucaoMapaStringXClasse;
	}


	@Override
	public String getOpcodeArgs() {
		return this.instance.getOpcodeArgs();
	}


	@Override
	public String getOpcode() {
		return this.instance.getOpcode();
	}


	@Override
	public Integer getRegistradorEscrito() {
		return this.instance.getRegistradorEscrito();
	}


	@Override
	public List<Integer> getRegistradoresLidos() {
		return this.instance.getRegistradoresLidos();
	}


	@Override
	public String getNome() {
		return this.instance.getNome();
	}


	@Override
	public Integer getDadoImediato() {
		return this.instance.getDadoImediato();
	}


	@Override
	public boolean isBranch() {
		return this.instance.isBranch();
	}


	@Override
	public String getALUOp() {
		return this.instance.getALUOp();
	}


	public boolean isJump() {
		return this.instance.isJump();
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.instrucao == null) ? 0 : this.instrucao.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrucao other = (Instrucao) obj;
		if (this.instrucao == null) {
			if (other.instrucao != null)
				return false;
		} else if (!this.instrucao.equals(other.instrucao))
			return false;
		return true;
	}



}
