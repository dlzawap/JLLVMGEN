package jllvmgen.instructions.bitwise;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;

public class LLVMAShrInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	private boolean noUnsignedWrap;
	private boolean noSignedWrap;
	
	public static LLVMAShrInst create(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, boolean noUnsignedWrap, boolean noSignedWrap) throws LLVMException
	{
		return new LLVMAShrInst(fn, op1, op2, noUnsignedWrap, noSignedWrap);
	}
	
	public static LLVMAShrInst createNoUnsignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMAShrInst(fn, op1, op2, true, false);
	}
	
	public static LLVMAShrInst createNoSignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMAShrInst(fn, op1, op2, false, true);
	}
	
	public static LLVMAShrInst createNoUnsignedAndSignedWrap(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMAShrInst(fn, op1, op2, true, true);
	}
	
	public LLVMAShrInst(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, boolean noUnsignedWrap, boolean noSignedWrap) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (op1 == null)
			throw new LLVMException("Parameter \"op1\" is null or empty.");
		if (op2 == null)
			throw new LLVMException("Parameter \"op2\" is null or empty.");
		
		// Check if both operands are value or vector types with an integer base type.
		if (op1.getType().isValueType())
		{
			LLVMValueType temp = (LLVMValueType)op1.getType();
			if (!temp.holdsInteger())
				throw new LLVMException("Operands must be from integers.");
		}
		else if (op1.getType().isVectorType())
		{
			if (((LLVMVectorType)op1.getType()).getBaseType().isValueType())
			{
				LLVMValueType temp = (LLVMValueType)((LLVMVectorType)op1.getType()).getBaseType();
				if (!temp.holdsInteger())
					throw new LLVMException("Operands must be vectors of integer values.");
			}
			else throw new LLVMException("Operands base types are not value types.");
		}
		
		// Operand 2 must be an integer with a positive value or an integer from an unsigned type.
		if (op2.getType().isValueType())
		{
			LLVMValueType temp = (LLVMValueType)op2.getType();
			if (!temp.holdsInteger())
				throw new LLVMException("Operands must be from integers.");
		}
		
		this.op1 = op1;
		this.op2 = op2;
		this.noUnsignedWrap = noUnsignedWrap;
		this.noSignedWrap = noSignedWrap;
		
		// Pre-generate value.
		result = LLVMDataValue.create(fn.getNextFreeLocalVariableValueName(), op1.getType());
		
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
		
		sb.append(" = ashr ");
		
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