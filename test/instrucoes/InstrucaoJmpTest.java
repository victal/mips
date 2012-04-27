package instrucoes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import registradores.Reg;

public class InstrucaoJmpTest {
	
	private InstrucaoJmp instr;
	private String opcodeArgs;
	private String opcode;

	@Before
	public void setUp() {
		//00001000000000000000000000010100
		this.opcode = "000010";  //jmp
		this.opcodeArgs = "00000000000000000000010100";  //jmp 20
		this.instr  = new InstrucaoJmp(opcode + opcodeArgs);
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
		assertEquals("jmp", this.instr.getNome());
	}
	
	@Test
	public void deveEntenderOEndereco() {
		assertEquals("00000000000000000000010100", this.instr.getAddressString());
	}
	
	@Test
	public void deveSaberQuaisOsRegistradoresLidos() {
		assertEquals(this.instr.getRegistradoresLidos(),new ArrayList<Reg>());  //Nada Lido
	}
	
	@Test
	public void deveSaberQuaisORegistradorEscrito() {
		assertEquals(this.instr.getRegistradorEscrito(), null); //nenhum registrador foi escrito nesta operacao
	}
	
	@Test
	public void deveSaberQualODadoImediato() {
		assertEquals(Integer.valueOf(20), this.instr.getDadoImediato());
	}
	
	@Test
	public void deveSaberIndicarQueEhUmJump() {
		assertTrue(this.instr.isJump());
	}


}
