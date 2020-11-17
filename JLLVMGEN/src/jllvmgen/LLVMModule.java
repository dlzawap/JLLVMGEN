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

	private ArrayList<LLVMDataValue> globalDataVars;
	private ArrayList<LLVMPointerType> globalPointerVars;

	public static LLVMModule create(String sourceFilename) {
		return new LLVMModule(sourceFilename);
	}

	public LLVMModule(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public void registerFunction(LLVMFunction fn) {
		functions.add(fn);
	}

	public void registerGlobalVar() throws LLVMException {

	}

	public void registerConst() throws LLVMException {

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
