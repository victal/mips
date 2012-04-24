package instrucoes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class InstrucaoLwTest {
	private InstrucaoLw instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		//100011 00000 00110 0000000000011100
		this.opcode = "100011";  //lw
		this.opcodeArgs = "00000001100000000000011100";  //lw R6,28(R0)
		this.instr  = new InstrucaoLw(opcode + opcodeArgs);
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
		assertEquals("lw", this.instr.getNome());
	}
	
	@Test
	public void deveEntenderOsArgumentosDoOpcode() {
		assertEquals("0000000000011100", this.instr.getImm());
		assertEquals("00110", this.instr.getRt());
		assertEquals("00000", this.instr.getRs());
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos().get(0), Integer.valueOf(0));  //R0 foi lido
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), Integer.valueOf(6)); //R6 foi escrito com mem[28+r0]
	}
	
	@Test
	public void deveSaberQualODadoImediato() {
		assertEquals(Integer.valueOf(28), this.instr.getDadoImediato());
	}

}
