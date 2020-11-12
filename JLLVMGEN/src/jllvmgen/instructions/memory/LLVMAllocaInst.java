package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.KeyValueList;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

/**
 * @author Manuel
 * Allocates memory on the stack frame of the currently executing function,
 * to be automatically released when this function returns to its caller.
 * %ptr = alloca i32, i32 4, align 1024
 */
public class LLVMAllocaInst implements ILLVMBaseInst
{
	private LLVMDataPointer result;
	private ILLVMMemoryType type;
	private KeyValueList<ILLVMMemoryType, Integer> subTypes;
	private Integer align;
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMDataPointer result) throws LLVMException
	{
		return new LLVMAllocaInst(fn, result, null, null);
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMDataPointer result,
			Integer align) throws LLVMException
	{
		return new LLVMAllocaInst(fn, result, null, align);
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMDataPointer result,
			KeyValueList<ILLVMMemoryType, Integer> subTypes) throws LLVMException
	{
		return new LLVMAllocaInst(fn, result, subTypes, null);
	}
	
	public static LLVMAllocaInst create(LLVMFunction fn, LLVMDataPointer result, 
			KeyValueList<ILLVMMemoryType, Integer> subTypes, Integer align) throws LLVMException
	{
		return new LLVMAllocaInst(fn, result, subTypes, align);
	}
	
	
	public LLVMAllocaInst(LLVMFunction fn, LLVMDataPointer result,
			KeyValueList<ILLVMMemoryType, Integer> subTypes, Integer align) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (result == null)
			throw new LLVMException("Parameter \"result\" is null or empty.");
		if (align != null && align < 1 << 29)
			throw new LLVMException("Alignment exceeded 1 << 29 size limit. Align: " + align);
		
		this.result = result;
		this.type = result.getType().getBaseType();
		this.subTypes = subTypes;
		this.align = align;
		
		// Register instruction.
		fn.registerInst(this);
	}
	
	
	@Override
	public String getInstructionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		sb.append(" = alloca ");
		sb.append(type.getTypeDefinitionString());
		
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
}
