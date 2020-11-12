package jllvmgen.instructions.terminator;

import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.extension.LLVMCondition;

public class LLVMBrInst implements ILLVMBaseInst
{
	private boolean isConditional;
	private LLVMCondition condition;
	private LLVMLabelType ifTrue;
	private LLVMLabelType ifFalse;
	private LLVMLabelType unconditionalLabel;
	
	public static LLVMBrInst createConditional(LLVMCondition condition, LLVMLabelType ifTrue, LLVMLabelType ifFalse) throws LLVMException
	{
		return new LLVMBrInst(condition, ifTrue, ifFalse, null);
	}
	
	public static LLVMBrInst createUnconditional(LLVMLabelType label) throws LLVMException
	{
		return new LLVMBrInst(null, null, null, label);
	}
	
	private LLVMBrInst(LLVMCondition condition,
			LLVMLabelType ifTrue, LLVMLabelType ifFalse, LLVMLabelType unconditionalLabel) throws LLVMException
	{
		if (condition == null && unconditionalLabel != null)
		{
			// unconditional branch
			isConditional = false;
			this.unconditionalLabel = unconditionalLabel;
		}
		else if (condition != null &&
				 ifTrue != null &&
				 ifFalse != null &&
				 unconditionalLabel == null)
		{
			// conditional branch
			isConditional = true;
			
			this.condition = condition;
			this.ifTrue = ifTrue;
			this.ifFalse = ifFalse;
		}
		else
			throw new LLVMException("Unknown branch.");
	}

	@Override
	public String getInstructionString() throws LLVMException
	{
		if (isConditional)
			return "br";
		
		
		return "br label " + unconditionalLabel.getName();
	}
}
