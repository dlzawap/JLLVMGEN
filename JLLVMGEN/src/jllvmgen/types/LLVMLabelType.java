package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMLabelType implements ILLVMCodeCreationFunctionality
{
	private String identifier;
	
	public void setName(String identifier) throws LLVMException
	{
		if (identifier == null)
			throw new LLVMException("Parameter \"name\" is null or empty.");
		
		this.identifier = identifier;
	}

	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		return identifier + ":";
	}
	
	public String getIdentifier()
	{
		return "%" + identifier;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMLabelType)
		{
			return ((LLVMLabelType)obj).identifier.equals(this.identifier);
		}
		return false;
	}
}
