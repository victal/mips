package instrucoes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class InstrucaoSwTest {

	private InstrucaoSw instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		//101011 00000 01001 0000000000011000
		this.opcode = "101011";  //sw
		this.opcodeArgs = "00000010010000000000011000";  //sw R9,24(R0)
		this.instr  = new InstrucaoSw(opcode + opcodeArgs);
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
		assertEquals("sw", this.instr.getNome());
	}

	@Test
	public void deveEntenderOsArgumentosDoOpcode() {
		assertEquals("0000000000011000", this.instr.getImm());
		assertEquals("01001", this.instr.getRt());
		assertEquals("00000", this.instr.getRs());
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos().get(0), Integer.valueOf(0));  //R0 foi lido
		assertEquals(this.instr.getRegistradoresLidos().get(1), Integer.valueOf(9));  //R9 foi lido
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), null ); //nenhum registrador foi escrito nesta operacao
	}

	@Test
	public void deveSaberQualODadoImediato() {
		assertEquals(Integer.valueOf(24), this.instr.getDadoImediato());
	}
	
}
