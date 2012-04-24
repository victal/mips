package instrucoes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class InstrucaoAddTest {
	
	private InstrucaoAdd instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		this.opcode = "000000";  //add
		this.opcodeArgs = "00001001110100100000100000";  //add R9, R1, R7
		this.instr  = new InstrucaoAdd(opcode + opcodeArgs);
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
		assertEquals("add", this.instr.getNome());
	}
	
	@Test
	public void deveEntenderOsArgumentosDoOpcode() {
		assertEquals("00001", this.instr.getRs());
		assertEquals("00111", this.instr.getRt());
		assertEquals("01001", this.instr.getRd());
		assertEquals("100000", this.instr.getFunct());
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos().get(0), Integer.valueOf(1));  //R1 e R7 foram lidos
		assertEquals(this.instr.getRegistradoresLidos().get(1), Integer.valueOf(7));  //R1 e R7 foram lidos
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), Integer.valueOf(9));
	}
	
	@Test
	public void deveSaberQueNaoTemDadoImediato() {
		assertNull(null, this.instr.getDadoImediato());
	}

}
