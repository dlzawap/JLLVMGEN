package jllvmgen.instructions.terminator;

import jllvmgen.LLVMDataValue;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;

public class LLVMRetInst implements ILLVMBaseInst
{
	private LLVMDataValue value;
	
	public LLVMRetInst createRetVoid()
	{
		return new LLVMRetInst(null);
	}
	
	public LLVMRetInst createRetValue(LLVMDataValue value)
	{
		return new LLVMRetInst(value);
	}
	
	
	/**
	 * @param value on null, a void return will be created.
	 */
	public LLVMRetInst(LLVMDataValue value)
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

}
