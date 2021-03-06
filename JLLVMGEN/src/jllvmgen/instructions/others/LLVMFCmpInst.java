package jllvmgen.instructions.others;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMFCompareConditions;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMValueType;

public class LLVMFCmpInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMFCompareConditions compareCondition;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	
	private LLVMFCmpInst(LLVMFunction fn, LLVMFCompareConditions compareCondition, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (op1 == null)
			throw new LLVMException("Parameter \"op1\" is null or empty.");
		if (op2 == null)
			throw new LLVMException("Parameter \"op2\" is null or empty.");
		if (!op1.getType().equals(op2.getType()))
			throw new LLVMException("Both operands are not from the same type. "
					+ "Op1 type: " + op1.getType().getTypeDefinitionString() + " "
					+ "Op2 type: " + op2.getType().getTypeDefinitionString());
		if (op1.getType().isValueType())
		{
			LLVMValueType temp = (LLVMValueType)op1.getType();
			if (!temp.holdsFloatingPoint())
				throw new LLVMException("Operands must be values from floating-point type.");
		}
		
		this.compareCondition = compareCondition;
		this.op1 = op1;
		this.op2 = op2;
		
		// Pre-generate result value.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), LLVMValueType.createBool());
	}
	
	public LLVMDataValue getResult()
	{
		return result;
	}
	
	@Override
	public String getInstructionString() throws LLVMException 
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		
		sb.append(" = fcmp ");
		sb.append(compareCondition);
		sb.append(" ");
		sb.append(op1.getType().getTypeDefinitionString());
		sb.append(" ");
		sb.append(op1.getIdentifierOrValue());
		sb.append(", ");
		sb.append(op2.getIdentifierOrValue());
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMFCmpInst create(LLVMFunction fn, LLVMFCompareConditions compareCondition, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		var instruction = new LLVMFCmpInst(fn, compareCondition, op1, op2);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMFCmpInst create(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMFCompareConditions compareCondition, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		var instruction = new LLVMFCmpInst(fn, compareCondition, op1, op2);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}