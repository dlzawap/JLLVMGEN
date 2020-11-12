package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMPackedStruct extends LLVMBaseStruct
{
	private String identifier;
	
	@Override
	public boolean isIdentifiedStruct() throws LLVMException
	{
		return false;
	}
	
	@Override
	public boolean isLiteralStruct() throws LLVMException
	{
		return false;
	}
	
	@Override
	public boolean isPackedStruct() throws LLVMException
	{
		return true;
	}
	
	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		return "type <" + super.getTypeDefinitionString() + ">";
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
}