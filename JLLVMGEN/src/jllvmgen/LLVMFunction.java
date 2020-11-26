package jllvmgen;

import java.util.ArrayList;

import jllvmgen.enums.LLVMRuntimePreemptionSpecifiers;
import jllvmgen.instructions.ILLVMBaseInst;
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
	
	private ArrayList<LLVMLabelType> labelTypes = new ArrayList<LLVMLabelType>();
	
	
	private int numberOfDefinedLabels = 0;
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
	 * @return next available local data value variable name without any pre- or suffix.
	 */
	public String getNextFreeLocalVariableValueName()
	{
		return "v" + (numberOfDefinedValueVars++);
	}
	
	/**
	 * @return next available local pointer variable name without any pre- or suffix.
	 */
	public String getNextFreeLocalPointerValueName()
	{
		return "p" + (numberOfDefinedPointerVars++);
	}
	
	/**
	 * @return next available label type name without any pre- or suffix.
	 */
	public String getNextAvailableLabelTypeName()
	{
		return "label" + (numberOfDefinedLabels++);
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
		if (labelTypes.size() == 0)
			instructions.add(instruction);
		else
			// Registers instruction to the last label section.
			labelTypes.get(labelTypes.size() - 1)
				.registerInstruction(instruction);
	}
	
	public void registerGlobalVariable(LLVMDataPointer variable) throws LLVMException
	{
		module.registerGlobalVariable(variable);
	}
	
	public void registerConstant(LLVMDataValue constant) throws LLVMException
	{
		module.registerConstant(constant);
	}
	
	public void registerLabelType(LLVMLabelType labelType) throws LLVMException
	{
		if (labelType == null)
			throw new LLVMException("Parameter \"labelSection\" is null or empty.");
		
		for (var label : labelTypes)
		{
			if (label.getIdentifier().equals(labelType.getIdentifier()))
				throw new LLVMException("Duplicate label type identifiers are not allowed. Identifier: " + labelType.getIdentifier());
		}
		
		labelTypes.add(labelType);
	}
	
	public void registerLabelAfterOtherLabelType(LLVMLabelType labelType, LLVMLabelType parentLabelType) throws LLVMException
	{
		if (labelType == null)
			throw new LLVMException("Parameter \"labelSection\" is null or empty.");
		
		Integer indexOfParentLabelType = null;
		
		for (int i = 0; i < labelTypes.size(); ++i)
		{
			if (labelTypes.get(i).getIdentifier().equals(labelType.getIdentifier()))
				throw new LLVMException("Duplicate label type identifiers are not allowed. Identifier: " + labelType.getIdentifier());
			
			if (parentLabelType.equals(labelTypes.get(i)))
			{
				indexOfParentLabelType = i;
			}
		}
		
		labelTypes.add(indexOfParentLabelType, labelType);
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
		
		for (var labelType : labelTypes)
		{
			sb.append(labelType.getTypeDefinitionString());
			sb.append('\n');
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	public void printDefinition() throws LLVMException
	{
		System.out.println(getDefinitionString());
	}
}