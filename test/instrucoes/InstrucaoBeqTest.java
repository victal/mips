package instrucoes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class InstrucaoBeqTest {
	
	private InstrucaoBeq instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		//00010100110010100000000000001100
		this.opcode = "000101";  //beq
		this.opcodeArgs = "00110010100000000000001100";  //ble R6, R10, 12
		this.instr  = new InstrucaoBeq(opcode + opcodeArgs);
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
		assertEquals("beq", this.instr.getNome());
	}
	
	@Test
	public void deveEntenderOsArgumentosDoOpcode() {
		assertEquals("0000000000001100", this.instr.getImm());
		assertEquals("01010", this.instr.getRt());
		assertEquals("00110", this.instr.getRs());
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos().get(0), Integer.valueOf(6));  //R6 foi lido
		assertEquals(this.instr.getRegistradoresLidos().get(1), Integer.valueOf(10));  //R10 foi lido
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), null); //nenhum registrador foi escrito nesta operacao
	}
	
	@Test
	public void deveSaberQualODadoImediato() {
		assertEquals(Integer.valueOf(12), this.instr.getDadoImediato());
	}
	
	@Test
	public void deveSaberIndicarQueEhUmBranch() {
		assertTrue(this.instr.isBranch());
	}


}
