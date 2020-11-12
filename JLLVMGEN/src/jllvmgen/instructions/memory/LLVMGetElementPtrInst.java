package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

public class LLVMGetElementPtrInst  implements ILLVMBaseInst
{
	private LLVMDataPointer result;
	private ILLVMMemoryType type;
	private LLVMDataPointer pointer;
	private int[] indexes;
	
	public static LLVMGetElementPtrInst createInst(LLVMFunction function, LLVMDataPointer pointer, int index) throws LLVMException
	{
		return new LLVMGetElementPtrInst(function, pointer, new int[] { index });
	}
	
	public static LLVMGetElementPtrInst createInst(LLVMFunction function, LLVMDataPointer pointer, int[] indexes) throws LLVMException
	{
		return new LLVMGetElementPtrInst(function, pointer, indexes);
	}
	
	private LLVMGetElementPtrInst(LLVMFunction function, LLVMDataPointer pointer, int[] indexes) throws LLVMException
	{
		if (function == null)
			throw new LLVMException("Parameter \"function\" is null or empty.");
		if (pointer == null)
			throw new LLVMException("Parameter \"pointer\" is null or empty.");
		if (indexes == null)
			throw new LLVMException("Parameter \"indexes\" is null or empty.");
		
		this.pointer = pointer;
		this.indexes = indexes;
		
		// Retrieve base type of pointer type.
		type = pointer.getType().getBaseType();
		
		// Pre-generate result pointer.
		result = LLVMDataPointer.create(function.getNextFreeLocalPointerValueName(), type);
		
		// Register instruction.
		function.registerInst(this);
	}
	
	public LLVMDataPointer getResultPointer()
	{
		return result;
	}
	
	@Override
	public String getInstructionString() throws LLVMException {
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		sb.append(" = getelementptr ");
		sb.append(type.getTypeDefinitionString());
		sb.append(", ");
		sb.append(pointer.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(pointer.getIdentifier());
		
		for (int index : indexes)
		{
			sb.append(", i32 " + index);
		}
		
		
		return sb.toString();
	}
}
