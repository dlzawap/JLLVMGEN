package jllvmgen.instructions.binary;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;

/**
 * The ‘mul’ instruction returns the product of its two operands.
 * The two arguments to the ‘mul’ instruction must be integer or vector of integer values. Both arguments must have identical types.
 * If a full product (e.g. i32 * i32 -> i64) is needed, the operands should be sign-extended or zero-extended as appropriate to the width of the full product.
 */
public class LLVMMulInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	
	private boolean noUnsignedWrap;
	private boolean noSignedWrap;
	
	public static LLVMMulInst create(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, boolean noUnsignedWrap, boolean noSignedWrap) throws LLVMException
	{
		return new LLVMMulInst(fn, op1, op2, noUnsignedWrap, noSignedWrap);
	}
	
	public static LLVMMulInst createNoUnsignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMMulInst(fn, op1, op2, true, false);
	}
	
	public static LLVMMulInst createNoSignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMMulInst(fn, op1, op2, false, true);
	}
	
	public static LLVMMulInst createNoUnsignedAndSignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMMulInst(fn, op1, op2, true, true);
	}
	
	public LLVMMulInst(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, boolean noUnsignedWrap, boolean noSignedWrap) throws LLVMException
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
			if (!temp.holdsPrimInt())
				throw new LLVMException("Operands must be from integers.");
		}
		else if (op2.getType().isVectorType())
		{
			if (((LLVMVectorType)op1.getType()).getBaseType().isValueType())
			{
				LLVMValueType temp = (LLVMValueType)((LLVMVectorType)op1.getType()).getBaseType();
				if (!temp.holdsPrimInt())
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
		
		sb.append(" = mul ");
		
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