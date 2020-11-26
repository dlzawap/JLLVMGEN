package jllvmgen.types;

import java.util.ArrayList;

import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;

public class LLVMLabelType implements ILLVMCodeCreationFunctionality
{
	private String identifier;
	private ArrayList<ILLVMBaseInst> instructions = new ArrayList<ILLVMBaseInst>();
	
	private LLVMLabelType(String identifier) throws LLVMException
	{
		setIdentifier(identifier);
	}
	
	private void setIdentifier(String identifier) throws LLVMException
	{
		if (identifier == null)
			throw new LLVMException("Parameter \"identifier\" is null or empty.");
		
		this.identifier = identifier;
	}

	public void registerInstruction(ILLVMBaseInst instruction) throws LLVMException
	{
		if (instruction == null)
			throw new LLVMException("Parameter \"instruction\" is null or empty.");
		
		instructions.add(instruction);
	}
	
	public String getIdentifier()
	{
		return "%" + identifier;
	}
	
	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(identifier).append(":");
		
		for (var inst : instructions)
		{
			sb.append("\n\t").append(inst.getInstructionString());
		}
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMLabelType create(LLVMFunction fn, String identifier, boolean autoRegisterLabelType) throws LLVMException
	{
		var labelType = new LLVMLabelType(identifier);
		
		// Register labelType if automatic registration is enabled.
		if (autoRegisterLabelType)
			fn.registerLabelType(labelType);
		
		return labelType;
	}
	
	public static LLVMLabelType createAfterOtherLabel(LLVMFunction fn, String identifier, LLVMLabelType parentLabelType, boolean autoRegisterLabelType) throws LLVMException
	{
		var labelType = new LLVMLabelType(identifier);
		
		// Register labelType if automatic registration is enabled.
		if (autoRegisterLabelType)
			fn.registerLabelAfterOtherLabelType(labelType, parentLabelType);
		
		return labelType;
	}
	
	
	
	
	/*
	 * End of factory functions.
	 */
}
