package instrucoes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class InstrucaoMulTest {
	
	private InstrucaoMul instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		//000000 00110 00110 0011100000011000
		this.opcode = "000000";  //add
		this.opcodeArgs = "00110001100011100000011000";  //mul R7,R6,R6
		this.instr  = new InstrucaoMul(opcode + opcodeArgs);
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
		assertEquals("mul", this.instr.getNome());
	}

	@Test
	public void deveEntenderOsArgumentosDoOpcode() {
		assertEquals("00110", this.instr.getRs());
		assertEquals("00110", this.instr.getRt());
		assertEquals("00111", this.instr.getRd());
		assertEquals("011000", this.instr.getFunct());
	}

	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos().get(0), Integer.valueOf(6));  //R6 eh lido
		assertEquals(this.instr.getRegistradoresLidos().get(1), Integer.valueOf(6));  //R6 eh lido de novo
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), Integer.valueOf(7));
	}
	

	@Test
	public void deveSaberQueNaoTemDadoImediato() {
		assertNull(null, this.instr.getDadoImediato());
	}

}
