package memorias;

import static org.junit.Assert.*;
import instrucoes.Instrucao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MemoriaTest {
	
	private List<Instrucao> lista; 
	private MemoriaInstrucao mem ;
	
	@Before
	public void setUp() {
		this.lista = criaListaInstrucoes();
		this.mem = new MemoriaInstrucao(lista);
	}
	
	@Test
	public void devePermitirSerInicializadaAPartirDeListaDeInstrucoes() {
		assertEquals(this.lista, this.mem.getAll());
	}
	
	@Test
	public void devePermitirRecuperarInstrucoesArmazenadas() {
		int pc = 0;
		for (Instrucao instrucao : this.lista) {
			assertEquals(instrucao, this.mem.get(pc));
			pc += 4;
		}
	}
	
	
	
	@Test public void deveAceitarEnderecamentosDeQuatroBytes() {
		int validPCs[] = {0, 4, 8, 12, 16, 20};
		for (int i : validPCs) {
			assertNotNull(this.mem.get(i));	
		}
	}
	
	@Test(expected=InvalidMemoryAddressException.class)
	public void deveRecusarEnderecamentosQueNaoSejamDeQuatroBytes() {
		int invalidPCs[] = {1, 3, 5, 7, 9, 11};
		
		for (int i : invalidPCs) {
			this.mem.get(i);	
		}
	}
	
	@Test
	public void deveRetornarNullQuandoAMemoriaAcabar() {
		int length = this.mem.getNumberOfInstructions();
		
		int pc = 0;
		for(int i = 0; i < length; ++i) {
			assertNotNull(this.mem.get(pc));
			pc += 4;
		}
		
		assertNull(this.mem.get(pc));
		assertNull(this.mem.get(pc + 4));
	}
		
	
	/*
	 * Apenas uma fabrica de lista de instrucoes para ajudar nos testes
	 */
	private List<Instrucao> criaListaInstrucoes() {
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
		return lista;
	}

}
