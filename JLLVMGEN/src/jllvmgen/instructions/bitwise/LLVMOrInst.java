package jllvmgen.instructions.bitwise;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;

/**
 * The ‘or’ instruction returns the bitwise logical inclusive or of its two operands.
 * The two arguments to the ‘or’ instruction must be integer or vector of integer values.
 * Both arguments must have identical types.
 */
public class LLVMOrInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	
	public LLVMOrInst(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (op1 == null)
			throw new LLVMException("Parameter \"op1\" is null or empty.");
		if (op2 == null)
			throw new LLVMException("Parameter \"op2\" is null or empty.");
		if (!op1.getType().equals(op2.getType()))
			throw new LLVMException("Both operands are not from the same base type."
					+ "Op1: " + op1.getType().getTypeDefinitionString() + " "
					+ "Op2: " + op2.getType().getTypeDefinitionString());
		
		// Check if both operands are value or vector types with an integer base type.
		if (op1.getType().isValueType())
		{
			LLVMValueType temp = (LLVMValueType)op1.getType();
			if (!temp.holdsInteger())
				throw new LLVMException("Operands must be from integers.");
		}
		else if (op2.getType().isVectorType())
		{
			if (((LLVMVectorType)op1.getType()).getBaseType().isValueType())
			{
				LLVMValueType temp = (LLVMValueType)((LLVMVectorType)op1.getType()).getBaseType();
				if (!temp.holdsInteger())
					throw new LLVMException("Operands must be vectors of integer values.");
			}
			else throw new LLVMException("Operands base types are not value types.");
		}
		
		this.op1 = op1;
		this.op2 = op2;
		
		
		// Pre-generate result value.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), op1.getType());
	}
	
	public LLVMDataValue getResult()
	{
		return result;
	}
	
	@Override
	public String getInstructionString() throws LLVMException 
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		
		sb.append(" = or ");
		
		sb.append(result.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(op1.getIdentifierOrValue());
		sb.append(", ");
		sb.append(op2.getIdentifierOrValue());
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMOrInst create(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		var instruction = new LLVMOrInst(fn, op1, op2);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMOrInst create(LLVMFunction fn, LLVMLabelType label, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		var instruction = new LLVMOrInst(fn, op1, op2);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registersInstructionIntoLabelSection(label, instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}