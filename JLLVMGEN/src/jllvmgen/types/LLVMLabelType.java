package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMLabelType implements ILLVMCodeCreationFunctionality
{
	private String name;
	
	public void setName(String name) throws LLVMException
	{
		if (name == null)
			throw new LLVMException("Parameter \"name\" is null or empty.");
		
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public String getTypeDefinitionString() throws LLVMException {
		return name + ":";
	}
	
	// definition with :
	// but value starts with %
}
