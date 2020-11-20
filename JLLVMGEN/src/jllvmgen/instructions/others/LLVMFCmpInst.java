package jllvmgen.instructions.others;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMFCompareConditions;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;

public class LLVMFCmpInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMFCompareConditions compareCondition;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	
	public static LLVMFCmpInst create(LLVMFunction fn, LLVMFCompareConditions compareCondition, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMFCmpInst(fn, compareCondition, op1, op2);
	}
	
	public LLVMFCmpInst(LLVMFunction fn, LLVMFCompareConditions compareCondition, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
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
		
		// Pre-generate result.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), LLVMValueType.createBool());
		
		if (fn.autoRegisterInstructions())
			fn.registerInst(this);
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
		sb.append(" ");
		sb.append(op2.getIdentifierOrValue());
		
		return sb.toString();
	}
}