package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.LLVMDataValue;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;

public class LLVMLoadInst  implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataPointer pointer;
	
	private LLVMLoadInst(LLVMFunction fn, LLVMDataPointer pointer) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"function\" is null or empty.");
		if (pointer == null)
			throw new LLVMException("Parameter \"pointer\" is null or empty");
		
		this.pointer = pointer;
		
		// Pre-generate value.
		// Use base type of pointer type. (i32* -> i32)
		result = LLVMDataValue.createLocalVariable(
				 fn.getNextFreeLocalPointerValueName(),
				 pointer.getType().getBaseType());
	}
	
	public LLVMDataValue getResult()
	{
		return result;
	}
	
	@Override
	public String getInstructionString() throws LLVMException {
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		
		sb.append(" = load ");
		sb.append(result.getType().getTypeDefinitionString());
		sb.append(", ");
		sb.append(pointer.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(pointer.getIdentifier());
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMLoadInst create(LLVMFunction fn, LLVMDataPointer pointer) throws LLVMException
	{
		var instruction = new LLVMLoadInst(fn, pointer);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMLoadInst create(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMDataPointer pointer) throws LLVMException
	{
		var instruction = new LLVMLoadInst(fn, pointer);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
