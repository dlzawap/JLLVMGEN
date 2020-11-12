package jllvmgen;

import java.util.ArrayList;

import jllvmgen.enums.LLVMRuntimePreemptionSpecifiers;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.KeyValueList;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

public class LLVMFunction {
	private String name;
	private ILLVMMemoryType returnType;
	private KeyValueList<ILLVMMemoryType, String> parameters;
	private ArrayList<ILLVMBaseInst> instructions = new ArrayList<ILLVMBaseInst>();
	private LLVMRuntimePreemptionSpecifiers runtimePreemptionSpecifier;
	
	private int numberOfDefinedValueVars = 0;
	private int numberOfDefinedPointerVars = 0;
	
	
	public static LLVMFunction createWithoutParameters(String name, ILLVMMemoryType returnType)
	{
		return new LLVMFunction(name, returnType, null);
	}
	
	public static LLVMFunction createWithParameters(String name, ILLVMMemoryType returnType, KeyValueList<ILLVMMemoryType, String> parameters)
	{
		return new LLVMFunction(name, returnType, parameters);
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
	
	private LLVMFunction(String name, ILLVMMemoryType returnType, KeyValueList<ILLVMMemoryType, String> parameters)
	{
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
	}
	
	/**
	 * @return Next free local variable name without %-prefix.
	 */
	public String getNextFreeLocalVariableValueName()
	{
		return "v" + (numberOfDefinedValueVars++);
	}
	public String getNextFreeLocalPointerValueName()
	{
		return "p" + (numberOfDefinedPointerVars++);
	}
	
	
	
	/**
	 * Don't use this function manually.
	 * @param instruction
	 * @throws LLVMException
	 */
	public void registerInst(ILLVMBaseInst instruction) throws LLVMException
	{
		if (instruction == null)
			throw new LLVMException("Parameter \"instruction\" is null or empty.");
		
		instructions.add(instruction);
	}
	
	
	public void printDefinition() throws LLVMException {
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
		
		sb.append("}");
		
		return sb.toString();
	}
	
	
	public boolean autoRegisterInstructions()
	{
		// save value in llvm module.
		return true;
	}
}