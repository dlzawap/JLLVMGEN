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
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMPackedStruct)
		{
			LLVMPackedStruct other = (LLVMPackedStruct)obj;
			if (identifier.equals(other.identifier))
				return super.equals(obj);
		}
		
		return false;
	}
}
