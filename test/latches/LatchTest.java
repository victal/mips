package latches;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.List;

import instrucoes.Instrucao;

import mips.ALU;
import mips.Controle;

import org.junit.Before;
import org.junit.Test;

import circuitos.Bus;
import circuitos.IDCircuit;
import circuitos.IFCircuit;

import registradores.Reg;


public class LatchTest {
	
	
	@Test
	public void deveSerPossivelAlimentarUmaEntradaEColetarElaNaSaidaAposComandoDeEscrita() {
		Latch latch = new Latch();
		
		Bus inputBus = new Bus();
		inputBus.put("key", 10);
		latch.setInput(inputBus);
		
		assertNull(latch.getOutput());
		latch.sendWritePulse();
		assertNotNull(latch.getOutput());
		assertEquals(10, latch.getOutput().get("key"));
	}
	
	@Test
	public void deveSerPossivelAbsorverUmControleNaEntradaEManterEleNaSaida() {
		Latch latch = new Latch();
		
		Controle controle = new Controle();
		controle.put("key", 10);
		
		latch.addInputControl(controle);
		
		assertNull(latch.getOutputControl());
		latch.sendWritePulse();
		assertEquals(10, latch.getOutputControl().get("key"));
		
	}
	
	@Test
	public void deveManterAMesmaSaidaAtehQueOutroComandoDeEscritaSejaDado() {
		Latch latch = new Latch();
		
		Bus inputBus = new Bus();
		inputBus.put("key", 10);
		latch.setInput(inputBus);
		
		Controle controle = new Controle();
		controle.put("key", 100);
		latch.addInputControl(controle);
		
		latch.sendWritePulse();
		assertEquals(10, latch.getOutput().get("key"));
		assertEquals(100, latch.getOutputControl().get("key"));
		
		inputBus.put("key", 20);
		controle.put("key", 200);
		assertEquals(10, latch.getOutput().get("key"));		//valor antigo ainda
		assertEquals(100, latch.getOutputControl().get("key"));  //valor antigo ainda
		
		latch.sendWritePulse();
		assertEquals(20, latch.getOutput().get("key"));  //atualizou
		assertEquals(200, latch.getOutputControl().get("key"));  //atualizou
	}
	
	@Test
	public void deveSaberSeConectarACircuitosNaEntradaESaida() {
		Latch latch = new Latch();
		
		Controle controle1 = new Controle();
		Controle controle2 = new Controle();
		IFCircuit inputCircuit = new IFCircuit();
		inputCircuit.setControl(controle1);
		IDCircuit outputCircuit = new IDCircuit();
		outputCircuit.setControl(new Controle());
		outputCircuit.setControl(controle2);
		
		latch.connectInput(inputCircuit);
		latch.connectOutput(outputCircuit);
		
		inputCircuit.putInOutputBus("key", 10);
		controle1.put("key", 100);
		
		
		latch.sendWritePulse();
		
		assertEquals(10, latch.getOutput().get("key"));
		assertEquals(100, latch.getOutputControl().get("key"));
		
		assertEquals(10, outputCircuit.getFromInputBus("key"));
		assertEquals(100, outputCircuit.getControl().get("key"));
		
	
		//tenta uma atualizacao falsa e confere que nada aconteceu na saida
		inputCircuit.putInOutputBus("key", 30);
		controle1.put("key", 1000);
		
		assertEquals(10, latch.getOutput().get("key"));
		assertEquals(100, latch.getOutputControl().get("key"));
		assertEquals(10, outputCircuit.getFromInputBus("key"));
		assertEquals(100, outputCircuit.getControl().get("key"));
		
		//agora manda o pulso de escrita e confere
		latch.sendWritePulse();  
		assertEquals(30, outputCircuit.getFromInputBus("key"));
		assertEquals(1000, outputCircuit.getControl().get("key"));
		assertEquals(30, outputCircuit.getFromInputBus("key"));
		assertEquals(1000, outputCircuit.getControl().get("key"));
		
	}
	
	
	

}
