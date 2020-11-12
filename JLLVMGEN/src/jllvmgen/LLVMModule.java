package jllvmgen;

import java.util.ArrayList;

import jllvmgen.misc.LLVMException;

public class LLVMModule
{
	private String sourceFilename;
	private ArrayList<LLVMFunction> functions
		= new ArrayList<LLVMFunction>();
	
	
	public static LLVMModule create(String sourceFilename)
	{
		return new LLVMModule(sourceFilename);
	}
	
	public LLVMModule(String sourceFilename)
	{
		this.sourceFilename = sourceFilename;
	}
	
	public void registerFn(LLVMFunction fn)
	{
		functions.add(fn);
	}
	
	public String foo() throws LLVMException
	{
		StringBuilder sb = new StringBuilder();
		
		// Append source filename.
		sb.append("source_filename = ");
		sb.append('\'' + sourceFilename + "\'\n\n");
		
		// Append target data layout.
		
		// Append constants.
		
		// Append functions.
		for (LLVMFunction fn : functions)
		{
			sb.append(fn.getDefinitionString());
			sb.append('\n');
		}
		
		// Define external functions.
		
		// Define attribute lists.
		
		return sb.toString();
	}
	
	/**
	 * Don't use this function manually.
	 * @param instruction
	 * @throws LLVMException
	 */
	public void registerGlobalVar() throws LLVMException
	{
		
	}
	
	/**
	 * Don't use this function manually.
	 * @param instruction
	 * @throws LLVMException
	 */
	public void registerConst() throws LLVMException
	{
		
	}
	
	public void writeCodeToFile(String filepath)
	{
		
	}
}
