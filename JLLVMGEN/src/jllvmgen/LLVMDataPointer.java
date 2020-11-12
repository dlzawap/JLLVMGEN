package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMPointerType;

public class LLVMDataPointer
{
	private String identifier;
	private LLVMPointerType type;
	
	
	public static LLVMDataPointer create(String name, LLVMPointerType type) throws LLVMException
	{
		return new LLVMDataPointer(name, type);
	}
	
	public static LLVMDataPointer create(String name, ILLVMMemoryType type) throws LLVMException
	{
		if (type instanceof LLVMPointerType)
		{
			return new LLVMDataPointer(name, (LLVMPointerType)type);
		}

		throw new LLVMException("Parameter \"type\" is not a valid pointer data type.");
	}
	
	public LLVMDataPointer(String name, LLVMPointerType type) throws LLVMException
	{
		if (name == null)
			throw new LLVMException("Parameter \"name\" is null or empty.");
		if (type == null)
			throw new LLVMException("Parameter \"type\" is null or empty.");
		
		this.identifier = name;
		this.type = type;
	}
	
	public String getIdentifier()
	{
		return "%" + identifier;
	}
	
	public LLVMPointerType getType()
	{
		return type;
	}
}
