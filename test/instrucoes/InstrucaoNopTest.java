package instrucoes;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class InstrucaoNopTest {
	
	private InstrucaoNop instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		// 00000000000000000000000000000000
		this.opcode = "000000";  //nop
		this.opcodeArgs = "00000000000000000000000000";  //nop
		this.instr  = new InstrucaoNop(opcode + opcodeArgs);
	}
	
	@Test
	public void deveSaberOsArgumentosDoOpcode() {
		assertEquals(this.opcodeArgs, this.instr.getOpcodeArgs());
	}
	
	@Test
	public void deveSaberOOpcode() {
		assertEquals(this.opcode, this.instr.getOpcode());
	}
	
	@Test
	public void deveSaberQualONomeDaInstrucao() {
		assertEquals("nop", this.instr.getNome());
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertTrue(this.instr.getRegistradoresLidos().isEmpty());
	}

	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), null);
	}
	

	@Test
	public void deveSaberQueNaoTemDadoImediato() {
		assertNull(null, this.instr.getDadoImediato());
	}

}
