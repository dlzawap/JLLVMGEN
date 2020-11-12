package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

/**
 * @author Manuel
 * Holds a value data type and suitable value.
 */
public class LLVMDataValue
{
	// Holds identifier without %-prefix.
	private String identifier;
	// Holds data value type. (Primitive type, struct, vector, array)
	private ILLVMMemoryType type;
	// Holds value (integer, float or constructor for arrays/vectors/structs)
	private String value;
	
	public static LLVMDataValue create(String identifier, ILLVMMemoryType type) throws LLVMException
	{
		return new LLVMDataValue(identifier, type, null);
	}
	
	public static LLVMDataValue create(String identifier, ILLVMMemoryType type, String value) throws LLVMException 
	{
		return new LLVMDataValue(identifier, type, value);
	}
	
	
	/**
	 * @param identifier without %-prefix.
	 * @param type must not be a pointer.
	 * @param value can be null.
	 * @throws LLVMException
	 */
	public LLVMDataValue(String identifier, ILLVMMemoryType type, String value) throws LLVMException
	{
		if (identifier == null)
			throw new LLVMException("Parameter \"identifier\" is null or empty.");
		if (type == null)
			throw new LLVMException("Parameter \"type\" is null or empty.");
		if (type.isPointerType())
			throw new LLVMException("A pointer type can't be held inside a value data type.");
		
		setIdentifier(identifier);
		
		this.type = type;
		this.value = value;
	}
	
	public String getIdentifierOrValue()
	{
		if (value != null)
			return value;
		else
			return "%" + identifier;
	}
	
	public String getIdentifierWithoutPrefix()
	{
		return identifier;
	}
	
	public String getIdentifier()
	{
		return "%" + identifier;
	}
	
	public void setIdentifier(String identifier) throws LLVMException
	{
		if (identifier == null)
			throw new LLVMException("Parameter \"identifier\" is null or empty.");
		
		// Check if identifier already contains %-prefix and if available remove it.
		if (identifier.startsWith("%"))
			identifier = identifier.substring(1);
		
		this.identifier = identifier;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public ILLVMMemoryType getType()
	{
		return type;
	}
}
