package jllvmgen.instructions.memory;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMDataPointer;
import jllvmgen.LLVMDataValue;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMValueType;

/**
 * The 'store' instruction is used to write to memory. (via pointer)
 */
public class LLVMStoreInst  implements ILLVMBaseInst {

	private LLVMDataValue value;
	private LLVMDataPointer pointer;
	
	private Integer align;
	
	
	public static LLVMStoreInst createInst(LLVMFunction fn, LLVMDataValue value, LLVMDataPointer pointer) throws LLVMException
	{
		return new LLVMStoreInst(fn, value, pointer, null);
	}
	
	public static LLVMStoreInst createInst(LLVMFunction fn, LLVMDataValue value, LLVMDataPointer pointer, int align) throws LLVMException
	{
		return new LLVMStoreInst(fn, value, pointer, align);
	}
	
	/**
	 * @param value should be a constant or local variable.
	 * @param pointer must be a pointer type of value.
	 * @param align can be null, if not its value should be below 1 << 29 and above 1
	 * @throws LLVMException
	 */
	private LLVMStoreInst(LLVMFunction fn, LLVMDataValue value, LLVMDataPointer pointer, Integer align) throws LLVMException
	{
		if (fn == null)
			throw new LLVMException("Parameter \"fn\" is null or empty.");
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (!value.getType().isValueType() && ((LLVMValueType)value.getType()).isPrimitiveType())
			throw new LLVMException("Store instruction are only allowed on primitive types");
		if (value.getValue() == null)
			throw new LLVMException("Value is null or not defined.");
		if (pointer == null)
			throw new LLVMException("Parameter \"pointer\" is null or empty");
		if (!pointer.getType().isPointerType())
			throw new LLVMException("Parameter \"pointer\" isn't a valid pointer type.");
		if (!pointer.getType().getTypeDefinitionString().startsWith(value.getType().getTypeDefinitionString()))
			throw new LLVMException("Value type and pointer type are not equal.");
		if (align != null)
			if (align > 1 << 29 || align < 1)
				throw new LLVMException("Align value should be above 1 and below 1 << 29."
						+ "Its actual value : " + align);
		
		this.value = value;
		this.pointer = pointer;
		this.align = align;
		
		// Register instruction
		if (fn.autoRegisterInstructions())
			fn.registerInst(this);
	}
	
	/**
	 * store i32 99, i32* %p1
	 */
	@Override
	public String getInstructionString() throws LLVMException {
		StringBuilder sb = new StringBuilder("store ");
		
		// "i32 3"
		sb.append(value.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(value.getValue());

		// ", i32* %p0"
		sb.append(", ");
		sb.append(pointer.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(pointer.getIdentifier());
		
		if (align != null)
		{
			// ", align 1024"
			sb.append(", align ");
			sb.append(align);
		}
		
		
		return sb.toString();
	}
}
