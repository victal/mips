package circuitos;


import static org.junit.Assert.*;



import memorias.MemoriaDados;
import mips.ALU;
import mips.Controle;

import org.junit.Before;
import org.junit.Test;





public class MEMCircuitTest {
	
	private MEMCircuit circuit;
	private Controle controle;
	private MemoriaDados mem;

	@Before
	public void setUp() {
		this.circuit = new MEMCircuit();
		this.mem = criaMem();
		this.circuit.setMem(mem);
	}
	
	

	@Test
	public void deveSaberRecuperarDadoDaMemoriaEColocarNoBusDeSaida() {
		//lw R6,28(R0)
		this.controle = criaControleLw();
		this.circuit.setControl(this.controle);
		this.circuit.putInInputBus("address", 28);
		this.mem.setValue(28, 100);
		
		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.circuit.getFromOutputBus("memdata"));
		
	}
	
	@Test
	public void deveSaberEscreverDadoEmMemoria() {
		//sw R9,24(R0)
		this.controle = criaControleSw();
		this.circuit.setControl(this.controle);
		this.circuit.putInInputBus("address", 24);
		this.circuit.putInInputBus("writedata", 100);
		
		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.mem.getValue(24));
		assertNull(this.circuit.getFromOutputBus("memdata"));
	}
	
	
	
	@Test
	public void deveSaberIgnorarInstrucoesQueNaoMexemComMemoria() {
		this.controle = criaControleAdd();
		this.circuit.setControl(this.controle);
		
		this.circuit.run();
		
		assertNull(this.circuit.getFromOutputBus("memdata"));
	}
	
	@Test
	public void deveSemprePassarOValorDoEnderecoAdianteParaOBusDeSaida() {
		this.controle = criaControleAdd();
		this.circuit.setControl(this.controle);
		
		this.circuit.putInInputBus("address", 24);
		
		this.circuit.run();
		
		assertEquals(Integer.valueOf(24), this.circuit.getFromOutputBus("aludata"));
	}
	
	@Test
	public void deveTentarProcurarAddressPeloPseudonimoDeResult () {
		this.controle = criaControleAdd();
		this.circuit.setControl(this.controle);
		
		this.circuit.putInInputBus("result", 24);  //a ula pode ter mandado com este nome
		
		this.circuit.run();
		
		assertEquals(Integer.valueOf(24), this.circuit.getFromOutputBus("aludata"));
	}
	
	@Test
	public void deveTentarProcurarWriteDataPeloPseudonimoDeRt() {
		this.controle = criaControleSw();
		this.circuit.setControl(this.controle);
		this.circuit.putInInputBus("address", 24);
		this.circuit.putInInputBus("rt", 100);
		
		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.mem.getValue(24));
		assertNull(this.circuit.getFromOutputBus("memdata"));

	}
	
	

	
	/*
	 * fabricas
	 */
	private Controle criaControleLw() {
		//lw R6,28(R0)
		Controle controle = new Controle();
		controle.put("ALUOp", ALU.ADD_CODE);  //vai ser um add
		controle.put("ALUSrc", 1); //vai usar dado immediato na ULA
		controle.put("RegWr", 1);  // vai escrever em registrador
		controle.put("RegWrID", 6);  // vai escrever em R10
		controle.put("MemToReg", 1);  //nao vai ser uma escrita de memoria para registrador 
		controle.put("MemWr", 0);  //nao vai escrever na memoria
		controle.put("MemRead", 1);  //vai ler da memoria
		controle.put("Branch", 0);  //nao eh branch
		controle.put("Jump", 0);  //nao eh jump
		return controle;
	}
	
	private Controle criaControleSw() {
		//sw R9,24(R0)
		Controle controle = new Controle();
		controle.put("ALUOp", ALU.ADD_CODE);  //vai ser um add
		controle.put("ALUSrc", 1); //vai usar dado immediato na ULA
		controle.put("RegWr", 0);
		controle.put("MemToReg", 0);  //nao vai ser uma escrita de memoria para registrador 
		controle.put("MemWr", 1);  //vai escrever na memoria
		controle.put("MemRead", 0);  //nao vai ler da memoria
		controle.put("Branch", 0);  //nao eh branch
		controle.put("Jump", 0);  //nao eh jump
		return controle;
	}
	
	private Controle criaControleAdd() {
		//add R10, R0, R1
		Controle controle = new Controle();
		controle.put("ALUOp", ALU.ADD_CODE);  //vai ser um add
		controle.put("ALUSrc", 0); //nao vai usar dado immediato na ULA
		controle.put("RegWr", 1);  // vai escrever em registrador
		controle.put("RegWrID", 10);  // vai escrever em R10
		controle.put("MemToReg", 0);  //nao vai ser uma escrita de memoria para registrador 
		controle.put("MemWr", 0);  //nao vai escrever na memoria
		controle.put("MemRead", 0);  //nao vai ler da memoria
		controle.put("Branch", 0);  //nao eh branch
		controle.put("Jump", 0);  //nao eh jump
		return controle;
	}
	
	private MemoriaDados criaMem() {
		return new MemoriaDados();
	}

}
