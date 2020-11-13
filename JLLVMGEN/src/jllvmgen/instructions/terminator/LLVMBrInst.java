package jllvmgen.instructions.terminator;

import jllvmgen.LLVMDataValue;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMValueType;

public class LLVMBrInst implements ILLVMBaseInst
{
	private boolean isConditional;
	private LLVMDataValue condition;
	private LLVMLabelType ifTrue;
	private LLVMLabelType ifFalse;
	private LLVMLabelType unconditionalLabel;
	
	public static LLVMBrInst createConditional(LLVMDataValue condition, LLVMLabelType ifTrue, LLVMLabelType ifFalse) throws LLVMException
	{
		return new LLVMBrInst(condition, ifTrue, ifFalse, null);
	}
	
	public static LLVMBrInst createUnconditional(LLVMLabelType label) throws LLVMException
	{
		return new LLVMBrInst(null, null, null, label);
	}
	
	private LLVMBrInst(LLVMDataValue condition,
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
			// Check if condition holds a value/identifier from type i1.
			if (!checkIfCondition(condition))
				throw new LLVMException("Condition(i1) parameter is not a real condition."
						+ "condition type: " + condition.getType().getTypeDefinitionString());
			
			// conditional branch
			isConditional = true;
			
			this.condition = condition;
			this.ifTrue = ifTrue;
			this.ifFalse = ifFalse;
		}
		else
			throw new LLVMException("Unknown branch.");
	}
	
	
	/**
	 * @param condition
	 * @return true if condition is from type i1.
	 */
	private boolean checkIfCondition(LLVMDataValue condition)
	{
		if (condition == null)
			return false;
		
		if (!condition.getType().isValueType())
			return false;
		
		return ((LLVMValueType)condition.getType()).holdsBoolean();
	}

	@Override
	public String getInstructionString() throws LLVMException
	{
		if (isConditional)
			return "br";
		
		
		return "br label " + unconditionalLabel.getName();
	}
}
