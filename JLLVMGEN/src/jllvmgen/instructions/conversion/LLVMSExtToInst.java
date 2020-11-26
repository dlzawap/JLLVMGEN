package jllvmgen.instructions.conversion;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMValueType;

public class LLVMSExtToInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataValue value;
	
	public LLVMSExtToInst(LLVMFunction fn, LLVMDataValue value, ILLVMMemoryType type) throws LLVMException
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
			
			if (!valueType.holdsInteger())
			{
				throw new LLVMException("Value type must hold an integer. Value type: " +
										valueType.getTypeDefinitionString());
			}
		}
		else throw new LLVMException("Value type must be an integer value type.");
		
		if (value.getType().isValueType())
		{
			LLVMValueType valueType = (LLVMValueType)value.getType();
			
			if (!valueType.holdsInteger())
			{
				throw new LLVMException("Value type must hold an integer. Value type: " +
										valueType.getTypeDefinitionString());
			}
			// Check if trunc-to type is larger than the value type.
			// TODO: Implement type size of value types.
		}
		else throw new LLVMException("Value type must be an integer value type.");
			
			
		// Pre-generate result value.
		result = LLVMDataValue.createLocalVariable(fn.getNextFreeLocalVariableValueName(), type);
	}
	
	@Override
	public String getInstructionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		sb.append(" = sext ");
		sb.append(value.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(value.getIdentifierOrValue());
		sb.append(" to ");
		sb.append(result.getType().getTypeDefinitionString());
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMSExtToInst create(LLVMFunction fn, LLVMDataValue value, ILLVMMemoryType type) throws LLVMException
	{
		var instruction = new LLVMSExtToInst(fn, value, type);

		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMSExtToInst create(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMDataValue value, ILLVMMemoryType type) throws LLVMException
	{
		var instruction = new LLVMSExtToInst(fn, value, type);

		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
