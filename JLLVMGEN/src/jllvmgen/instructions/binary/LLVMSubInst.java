package jllvmgen.instructions.binary;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;

/**
 * The ‘sub’ instruction returns the difference of its two operands.
 * Note that the ‘sub’ instruction is used to represent the ‘neg’ instruction present in most other intermediate representations.
 * The two arguments to the ‘sub’ instruction must be integer or vector of integer values. Both arguments must have identical types.
 */
public class LLVMSubInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	
	private boolean noUnsignedWrap;
	private boolean noSignedWrap;
	
	public static LLVMSubInst create(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, boolean noUnsignedWrap, boolean noSignedWrap) throws LLVMException
	{
		return new LLVMSubInst(fn, op1, op2, noUnsignedWrap, noSignedWrap);
	}
	
	public static LLVMSubInst createNoUnsignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMSubInst(fn, op1, op2, true, false);
	}
	
	public static LLVMSubInst createNoSignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMSubInst(fn, op1, op2, false, true);
	}
	
	public static LLVMSubInst createNoUnsignedAndSignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMSubInst(fn, op1, op2, true, true);
	}
	
	public LLVMSubInst(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, boolean noUnsignedWrap, boolean noSignedWrap) throws LLVMException
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
		
		this.noUnsignedWrap = noUnsignedWrap;
		this.noSignedWrap = noSignedWrap;
		
		// If activated, register instruction.
		if (fn.autoRegisterInstructions())
			fn.registerInst(this);
	}
	
	@Override
	public String getInstructionString() throws LLVMException 
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		
		sb.append(" = sub ");
		
		if (noUnsignedWrap)
			sb.append("nuw ");
		if (noSignedWrap)
			sb.append("nsw ");
		
		sb.append(result.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(op1.getIdentifierOrValue());
		sb.append(", ");
		sb.append(op2.getIdentifierOrValue());
		
		return sb.toString();
	}
}
