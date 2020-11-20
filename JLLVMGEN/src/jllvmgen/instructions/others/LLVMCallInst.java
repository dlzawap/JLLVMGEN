package jllvmgen.instructions.others;

import java.util.ArrayList;

import jllvmgen.ILLVMVariableType;
import jllvmgen.LLVMDataPointer;
import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMCallingConvention;
import jllvmgen.enums.LLVMFastMathFlags;
import jllvmgen.enums.LLVMTailFlags;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

/*
 * TODO:
 * 1. Create wrapper class for fn arguments, to allowed parameter arguments (noext, signext, zeroext)
 * 2. Replace function name with a function type or function pointer class.
 * 3. Allow var_arg
 * 
 * HINT:
 * For extern function, function pointers are needed??
 * call i32 (i8*, ...)* @printf(i8* c"sd")
 * 
 * It seems like, its possible to retrieve result from a function call with a void as return type.
 */

public class LLVMCallInst implements ILLVMBaseInst
{
	private LLVMDataValue 				 resultValue;
	private LLVMDataPointer 			 resultPointer;
	private ILLVMMemoryType 			 resultType;
	private String 						 calleeFnName;
	private ArrayList<ILLVMVariableType> arguments
		= new ArrayList<ILLVMVariableType>();
	
	private LLVMTailFlags 			tailFlag;
	private LLVMFastMathFlags[] 	fastMathFlags;
	private LLVMCallingConvention 	callingConv;

	
	public static LLVMCallInst create(
			LLVMFunction fn,
			ILLVMMemoryType resultType,
			String calleeFnName,
			LLVMTailFlags tailFlag,
			LLVMFastMathFlags[] fastMathFlags,
			LLVMCallingConvention cc,
			Integer addrSpace,
			ArrayList<ILLVMVariableType> arguments) throws LLVMException
	{
		return new LLVMCallInst(fn, resultType, calleeFnName, tailFlag, fastMathFlags, cc, addrSpace, arguments);
	}
	
	public LLVMCallInst(
			LLVMFunction fn,
			ILLVMMemoryType resultType,
			String calleeFnName,
			LLVMTailFlags tailFlag,
			LLVMFastMathFlags[] fastMathFlags,
			LLVMCallingConvention cc,
			Integer addrSpace,
			ArrayList<ILLVMVariableType> arguments) throws LLVMException
	{
		this.resultType = resultType;
		this.calleeFnName = calleeFnName;
		
		if (arguments != null)
			this.arguments = arguments;
		
		// Pre-generate result pointer.
		if (resultType.isValueType())
		{
			resultValue = LLVMDataValue.createLocalVariable(
					fn.getNextFreeLocalVariableValueName(),
					resultType);
		}
		else if (resultType.isPointerType())
		{
			resultPointer = LLVMDataPointer.createLocalVariable(
					fn.getNextFreeLocalVariableValueName(),
					resultType);
		}
		
		if (fn.autoRegisterInstructions())
			fn.registerInst(this);
	}
	
	public LLVMDataValue getResultValue()
	{
		return resultValue;
	}
	
	public LLVMDataPointer getResultPointer()
	{
		return resultPointer;
	}
	
	@Override
	public String getInstructionString() throws LLVMException
	{
		StringBuilder sb;
		
		if (resultValue != null)
			sb = new StringBuilder(resultValue.getIdentifier() + " = ");
		else if (resultPointer != null)
			sb = new StringBuilder(resultPointer.getIdentifier() + " = ");
		else
			sb = new StringBuilder();
		
		sb.append("call ");
			
		sb.append(resultType.getTypeDefinitionString());
		sb.append(" ");
		sb.append(calleeFnName);
		sb.append(" (");
		
		for (int i = 0; i < arguments.size(); ++i)
		{
			var arg = arguments.get(i);
			
			if (i == 0)
				sb.append(", ");
			
			sb.append(arg.getType().getTypeDefinitionString());
			sb.append(" ");
			
			if (arg.isConstant() || arg.isValueVariable())
			{
				sb.append(((LLVMDataValue)arg).getIdentifierOrValue());
			}
			else
			{
				sb.append(arg.getIdentifier());
			}
		}
		
		sb.append(")");
		return sb.toString();
	}
}
