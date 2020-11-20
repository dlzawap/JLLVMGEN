package jllvmgen.instructions.binary;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMFastMathFlags;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;

/**
 * The ‘fdiv’ instruction returns the quotient of its two operands.
 * The two arguments to the ‘fdiv’ instruction must be floating-point or vector of floating-point values. Both arguments must have identical types.
 */
public class LLVMFDivInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue op1;
	private LLVMDataValue op2;
	private LLVMFastMathFlags[] fastMathFlags;
	
	public static LLVMFDivInst create(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2) throws LLVMException
	{
		return new LLVMFDivInst(fn, op1, op2, null);
	}
	
	public static LLVMFDivInst create(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, LLVMFastMathFlags... fastMathFlags) throws LLVMException
	{
		return new LLVMFDivInst(fn, op1, op2, fastMathFlags);
	}
	
	public LLVMFDivInst(LLVMFunction fn, LLVMDataValue op1, LLVMDataValue op2, LLVMFastMathFlags[] fastMathFlags) throws LLVMException
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
		
		// Check if both operands are value or vector types with an floating-point base type.
		if (op1.getType().isValueType())
		{
			LLVMValueType temp = (LLVMValueType)op1.getType();
			if (!temp.holdsFloatingPoint())
				throw new LLVMException("Operands must be from floating-point.");
		}
		else if (op2.getType().isVectorType())
		{
			if (((LLVMVectorType)op1.getType()).getBaseType().isValueType())
			{
				LLVMValueType temp = (LLVMValueType)((LLVMVectorType)op1.getType()).getBaseType();
				if (!temp.holdsFloatingPoint())
					throw new LLVMException("Operands must be vectors of floating-point values.");
			}
			else throw new LLVMException("Operands base types are not floating-point types.");
		}
		
		this.op1 = op1;
		this.op2 = op2;
		this.fastMathFlags = fastMathFlags;
		
		// Pre-generate value.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), op1.getType());
		
		// If activated, register instruction.
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
		
		sb.append(" = fdiv ");
		
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
		sb.append(op1.getIdentifierOrValue());
		sb.append(", ");
		sb.append(op2.getIdentifierOrValue());
		
		return sb.toString();
	}
}