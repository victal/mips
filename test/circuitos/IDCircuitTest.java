package circuitos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import instrucoes.Instrucao;

import mips.ALU;
import mips.Controle;

import org.junit.Before;
import org.junit.Test;

import registradores.Reg;

public class IDCircuitTest {
	
	private IDCircuit circuit;
	private Instrucao instrucao;
	private List<Reg> regs;
	private Controle controle;


	@Before
	public void setUp() {
		this.circuit = new IDCircuit();
		this.regs = criaRegs();
		this.controle = new Controle();
		this.circuit.setMipsRegisters(this.regs);
		this.circuit.setControl(controle);
	
		this.instrucao = new Instrucao("00100000000010100000000001100100"); //addi R10,R0,100
		this.circuit.putInInputBus("instrucao", this.instrucao);
	}
	

	@Test
	public void deveSaberReceberUmaInstrucaoNoBarramentoDeEntrada() {
		assertEquals(this.instrucao, this.circuit.getFromInputBus("instrucao"));
	}
	
	@Test
	public void deveSaberQuaisSaoOsRegistradoresParaRecuperarOValor() {
		assertEquals(this.regs.get(0), this.circuit.getRegsToReadFrom().get(0));
	}
	
	@Test
	public void deveSaberMarcarUmRegistradorComoSujoSeAOperacaoPretendeEscreverNele() {
		this.circuit.run();
		assertTrue(this.regs.get(10).isDirty());	//addi R10,R0,100 pretende escrever em R10
	}
	
	@Test
	public void deveSaberRepassarParaOBusDeSaidaOPCRecebedioNoBusDeEntrada() {
		this.circuit.putInInputBus("pc", 4);
		this.circuit.run();
		assertEquals(Integer.valueOf(4), this.circuit.getFromOutputBus("pc"));
	}
	
	
	@Test
	public void deveSaberColocarNoBusDeSaidaOValorDosRegistradoresQueAOperacaoPretendeLer() {
		this.circuit.run();
		assertEquals(0, this.circuit.getFromOutputBus("rs"));
		assertNull(this.circuit.getFromOutputBus("rt"));
	}
	
	@Test
	public void deveSaberColocarODadoImmediatoNoBusDeSaida() {
		this.circuit.run();
		assertEquals(100, this.circuit.getFromOutputBus("imm"));
	}
	
	@Test
	public void deveSaberColocarOsRegistradoresCorretosNaSaidaQuandoForSw() {
		Instrucao instrucao = new Instrucao("10101100000010010000000000011000"); //sw R9,24(R0)
		this.circuit.putInInputBus("instrucao", instrucao);
		this.regs.get(9).setValue(10);
		this.circuit.run();
		
		assertEquals(10, this.circuit.getFromOutputBus("rt"));  //R9 contem valor 10
		assertEquals(24, this.circuit.getFromOutputBus("imm"));
		assertEquals(0, this.circuit.getFromOutputBus("rs"));
	}
	
	@Test
	public void deveSaberColocarOsRegistradoresCorretosNaSaidaQuandoForLw() {
		Instrucao instrucao = new Instrucao("10001100000001100000000000011100"); //lw R6,28(R0)
		this.circuit.putInInputBus("instrucao", instrucao);
		this.circuit.run();
		
		assertNull(this.circuit.getFromOutputBus("rt"));
		assertEquals(28, this.circuit.getFromOutputBus("imm"));
		assertEquals(0, this.circuit.getFromOutputBus("rs"));
	}
	
	
	@Test
	public void deveBloquearAOperacaoSeVaiPrecisarRecuperarValorDeRegistradorSujo() {
		this.regs.get(0).setDirty(); //marcamos R0 como sujo para simular leitura em reg sujo
		
		this.circuit.run();
		
		assertTrue(this.circuit.isBlocked());

		assertNull(this.circuit.getFromOutputBus("rs"));  //nao tem tal informacao no bus de saida
		assertNull(this.circuit.getFromOutputBus("rt"));
		assertNull(this.circuit.getFromOutputBus("imm"));
		assertFalse(this.regs.get(10).isDirty());	//nao chegou a tocar em R10. Foi bloqueado antes
	}
	
	@Test
	public void deveSaberDecodificarUmaInstrucaoEmSinaisDeControle() {
		this.circuit.run();
		
		assertEquals(ALU.ADD_CODE, this.controle.get("ALUOp"));  //vai ser um add
		assertEquals(1, this.controle.get("ALUSrc")); //vai usar dado immediato na ULA
		assertEquals(1, this.controle.get("RegWr"));  // vai escrever em registrador
		assertEquals(10, this.controle.get("RegWrID"));  // vai escrever em R10
		assertEquals(0, this.controle.get("MemToReg"));  //nao vai ser uma escrita de memoria para registrador 
		assertEquals(0, this.controle.get("MemWr"));  //nao vai escrever na memoria
		assertEquals(0, this.controle.get("Branch"));  //nao eh branch
		assertEquals(0, this.controle.get("Jump"));  //nao eh jump
		
		
	}
	
	@Test
	public void deveSaberDecodificarSinaisDeControleParaBranches() {
		Instrucao instrucao = new Instrucao("00011100110010100000000000001100"); //ble R6, R10, 12
		this.circuit.putInInputBus("instrucao", instrucao);
		this.circuit.run();
		
		assertEquals(ALU.SUB_CODE, this.controle.get("ALUOp"));  //vai ser um sub
		assertEquals(0, this.controle.get("ALUSrc")); //nao vai usar dado immediato na ULA
		assertEquals(0, this.controle.get("RegWr"));  // nao vai escrever em registrador
		assertNull(this.controle.get("RegWrID"));  // nao vai escrever em registrador nenhum
		assertEquals(0, this.controle.get("MemToReg"));  //nao vai ser uma escrita de memoria para registrador 
		assertEquals(0, this.controle.get("MemWr"));  //nao vai escrever na memoria
		assertEquals(1, this.controle.get("Branch"));  //Eh branch
		assertEquals(0, this.controle.get("Jump"));  //nao eh jump
		
		assertEquals("ble", this.controle.get("BranchType"));
	}
	
	@Test
	public void deveSaberDecodificarSinaisDeControleParaMul() {
		Instrucao instrucao = new Instrucao("00000000110001100011100000011000"); //mul R7,R6,R6
		this.circuit.putInInputBus("instrucao", instrucao);
		this.circuit.run();
		
		assertEquals(ALU.MUL_CODE, this.controle.get("ALUOp"));  //vai ser um mul
		assertEquals(0, this.controle.get("ALUSrc")); //nao vai usar dado immediato na ULA
		assertEquals(1, this.controle.get("RegWr"));  // vai escrever em registrador
		assertEquals(7, this.controle.get("RegWrID"));  // vai escrever em R7
		assertEquals(0, this.controle.get("MemToReg"));  //nao vai ser uma escrita de memoria para registrador 
		assertEquals(0, this.controle.get("MemWr"));  //nao vai escrever na memoria
		assertEquals(0, this.controle.get("Branch"));  //nao eh branch
		assertEquals(0, this.controle.get("Jump"));  //nao eh jump
	}
	
	@Test
	public void deveSaberDecodificarSinaisDeControleParaNop() {
		Instrucao instrucao = new Instrucao("00000000000000000000000000000000"); //nop
		this.circuit.putInInputBus("instrucao", instrucao);
		this.circuit.run();
		
		assertEquals(ALU.NOP_CODE, this.controle.get("ALUOp"));  //vai ser um nop
		assertEquals(0, this.controle.get("ALUSrc")); //nao vai usar dado immediato na ULA
		assertEquals(0, this.controle.get("RegWr"));  // nao vai escrever em registrador
		assertNull(this.controle.get("RegWrID"));  // vai escrever em lugar nenhum
		assertEquals(0, this.controle.get("MemToReg"));  //nao vai ser uma escrita de memoria para registrador 
		assertEquals(0, this.controle.get("MemWr"));  //nao vai escrever na memoria
		assertEquals(0, this.controle.get("Branch"));  //nao eh branch
		assertEquals(0, this.controle.get("Jump"));  //nao eh jump
	}
	
	@Test
	public void deveSaberDecodificarSinaisDeControleParaLw() {
		Instrucao instrucao = new Instrucao("10001100000001100000000000011100"); //lw R6,28(R0)
		this.circuit.putInInputBus("instrucao", instrucao);
		this.circuit.run();
		
		assertEquals(ALU.ADD_CODE, this.controle.get("ALUOp"));  //vai ser um add
		assertEquals(1, this.controle.get("ALUSrc")); //vai usar dado immediato na ULA
		assertEquals(1, this.controle.get("RegWr"));  //vai escrever em registrador
		assertEquals(6, this.controle.get("RegWrID"));  // vai escrever em R6
		assertEquals(1, this.controle.get("MemToReg"));  //vai ser uma escrita de memoria para registrador 
		assertEquals(0, this.controle.get("MemWr"));  //nao vai escrever na memoria
		assertEquals(1, this.controle.get("MemRead"));  //vai ler da memoria
		assertEquals(0, this.controle.get("Branch"));  //nao eh branch
		assertEquals(0, this.controle.get("Jump"));  //nao eh jump
		
	}
	
	@Test
	public void deveSaberDecodificarSinaisDeControleParaSw() {
		Instrucao instrucao = new Instrucao("10101100000010010000000000011000"); //sw R9,24(R0)
		this.circuit.putInInputBus("instrucao", instrucao);
		this.circuit.run();
		
		assertEquals(ALU.ADD_CODE, this.controle.get("ALUOp"));  //vai ser um add
		assertEquals(1, this.controle.get("ALUSrc")); //vai usar dado immediato na ULA
		assertEquals(0, this.controle.get("RegWr"));  // nao vai escrever em registrador
		assertNull(this.controle.get("RegWrID"));  // vai escrever em lugar nenhum
		assertEquals(0, this.controle.get("MemToReg"));  //nao vai ser uma escrita de memoria para registrador 
		assertEquals(1, this.controle.get("MemWr"));  //vai escrever na memoria
		assertEquals(0, this.controle.get("MemRead"));  //nao vai ler da memoria
		assertEquals(0, this.controle.get("Branch"));  //nao eh branch
		assertEquals(0, this.controle.get("Jump"));  //nao eh jump
		
	}
	
	
	/*
	 * Apenas uma fabrica de Regs para ajudar nos testes
	 */
	private List<Reg> criaRegs() {
		List<Reg> regs = new ArrayList<Reg>();
		
		for (int i = 0; i < 32; i++) {
			regs.add(new Reg(i));
		}
		
		regs.get(0).setValue(0);
		
		return regs;
	}


}
