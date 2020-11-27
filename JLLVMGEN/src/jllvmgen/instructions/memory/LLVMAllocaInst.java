package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.KeyValueList;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMPointerType;

/**
 * Allocates memory on the stack frame of the currently executing function,
 * to be automatically released when this function returns to its caller.
 * %ptr = alloca i32, i32 4, align 1024
 */
public class LLVMAllocaInst implements ILLVMBaseInst
{
	private LLVMDataPointer result;
	private KeyValueList<ILLVMMemoryType, Integer> subTypes;
	private Integer align;
	
	private LLVMAllocaInst(LLVMFunction fn, String identifier, LLVMPointerType resultType,
			KeyValueList<ILLVMMemoryType, Integer> subTypes, Integer align) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (identifier == null)
			throw new LLVMException("Parameter\"identifier\" is null or empty.");
		if (resultType == null)
			throw new LLVMException("Parameter \"resultType\" is null or empty.");
		if (align != null && align > 1 << 29)
			throw new LLVMException("Alignment exceeded 1 << 29 size limit. Align: " + align);
		
		this.subTypes = subTypes;
		this.align = align;
		
		// Pre-generate result pointer.
		result = LLVMDataPointer.createLocalVariable(identifier, resultType);
	}
	
	public LLVMDataPointer getResult()
	{
		return result;
	}
	
	@Override
	public String getInstructionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		sb.append(" = alloca ");
		sb.append(result.getType().getBaseType().getTypeDefinitionString());
		
		// Append defined sub types.
		if (subTypes != null)
		{
			for (int i = 0; i < subTypes.getCount(); ++i)
			{
				sb.append(", ");
				sb.append(subTypes.keyAt(i).getTypeDefinitionString());
				sb.append(' ');
				sb.append(subTypes.valueAt(i));
			}
		}
		
		// Add if defined an align size.
		if (align != null)
		{
			sb.append(", align ");
			sb.append(align);
		}
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMAllocaInst create(LLVMFunction fn, String identifier, LLVMPointerType resultType) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, null, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, String identifier, LLVMPointerType resultType,
			Integer align) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, null, align);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, String identifier, LLVMPointerType resultType,
			KeyValueList<ILLVMMemoryType, Integer> subTypes) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, subTypes, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, String identifier, LLVMPointerType resultType, 
			KeyValueList<ILLVMMemoryType, Integer> subTypes, Integer align) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, subTypes, align);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMLabelType parentLabelType, String identifier, LLVMPointerType resultType) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, null, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMLabelType parentLabelType, String identifier, LLVMPointerType resultType,
			Integer align) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, null, align);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMLabelType parentLabelType, String identifier, LLVMPointerType resultType,
			KeyValueList<ILLVMMemoryType, Integer> subTypes) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, subTypes, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMLabelType parentLabelType, String identifier, LLVMPointerType resultType, 
			KeyValueList<ILLVMMemoryType, Integer> subTypes, Integer align) throws LLVMException
	{
		var instruction = new LLVMAllocaInst(fn, identifier, resultType, subTypes, align);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
