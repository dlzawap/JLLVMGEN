package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.LLVMDataValue;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;

public class LLVMLoadInst  implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataPointer pointer;
	
	public static LLVMLoadInst createInst(LLVMFunction function, LLVMDataPointer pointer) throws LLVMException
	{
		return new LLVMLoadInst(function, pointer);
	}
	
	private LLVMLoadInst(LLVMFunction function, LLVMDataPointer pointer) throws LLVMException
	{
		if (function == null)
			throw new LLVMException("Parameter \"function\" is null or empty.");
		if (pointer == null)
			throw new LLVMException("Parameter \"pointer\" is null or empty");
		
		this.pointer = pointer;
		
		// Pre-generate value.
		// Use base type of pointer type. (i32* -> i32)
		result = LLVMDataValue.createLocalVariable(
				function.getNextFreeLocalPointerValueName(),
				pointer.getType().getBaseType());
		
		// Register instruction.
		function.registerInst(this);
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
}
