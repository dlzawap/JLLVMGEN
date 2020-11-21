package jllvmgen.internal;

import java.util.ArrayList;

import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;

public class LLVMLabelSection
{
	private LLVMLabelType label;
	private ArrayList<ILLVMBaseInst> instructions
		= new ArrayList<ILLVMBaseInst>();
	
	public LLVMLabelSection(LLVMLabelType label) throws LLVMException
	{
		if (label == null)
			throw new LLVMException("Parameter \"label\" is null or empty.");
		
		this.label = label;
	}
	
	public void registerInstruction(ILLVMBaseInst instruction) throws LLVMException
	{
		if (instruction == null)
			throw new LLVMException("Parameter \"instruction\" is null or empty");
		
		instructions.add(instruction);
	}
	
	public String getDefintionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(label.getTypeDefinitionString());
		sb.append('\n');
		
		for (var inst : instructions)
		{
			sb.append('\t');
			sb.append(inst.getInstructionString());
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	public LLVMLabelType getLabelType()
	{
		return label;
	}
}
