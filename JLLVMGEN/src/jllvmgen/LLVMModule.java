package jllvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMPointerType;

public class LLVMModule
{
	private LLVMModuleSettings moduleSettings = new LLVMModuleSettings();

	private String sourceFilename;
	private ArrayList<LLVMFunction> functions = new ArrayList<LLVMFunction>();
	
	private ArrayList<ILLVMVariableType> constants  = new ArrayList<ILLVMVariableType>();
	private ArrayList<ILLVMVariableType> globalVariables = new ArrayList<ILLVMVariableType>();

	public static LLVMModule create(String sourceFilename) {
		return new LLVMModule(sourceFilename);
	}

	public LLVMModule(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public void registerFunction(LLVMFunction fn) {
		functions.add(fn);
	}

	public void registerGlobalVariable(LLVMDataPointer pointer) throws LLVMException
	{
		if (pointer == null)
			throw new LLVMException("Parameter \"pointer\" is null or empty.");
		if (!pointer.isConstant())
			throw new LLVMException("Tried to register an invalid global variable.");
		
		globalVariables.add(pointer);
	}
	
	public void registerConstant(LLVMDataValue constant) throws LLVMException
	{
		if (constant == null)
			throw new LLVMException("Parameter \"constant\" is null or empty.");
		if (!constant.isConstant())
			throw new LLVMException("Tried to register a non constant value as a constant.");
		
		constants.add(constant);
	}
	
	public boolean autoRegisterFunctions()
	{
		return moduleSettings.autoRegisterFunctions;
	}

	public boolean autoRegisterInstructions() {
		return moduleSettings.autoRegisterInst;
	}

	public boolean autoRegisterGlobalVars() {
		return moduleSettings.autoRegisterGlobalVars;
	}

	public String getModuleDefinitionString() throws LLVMException {
		StringBuilder sb = new StringBuilder();

		// Append source filename.
		sb.append("source_filename = ");
		sb.append('\"' + sourceFilename + "\"\n\n");

		// Append target data layout.

		// Append constants.

		// Append functions.
		for (LLVMFunction fn : functions) {
			sb.append(fn.getDefinitionString());
			sb.append('\n');
		}

		// Define external functions.

		// Define attribute lists.

		return sb.toString();
	}

	public void writeCodeToFile(String filepath) throws LLVMException
	{
		try 
		{
			// Check if file is already existing, if not create a one.
			File file = new File(filepath);
			
			if (!file.exists())
		    {
				if (!file.createNewFile())
					throw new LLVMException("Failed to create new file: Filepath: " + filepath);
		    }
		      
		    FileWriter fileWriter = new FileWriter(filepath);
		    fileWriter.write(getModuleDefinitionString());
		    fileWriter.close();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
