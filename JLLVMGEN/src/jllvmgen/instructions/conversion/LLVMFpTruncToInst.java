package jllvmgen.instructions.conversion;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMValueType;

public class LLVMFpTruncToInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue value;
	
	public static LLVMFpTruncToInst create(LLVMFunction fn, LLVMDataValue value, ILLVMMemoryType type) throws LLVMException
	{
		return new LLVMFpTruncToInst(fn, value, type);
	}
	
	public LLVMFpTruncToInst(LLVMFunction fn, LLVMDataValue value, ILLVMMemoryType type) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (type == null)
			throw new LLVMException("Parameter \"type\" is null or empty.");
		
		if (value.getType().isValueType())
		{
			LLVMValueType valueType = (LLVMValueType)value.getType();
			
			if (!valueType.holdsFloatingPoint())
			{
				throw new LLVMException("Value type must hold an integer. Value type: " +
										valueType.getTypeDefinitionString());
			}
		}
		else throw new LLVMException("Value type must be an integer value type.");
		
		if (value.getType().isValueType())
		{
			LLVMValueType valueType = (LLVMValueType)value.getType();
			
			if (!valueType.holdsFloatingPoint())
			{
				throw new LLVMException("Value type must hold a floating-point value. Value type: " +
										valueType.getTypeDefinitionString());
			}
			// Check if trunc-to type is smaller than the value type.
			// TODO: Implement type size of value types.
		}
		else throw new LLVMException("Value type must be an integer value type.");
			
			
		// Pre-generate value.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), type);
		
		
		if (fn.autoRegisterInstructions())
			fn.registerInst(this);
	}
	
	@Override
	public String getInstructionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		sb.append(" = fptrunc ");
		sb.append(value.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(value.getIdentifierOrValue());
		sb.append(" to ");
		sb.append(result.getType().getTypeDefinitionString());
		
		return sb.toString();
	}
}