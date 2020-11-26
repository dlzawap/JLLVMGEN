package jllvmgen.instructions.terminator;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMValueType;

/**
 * The ‘br’ instruction is used to cause control flow to transfer to a different basic block in the current function.
 * There are two forms of this instruction, corresponding to a conditional branch and an unconditional branch.
 */
public class LLVMBrInst
{
	public class LLVMConditionalBr implements ILLVMBaseInst
	{
		private LLVMDataValue condition;
		private LLVMLabelType ifTrueLabelType;
		private LLVMLabelType ifFalseLabelType;
		
		private LLVMConditionalBr(
				LLVMFunction fn,
				LLVMDataValue condition,
				LLVMLabelType ifTrueLabelType, LLVMLabelType ifFalseLabelType) throws LLVMException
		{
			if (fn == null)
				throw new LLVMException("Parameter \"fn\" is null or empty.");
			if (condition == null)
				throw new LLVMException("Parameter \"condition\" is null or empty.");
			
			// Throw exception if condition is not a real boolean.
			if (!condition.isValueVariable() &&
			    !((LLVMValueType)condition.getType()).holdsBoolean())
			{
				throw new LLVMException("Parameter \"condition\" must be from type boolean. But it's actually: "
						+ condition.getType().getTypeDefinitionString());
			}
			
			this.condition = condition;
			
			if (ifTrueLabelType == null)
				this.ifTrueLabelType = LLVMLabelType.create(fn, fn.getNextAvailableLabelTypeName(), false);
			else
				this.ifTrueLabelType = ifFalseLabelType;
			
			if (ifFalseLabelType == null)
				this.ifFalseLabelType = LLVMLabelType.create(fn, fn.getNextAvailableLabelTypeName(), false);
			else
				this.ifFalseLabelType = ifFalseLabelType;
		}
		
		public LLVMLabelType getIfTrueLabelType()
		{
			return ifTrueLabelType;
		}
		
		public LLVMLabelType getIfFalseLabelType()
		{
			return ifFalseLabelType;
		}
		
		@Override
		public String getInstructionString() throws LLVMException
		{
			StringBuilder sb = new StringBuilder("br i1 ");
			sb.append(condition.getIdentifierOrValue());
			sb.append(", label ");
			sb.append(ifTrueLabelType.getIdentifier());
			sb.append(", label ");
			sb.append(ifFalseLabelType.getIdentifier());
			
			return sb.toString();
		}
	}
	
	public class LLVMUnconditionalBr implements ILLVMBaseInst
	{
		private LLVMLabelType destination;
		
		private LLVMUnconditionalBr(LLVMLabelType destination) throws LLVMException
		{
			if (destination == null)
				throw new LLVMException("Parameter \"destination\" is null or empty.");
			
			this.destination = destination;
		}
		
		@Override
		public String getInstructionString() throws LLVMException
		{
			return "br label " + destination.getIdentifier();
		}
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMConditionalBr createConditional(LLVMFunction fn, LLVMDataValue condition) throws LLVMException
	{
		var instruction = new LLVMBrInst().new LLVMConditionalBr(fn, condition, null, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		fn.registerLabelType(instruction.getIfTrueLabelType());
		fn.registerLabelType(instruction.getIfFalseLabelType());
		
		return instruction;
	}
	
	public static LLVMConditionalBr createConditional(LLVMFunction fn, LLVMDataValue condition, LLVMLabelType ifTrueLabelType, LLVMLabelType ifFalseLabelType) throws LLVMException
	{
		var instruction = new LLVMBrInst().new LLVMConditionalBr(fn, condition, ifTrueLabelType, ifFalseLabelType);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMUnconditionalBr createUnconditional(LLVMFunction fn, LLVMLabelType destination) throws LLVMException
	{
		var instruction = new LLVMBrInst().new LLVMUnconditionalBr(destination);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMConditionalBr createConditional(
			LLVMFunction fn,
			LLVMLabelType parentLabelType,
			LLVMDataValue condition) throws LLVMException
	{
		var instruction = new LLVMBrInst().new LLVMConditionalBr(fn, condition, null, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);

		fn.registerLabelType(instruction.getIfTrueLabelType());
		fn.registerLabelType(instruction.getIfFalseLabelType());
		
		return instruction;
	}
	
	public static LLVMConditionalBr createConditional(
			LLVMFunction fn,
			LLVMLabelType parentLabelType,
			LLVMDataValue condition,
			LLVMLabelType ifTrue, LLVMLabelType ifFalse) throws LLVMException
	{
		var instruction = new LLVMBrInst().new LLVMConditionalBr(fn, condition, ifTrue, ifFalse);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMUnconditionalBr createUnconditional(LLVMFunction fn, LLVMLabelType parentLabelType, LLVMLabelType destination) throws LLVMException
	{
		var instruction = new LLVMBrInst().new LLVMUnconditionalBr(destination);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
