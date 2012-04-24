package instrucoes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class InstrucaoAddiTest {
	
	private InstrucaoAddi instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		this.opcode = "001000";  //addi
		this.opcodeArgs = "00000010100000000001100100";  //addi R10, R0, 100
		this.instr  = new InstrucaoAddi(opcode + opcodeArgs);
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
		assertEquals("addi", this.instr.getNome());
	}

	@Test
	public void deveEntenderOsArgumentosDoOpcode() {
		assertEquals("0000000001100100", this.instr.getImm());
		assertEquals("01010", this.instr.getRt());
		assertEquals("00000", this.instr.getRs());
		
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos().get(0), Integer.valueOf(0));  //Apenas R0 foi lido
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), Integer.valueOf(10));
	}
	
	@Test
	public void deveSaberQualODadoImediato() {
		assertEquals(Integer.valueOf(100), this.instr.getDadoImediato());
	}

}
