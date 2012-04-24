package mips;

import java.util.HashMap;

public class ALU {

	public static final Integer NOP_CODE = 0;
	public static final Integer ADD_CODE = 1;
	public static final Integer SUB_CODE = 2;
	public static final Integer MUL_CODE = 3;

	public static Integer getControlCode(String aluOp) {
		HashMap<String, Integer> aluOpMap = new HashMap<String, Integer>();
		aluOpMap.put("nop", NOP_CODE);
		aluOpMap.put("add", ADD_CODE);
		aluOpMap.put("sub", SUB_CODE);
		aluOpMap.put("mul", MUL_CODE);
		
		return aluOpMap.get(aluOp);
	}

	public static boolean isMul(Integer op) {
		return op == MUL_CODE;
	}

	public static Integer run(Integer operand1, Integer operand2, Integer operation) {
		if (operation == ADD_CODE)
			return operand1 + operand2;
		
		if (operation == SUB_CODE)
			return operand1 - operand2;
		
		if (operation == MUL_CODE)
			return operand1 * operand2;
		
		return null;
	}
	

}
