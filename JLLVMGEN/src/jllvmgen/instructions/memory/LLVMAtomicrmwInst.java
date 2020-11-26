package jllvmgen.instructions.memory;

import jllvmgen.LLVMDataPointer;
import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.enums.LLVMAtomicMemoryOrderingConstraints;
import jllvmgen.enums.LLVMAtomicrmwOperations;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;

public class LLVMAtomicrmwInst implements ILLVMBaseInst
{
	private LLVMDataValue result;
	private LLVMDataPointer pointer;
	private LLVMDataValue value;
	private LLVMAtomicrmwOperations operation;
	private boolean isVolatile;
	private LLVMAtomicMemoryOrderingConstraints ordering;

	private LLVMAtomicrmwInst(LLVMFunction fn,
			LLVMDataPointer pointer,
			LLVMDataValue value, LLVMAtomicrmwOperations operation,
			boolean isVolatile, LLVMAtomicMemoryOrderingConstraints ordering) throws LLVMException
	{
		if (pointer == null)
			throw new LLVMException("Parameter \"pointer\" is null or empty.");
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (operation == null)
			throw new LLVMException("Parameter \"operation\" is null or empty.");
		if (ordering == null)
			throw new LLVMException("Parameter \"ordering\" is null or empty.");
		
		this.pointer = pointer;
		this.value = value;
		this.operation = operation;
		this.isVolatile = isVolatile;
		this.ordering = ordering;
		
		result = LLVMDataValue.createLocalVariable(
				 fn.getNextFreeLocalVariableValueName(),
				 value.getType());
	}
	
	public LLVMDataValue getResult()
	{
		return result;
	}
	
	@Override
	public String getInstructionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(result.getIdentifier());
		sb.append(" = atomicrmw ");
		
		if (isVolatile)
			sb.append("volatile ");
		
		sb.append(operation.toString());
		sb.append(' ');
		sb.append(pointer.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(pointer.getIdentifier());
		sb.append(", ");
		sb.append(value.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(value.getIdentifierOrValue());
		
		sb.append(ordering.toString());
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMAtomicrmwInst create(LLVMFunction fn,
			LLVMDataPointer pointer,
			LLVMDataValue value, LLVMAtomicrmwOperations operation,
			boolean isVolatile, LLVMAtomicMemoryOrderingConstraints ordering) throws LLVMException
	{
		var instruction = new LLVMAtomicrmwInst(fn, pointer, value, operation, isVolatile, ordering);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMAtomicrmwInst create(LLVMFunction fn,
			LLVMLabelType parentLabelType, LLVMDataPointer pointer,
			LLVMDataValue value, LLVMAtomicrmwOperations operation,
			boolean isVolatile, LLVMAtomicMemoryOrderingConstraints ordering) throws LLVMException
	{
		var instruction = new LLVMAtomicrmwInst(fn, pointer, value, operation, isVolatile, ordering);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
