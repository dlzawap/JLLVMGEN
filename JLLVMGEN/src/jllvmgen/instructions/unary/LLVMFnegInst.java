package jllvmgen.instructions.unary;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMFastMathFlags;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;

//TODO: Add vector validation.

public class LLVMFnegInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue value;
	private LLVMFastMathFlags[] fastMathFlags;
	
	
	public static LLVMFnegInst create(LLVMFunction fn, LLVMDataValue value) throws LLVMException
	{
		return new LLVMFnegInst(fn, value, null);
	}

	public static LLVMFnegInst create(LLVMFunction fn, LLVMDataValue value, LLVMFastMathFlags... fastMathFlags) throws LLVMException
	{
		return new LLVMFnegInst(fn, value, fastMathFlags);
	}
	
	public LLVMFnegInst(LLVMFunction fn, LLVMDataValue value, LLVMFastMathFlags[] fastMathFlags) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"function\" is null or empty.");
		if (result == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (!value.getType().isValueType() &&
			!((LLVMValueType)value.getType()).holdsFloatingPoint())// ||
			//!value.getType().isVectorType() &&
			//!((LLVMValueType)((LLVMVectorType)value.getType())).holdsFloatingPoint())
		{
			throw new LLVMException("Fneg instruction is only on floating-point or vector of floating-point values allowed.");
		}
		
		this.value = value;
		this.fastMathFlags = fastMathFlags;
		
		// Pre-generate result type.
		result = LLVMDataValue.create(fn.getNextFreeLocalVariableValueName(), value.getType());
		
		// Register instruction.
		fn.registerInst(this);
	}
	
	
	@Override
	public String getInstructionString() throws LLVMException 
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		
		sb.append(" = fneg ");
		
		// Append fast math flags
		if (fastMathFlags != null)
		{
			for (LLVMFastMathFlags flag : fastMathFlags)
			{
				sb.append(flag);
				sb.append(' ');
			}
		}
		
		sb.append(result.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(value.getIdentifierOrValue());
		
		
		return sb.toString();
	}
}
