package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

/**
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
	private boolean isConstant;
	
	public static LLVMDataValue create(String identifier, ILLVMMemoryType type) throws LLVMException
	{
		return new LLVMDataValue(identifier, type, null, false);
	}
	
	public static LLVMDataValue create(String identifier, ILLVMMemoryType type, String value) throws LLVMException 
	{
		return new LLVMDataValue(identifier, type, value, false);
	}
	
	public static LLVMDataValue create(String identifier, ILLVMMemoryType type, String value, boolean isConstant) throws LLVMException 
	{
		return new LLVMDataValue(identifier, type, value, isConstant);
	}
	
	public static LLVMDataValue createConstant(ILLVMMemoryType type, String value) throws LLVMException 
	{
		return new LLVMDataValue(null, type, value, true);
	}
	
	
	/**
	 * @param identifier without %-prefix.
	 * @param type must not be a pointer.
	 * @param value can be null.
	 * @throws LLVMException
	 */
	public LLVMDataValue(String identifier, ILLVMMemoryType type, String value, boolean isConstant) throws LLVMException
	{
		if (identifier != null)
			setIdentifier(identifier);
		if (type == null)
			throw new LLVMException("Parameter \"type\" is null or empty.");
		if (type.isPointerType())
			throw new LLVMException("A pointer type can't be held inside a value data type.");
		
		
		this.type = type;
		this.value = value;
		this.isConstant = isConstant;
	}
	
	public String getIdentifierOrValue()
	{
		if (isConstant)
			return value;
		
		return getIdentifier();
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
