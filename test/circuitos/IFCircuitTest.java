package circuitos;

import static org.junit.Assert.*;
import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaInstrucao;

import org.junit.Before;
import org.junit.Test;



public class IFCircuitTest {

	private MemoriaInstrucao mem;
	private IFCircuit circuit;


	@Before
	public void setUp() {
		this.mem = criaMemoria();
		this.circuit = new IFCircuit();
		this.circuit.setMem(this.mem);
	}
	
	@Test
	public void deveConhecerUmaMemoriaPadraoParaConsultar() {
		this.circuit.setMem(this.mem);
		assertEquals(this.mem, this.circuit.getDefaultMem());
	}
	
	@Test
	public void deveSaberIncrementarOPCCorretamente() {
		assertEquals(0, this.circuit.getPC());
		this.circuit.incrementPC();
		assertEquals(4, this.circuit.getPC());
		this.circuit.incrementPC();
		assertEquals(8, this.circuit.getPC());
	}
	
	@Test
	public void deveSaberRecuperarInstrucoesDaMemoria() {
		Instrucao instrucao = null;
		int edge_pc = this.mem.getNumberOfInstructions() * this.mem.getInstructionSize();
		for (int i = 0; i < edge_pc; i = i + 4) {
			instrucao = this.circuit.fetch();
			assertEquals(this.mem.get(i), instrucao);	
		}
	}
	
	@Test
	public void deveColocarNOPNoCircuitoSeAcarabamAsInstrucoesDaMem() {
		//fetch everything
		int edge_pc = this.mem.getNumberOfInstructions() * this.mem.getInstructionSize();
		for (int i = 0; i < edge_pc; i = i + 4) {
			this.circuit.fetch();
		}

		//one more fetch
		assertEquals(new Instrucao(Instrucao.NOP_CODE), this.circuit.fetch());
	}
	
	@Test
	public void deveSaberQualAInstrucaoAtual() {
		assertEquals(this.mem.get(0), this.circuit.getCurrentInstruction());
		assertEquals(this.mem.get(0), this.circuit.getCurrentInstruction());
		this.circuit.fetch();
		assertEquals(this.mem.get(4), this.circuit.getCurrentInstruction());
		assertEquals(this.mem.get(4), this.circuit.getCurrentInstruction());
	}
	
			
	@Test
	public void deveSaberColocarAInstrucaoRecuperadaNoBarramentoDeSaida() {
		this.circuit.run();
		assertEquals(this.mem.get(0), (Instrucao) this.circuit.getFromOutputBus("instrucao"));
	}
	
	@Test
	public void deveSaberColocarOPCIncrementadoNoBarramentoDeSaida() {
		assertEquals(0, this.circuit.getPC());
		circuit.run();
		assertEquals(Integer.valueOf(4), this.circuit.getFromOutputBus("pc"));	
	}

	
	
	/*
	 * Apenas uma fabrica de memoria para ajudar nos testes
	 */
	private MemoriaInstrucao criaMemoria() {
		List<Instrucao> lista = new ArrayList<Instrucao>();
		lista.add(new Instrucao("00100000000010100000000001100100"));
		lista.add(new Instrucao("10101100000000000000000000011000"));
		lista.add(new Instrucao("10101100000000000000000000011100"));
		lista.add(new Instrucao("10001100000001100000000000011100"));
		lista.add(new Instrucao("00000000110001100011100000011000"));
		lista.add(new Instrucao("10001100000000010000000000011000"));
		lista.add(new Instrucao("00000000001001110100100000100000"));
		lista.add(new Instrucao("10101100000010010000000000011000"));
		lista.add(new Instrucao("00100000110001100000000000000001"));
		lista.add(new Instrucao("10101100000001100000000000011100"));
		lista.add(new Instrucao("00011100110010100000000000001100"));
		return new MemoriaInstrucao(lista);
	}
	

}
