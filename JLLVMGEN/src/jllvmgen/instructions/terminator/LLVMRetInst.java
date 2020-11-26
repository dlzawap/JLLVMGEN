package jllvmgen.instructions.terminator;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;

public class LLVMRetInst implements ILLVMBaseInst
{
	private LLVMDataValue value;
	
	private LLVMRetInst(LLVMDataValue value)
	{
		this.value = value;
	}

	@Override
	public String getInstructionString() throws LLVMException
	{
		if (value == null)
			return "ret void";
		
		
		return "ret" + value.getType() + " " + value.getValue();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMRetInst createVoid(LLVMFunction fn) throws LLVMException
	{
		var instruction = new LLVMRetInst(null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMRetInst create(LLVMFunction fn, LLVMDataValue value) throws LLVMException
	{
		var instruction = new LLVMRetInst(value);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMRetInst createVoid(LLVMFunction fn, LLVMLabelType parentLabelType) throws LLVMException
	{
		var instruction = new LLVMRetInst(null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMRetInst create(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMDataValue value) throws LLVMException
	{
		var instruction = new LLVMRetInst(value);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
