package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMLabelType;

public class LLVMGetElementPtrInst  implements ILLVMBaseInst
{
	private LLVMDataPointer result;
	private ILLVMMemoryType type;
	private LLVMDataPointer pointer;
	private int[] indexes;
	
	private LLVMGetElementPtrInst(LLVMFunction fn, LLVMDataPointer pointer, int[] indexes) throws LLVMException
	{
		if (fn == null)
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
		result = LLVMDataPointer.createLocalVariable(fn.getNextFreeLocalPointerValueName(), type);
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
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMGetElementPtrInst create(LLVMFunction fn, LLVMDataPointer pointer, int index) throws LLVMException
	{
		var instruction = new LLVMGetElementPtrInst(fn, pointer, new int[] { index });
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMGetElementPtrInst create(LLVMFunction fn, LLVMDataPointer pointer, int[] indexes) throws LLVMException
	{
		var instruction = new LLVMGetElementPtrInst(fn, pointer, indexes);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMGetElementPtrInst create(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMDataPointer pointer, int index) throws LLVMException
	{
		var instruction = new LLVMGetElementPtrInst(fn, pointer, new int[] { index });
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMGetElementPtrInst create(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMDataPointer pointer, int[] indexes) throws LLVMException
	{
		var instruction = new LLVMGetElementPtrInst(fn, pointer, indexes);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
