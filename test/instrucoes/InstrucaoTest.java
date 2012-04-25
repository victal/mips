package instrucoes;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;

public class InstrucaoTest {
	
	private String opcode;
	private String opcodeArgs;
	private Instrucao instr;

	@Test
	public void deveSaberInterpretarOCodigoDeAddParaInstrucaoCorreta() {
		this.opcode = "000000";  //add
		this.opcodeArgs = "00001001110100100000100000";  //add R9, R1, R7
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("add", this.instr.getNome());
	}
		
	@Test
	public void deveSaberInterpretarOCodigoDeMulParaInstrucaoCorreta() {
		this.opcode = "000000";  //add
		this.opcodeArgs = "00110001100011100000011000";  //mul R7,R6,R6
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("mul", this.instr.getNome());
	}
	
	@Test
	public void deveSaberInterpretarOCodigoDeAddiParaInstrucaoCorreta() {
		this.opcode = "001000";  //addi
		this.opcodeArgs = "00000010100000000001100100";  //addi R10, R0, 100
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("addi", this.instr.getNome());
	}
	
	@Test
	public void deveSaberInterpretarOCodigoDeBleParaInstrucaoCorreta() {
		this.opcode = "000111";  //ble
		this.opcodeArgs = "00110010100000000000001100";  //ble R6, R10, 12
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("ble", this.instr.getNome());
	}
	
	@Test
	public void deveSaberInterpretarOCodigoDeLwParaInstrucaoCorreta() {
		this.opcode = "100011";  //lw
		this.opcodeArgs = "00000001100000000000011100";  //lw R6,28(R0)
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("lw", this.instr.getNome());
	}
	
	@Test
	public void deveSaberInterpretarOCodigoDeSwParaInstrucaoCorreta() {
		this.opcode = "101011";  //sw
		this.opcodeArgs = "00000010010000000000011000";  //sw R9,24(R0)
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("sw", this.instr.getNome());
	}
	
	@Test
	public void deveSaberInterpretarOCodigoDeNopParaInstrucaoCorreta() {
		this.opcode = "000000";  //nop
		this.opcodeArgs = "00000000000000000000000000";  //nop
		this.instr  = new Instrucao(opcode + opcodeArgs);
		
		assertEquals("nop", this.instr.getNome());
	}
	
	@Test
	public void deveConhecerAConstanteDeCodigoParaInstrucaoNOP() {
		assertEquals("00000000000000000000000000000000", Instrucao.NOP_CODE);
	}

}
