package jllvmgen.instructions.unary;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMFastMathFlags;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVectorType;


/**
 * The ‘fneg’ instruction returns the negation of its operand.
 * The argument to the ‘fneg’ instruction must be a floating-point or vector of floating-point values.
 */
public class LLVMFnegInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue value;
	private LLVMFastMathFlags[] fastMathFlags;
	
	public LLVMFnegInst(LLVMFunction fn, LLVMDataValue value, LLVMFastMathFlags[] fastMathFlags) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"function\" is null or empty.");
		if (result == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (value.getType().isValueType())
		{
			LLVMValueType temp = (LLVMValueType)value.getType();
			if (!temp.holdsFloatingPoint())
				throw new LLVMException("Fneg instruction is only on floating-point or vector of floating-point values allowed.");
		}
		else if (value.getType().isVectorType())
		{
			if (((LLVMVectorType)value.getType()).getBaseType().isValueType())
			{
				LLVMValueType temp = (LLVMValueType)((LLVMVectorType)value.getType()).getBaseType();
				if (!temp.holdsFloatingPoint())
					throw new LLVMException("Fneg instruction is only on floating-point or vector of floating-point values allowed.");
			}
			else throw new LLVMException("Fneg instruction is only on floating-point or vector of floating-point values allowed.");
		}
		
		this.value = value;
		this.fastMathFlags = fastMathFlags;
		
		// Pre-generate result value.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), value.getType());
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
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMFnegInst create(LLVMFunction fn, LLVMDataValue value) throws LLVMException
	{
		var instruction = new LLVMFnegInst(fn, value, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}

	public static LLVMFnegInst create(LLVMFunction fn, LLVMDataValue value, LLVMFastMathFlags... fastMathFlags) throws LLVMException
	{
		var instruction = new LLVMFnegInst(fn, value, fastMathFlags);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMFnegInst create(LLVMFunction fn, LLVMLabelType label, LLVMDataValue value) throws LLVMException
	{
		var instruction = new LLVMFnegInst(fn, value, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registersInstructionIntoLabelSection(label, instruction);
		
		return instruction;
	}

	public static LLVMFnegInst create(LLVMFunction fn, LLVMLabelType label, LLVMDataValue value, LLVMFastMathFlags... fastMathFlags) throws LLVMException
	{
		var instruction = new LLVMFnegInst(fn, value, fastMathFlags);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registersInstructionIntoLabelSection(label, instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
