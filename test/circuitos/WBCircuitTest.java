package circuitos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import instrucoes.Instrucao;

import memorias.MemoriaDados;
import mips.ALU;
import mips.Controle;

import org.junit.Before;
import org.junit.Test;

import registradores.Reg;


public class WBCircuitTest {
	
	private WBCircuit circuit;
	private List<Reg> regs;

	@Before
	public void setUp() {
		this.circuit = new WBCircuit();
		this.regs = criaRegs();
		this.circuit.setMipsRegisters(this.regs);
	}
	
	@Test
	public void deveSaberQueVaiPegarOResultadoDaMemoriaQuandoForLoad(){
		Controle control = criaControleLw();
		this.circuit.setControl(control);
		this.circuit.putInInputBus("memdata", 100);

		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.circuit.getData());
	}
	
	@Test
	public void deveSaberQueVaiPegarOResuldatoDaULAPraEscreverEmRegistradorQuandoNaoForLoad() {
		Controle control = criaControleAdd();
		this.circuit.setControl(control);
		this.circuit.putInInputBus("uladata", 100);

		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.circuit.getData());
	}
	
	@Test
	public void deveColocarOValorFinalNoBusDeSaida() {
		Controle control = criaControleAdd();
		this.circuit.setControl(control);
		this.circuit.putInInputBus("uladata", 100);

		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.circuit.getFromOutputBus("data"));
	}
	
	@Test
	public void deveColocarOValorFinalNoBusDeSaidaQuandoLoad() {
		Controle control = criaControleLw();
		this.circuit.setControl(control);
		this.circuit.putInInputBus("memdata", 100);

		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.circuit.getFromOutputBus("data"));
	}

	
	@Test
	public void deveEscreverNoRegistradorCorretoOValorFinal() {
		Controle control = criaControleAdd();  //add R10, R0, R1
		this.circuit.setControl(control);
		this.circuit.putInInputBus("uladata", 100);

		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.regs.get(10).getValue());
	}	

	@Test
	public void deveEscreverNoRegistradorCorretoOValorFinalQuandoLoad() {
		Controle control = criaControleLw();  //lw R6,28(R0)
		this.circuit.setControl(control);
		this.circuit.putInInputBus("memdata", 100);

		this.circuit.run();
		
		assertEquals(Integer.valueOf(100), this.regs.get(6).getValue());
	}	
	
	@Test
	public void deveMarcarUmRegistradorComoLimpoDepoisQueEscreveuNele() {
		Controle control = criaControleLw();  //lw R6,28(R0)
		this.circuit.setControl(control);
		this.circuit.putInInputBus("memdata", 100);
		this.regs.get(6).setDirty();
		
		this.circuit.run();
		
		assertFalse(this.regs.get(6).isDirty());
		
	}

	@Test
	public void deveSaberIgnorarQuandoNaoForOperacaoQueEscreverEmRegistrador() {
		Controle control = criaControleSw();  //lw R6,28(R0)
		this.circuit.setControl(control);

		this.circuit.run();
		
		assertNull(this.circuit.getFromOutputBus("data"));
		
	}
	
	
	
	/*
	 * Algumas fabricas de sinais de controle para ajudar nos testes
	 */
	private Controle criaControleAdd() {
		//add R10, R0, R1
		Controle controle = new Controle();
		controle.put("ALUOp", ALU.ADD_CODE);  //vai ser um add
		controle.put("ALUSrc", 0); //nao vai usar dado immediato na ULA
		controle.put("RegWr", 1);  // vai escrever em registrador
		controle.put("RegWrID", 10);  // vai escrever em R10
		controle.put("MemToReg", 0);  //nao vai ser uma escrita de memoria para registrador 
		controle.put("MemWr", 0);  //nao vai escrever na memoria
		controle.put("MemRead", 0);  //nao vai ler da memoria
		controle.put("Branch", 0);  //nao eh branch
		controle.put("Jump", 0);  //nao eh jump
		return controle;
	}
	
	private Controle criaControleLw() {
		//lw R6,28(R0)
		Controle controle = new Controle();
		controle.put("ALUOp", ALU.ADD_CODE);  //vai ser um add
		controle.put("ALUSrc", 1); //vai usar dado immediato na ULA
		controle.put("RegWr", 1);  // vai escrever em registrador
		controle.put("RegWrID", 6);  // vai escrever em R10
		controle.put("MemToReg", 1);  //nao vai ser uma escrita de memoria para registrador 
		controle.put("MemWr", 0);  //nao vai escrever na memoria
		controle.put("MemRead", 1);  //vai ler da memoria
		controle.put("Branch", 0);  //nao eh branch
		controle.put("Jump", 0);  //nao eh jump
		return controle;
	}
	
	private Controle criaControleSw() {
		//sw R9,24(R0)
		Controle controle = new Controle();
		controle.put("ALUOp", ALU.ADD_CODE);  //vai ser um add
		controle.put("ALUSrc", 1); //vai usar dado immediato na ULA
		controle.put("RegWr", 0);
		controle.put("MemToReg", 0);  //nao vai ser uma escrita de memoria para registrador 
		controle.put("MemWr", 1);  //vai escrever na memoria
		controle.put("MemRead", 0);  //nao vai ler da memoria
		controle.put("Branch", 0);  //nao eh branch
		controle.put("Jump", 0);  //nao eh jump
		return controle;
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
