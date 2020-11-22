package jllvmgen;

import java.util.ArrayList;

import jllvmgen.enums.LLVMRuntimePreemptionSpecifiers;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.internal.LLVMLabelSection;
import jllvmgen.misc.KeyValueList;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMLabelType;

public class LLVMFunction
{
	private LLVMModule module;
	
	private String name;
	private ILLVMMemoryType returnType;
	private KeyValueList<ILLVMMemoryType, String> parameters;
	private ArrayList<ILLVMBaseInst> instructions = new ArrayList<ILLVMBaseInst>();
	private LLVMRuntimePreemptionSpecifiers runtimePreemptionSpecifier;
	
	private ArrayList<LLVMLabelSection> labelSections;
	
	
	private int numberOfDefinedValueVars = 0;
	private int numberOfDefinedPointerVars = 0;
	
	
	public static LLVMFunction createWithoutParameters(LLVMModule module, String name, ILLVMMemoryType returnType) throws LLVMException
	{
		return new LLVMFunction(module, name, returnType, null);
	}
	
	public static LLVMFunction createWithParameters(LLVMModule module, String name, ILLVMMemoryType returnType, KeyValueList<ILLVMMemoryType, String> parameters) throws LLVMException
	{
		return new LLVMFunction(module, name, returnType, parameters);
	}
	
//	public static LLVMFunction createVoidWithoutParameters(String name)
//	{
//		return new LLVMFunction(name, new LLVMPrimitiveType(LLVMPrimitiveTypes._void), null);
//	}
//	
//	public static LLVMFunction createVoidWithParameters(String name, KeyValueList<ILLVMMemoryType, String> parameters)
//	{
//		return new LLVMFunction(name, new LLVMPrimitiveType(LLVMPrimitiveTypes._void), parameters);
//	}
	
	private LLVMFunction(LLVMModule module, String name, ILLVMMemoryType returnType, KeyValueList<ILLVMMemoryType, String> parameters) throws LLVMException
	{
		if (module == null)
			throw new LLVMException("Parameter \"module\" is null or empty.");
		if (name == null)
			throw new LLVMException("Parameter \"name\" is null or empty.");
		if (returnType == null)
			throw new LLVMException("Parameter \"returnType\" is null or empty.");
		
		this.module = module;
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		
		if (module.autoRegisterFunctions())
			module.registerFunction(this);
	}
	
	/**
	 * @return next free local variable name without %-prefix.
	 */
	public String getNextFreeLocalVariableValueName()
	{
		return "v" + (numberOfDefinedValueVars++);
	}
	
	/**
	 * @return next free local pointer variable name without %-prefix.
	 */
	public String getNextFreeLocalPointerValueName()
	{
		return "p" + (numberOfDefinedPointerVars++);
	}
	
	public boolean autoRegisterInstructions()
	{
		return module.autoRegisterInstructions();
	}
	
	public boolean autoRegisterGlobalVars()
	{
		return module.autoRegisterGlobalVars();
	}
	
	public void registerInstruction(ILLVMBaseInst instruction) throws LLVMException
	{
		if (instruction == null)
			throw new LLVMException("Parameter \"instruction\" is null or empty.");
		
		// Check if any label section is available, if not add it to the default instruction list.
		if (labelSections.size() == 0)
			instructions.add(instruction);
		else
			// Registers instruction to the last label section.
			labelSections.get(labelSections.size() - 1)
				.registerInstruction(instruction);
	}
	
	public void registersInstructionIntoLabelSection(LLVMLabelType labelType, ILLVMBaseInst instruction) throws LLVMException
	{
		if (labelType == null)
			throw new LLVMException("Parameter \"labelType\" is null or empty.");
		
		// Search for label section and registers instruction to it.
		for (var section : labelSections)
		{
			if (section.getLabelType().equals(labelType))
			{
				section.registerInstruction(instruction);
			}
		}
	}
	
	public void registerGlobalVariable(LLVMDataPointer variable) throws LLVMException
	{
		module.registerGlobalVariable(variable);
	}
	
	public void registerConstant(LLVMDataValue constant) throws LLVMException
	{
		module.registerConstant(constant);
	}
	
	
	/**
	 * Registers label and appends a new label section on the end of the function.
	 * @param label
	 * @throws LLVMException
	 */
	public void registerLabel(LLVMLabelType label) throws LLVMException
	{
		if (label == null)
			throw new LLVMException("Parameter \"label\" is null or empty.");
		
		labelSections.add(new LLVMLabelSection(label));
	}
	
	
	public void printDefinition() throws LLVMException
	{
		System.out.println(getDefinitionString());
	}
	
	public String getDefinitionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder("define ");
		sb.append(returnType.getTypeDefinitionString() + ' ');
		sb.append("@" + name);
		sb.append("(");
		
		sb.append(") {\n");
		
		for (ILLVMBaseInst inst : instructions)
		{
			sb.append('\t');
			sb.append(inst.getInstructionString());
			sb.append('\n');
		}
		
		for (var labelSection : labelSections)
		{
			sb.append(labelSection.getDefintionString());
		}
		
		sb.append("}");
		
		return sb.toString();
	}
}