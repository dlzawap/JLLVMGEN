package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

/**
 * Holds a value data type and suitable value.
 */
public class LLVMDataValue implements ILLVMVariableType
{
	public enum ValueVariableTypes
	{
		local,
		constant,
		value_only;
	}
	
	// Holds identifier without %(@ for constant)-prefix.
	private String identifier;
	// Holds data value type. (Primitive type, struct, vector, array)
	private final ILLVMMemoryType type;
	// Holds value (integer, float or constructor for arrays/vectors/structs)
	private String value;
	
	private final ValueVariableTypes variableType;
	
	
	public static LLVMDataValue create(
			LLVMFunction fn,
			String identifier,
			ILLVMMemoryType type,
			String value,
			ValueVariableTypes variableType) throws LLVMException
	{
		return new LLVMDataValue(fn, identifier, type, value, variableType);
	}
	
	// Without value
	public static LLVMDataValue createLocalVariable(
			String identifier,
			ILLVMMemoryType type) throws LLVMException
	{
		return new LLVMDataValue(null, identifier, type, null, ValueVariableTypes.local);
	}
	
	// With value
	public static LLVMDataValue createLocalVariable(
			String identifier,
			ILLVMMemoryType type,
			String value) throws LLVMException
	{
		return new LLVMDataValue(null, identifier, type, value, ValueVariableTypes.local);
	}
	
	public static LLVMDataValue createConstant(
			LLVMFunction fn,
			String identifier,
			ILLVMMemoryType type,
			String value) throws LLVMException
	{
		return new LLVMDataValue(fn, identifier, type, value, ValueVariableTypes.constant);
	}
	
	public static LLVMDataValue createValueOnly(
			String identifier,
			ILLVMMemoryType type,
			String value) throws LLVMException
	{
		return new LLVMDataValue(null, identifier, type, value, ValueVariableTypes.value_only);
	}
	
	public LLVMDataValue(
			LLVMFunction fn,
			String identifier,
			ILLVMMemoryType type,
			String value,
			ValueVariableTypes variableType) throws LLVMException
	{
		if (identifier != null)
			setIdentifier(identifier);
		if (type == null)
			throw new LLVMException("Parameter \"type\" is null or empty.");
		if (type.isPointerType())
			throw new LLVMException("A pointer type can't be held inside a value data type.");
		
		
		this.type = type;
		this.value = value;
		this.variableType = variableType;

		// Only constant types need to be registered on the module through the function.
		if (isConstant())
		{
			if (fn == null)
				throw new LLVMException("Parameter \"fn\" is null or empty."
						+ "Empty function parameter is only on value-only types and local variables allowed.");
			if (value == null)
				throw new LLVMException("A constant must define always a value.");
			
			fn.registerConstant(this);
		}
	}
	
	public String getIdentifierOrValue()
	{
		if (variableType == ValueVariableTypes.value_only)
			return value;
		
		return getIdentifier();
	}
	
	public String getValue()
	{
		return value;
	}
	
	public boolean isValueOnly()
	{
		return variableType == ValueVariableTypes.value_only;
	}
	
	@Override
	public String getIdentifier()
	{
		if (isConstant())
			return "@" + identifier;
		
		return "%" + identifier;
	}
	
	@Override
	public String getIdentifierWithoutPrefix()
	{
		return identifier;
	}

	@Override
	public void setIdentifier(String identifier) throws LLVMException
	{
		if (identifier == null)
			throw new LLVMException("Parameter \"identifier\" is null or empty.");
		
		// Check if identifier already contains %/@-prefix and if available remove it.
		if (identifier.startsWith("%"))
			identifier = identifier.substring(1);
		if (identifier.startsWith("@"))
			identifier = identifier.substring(1);
		
		this.identifier = identifier;
	}

	@Override
	public ILLVMMemoryType getType()
	{
		return type;
	}

	@Override
	public boolean isLocalVariable()
	{
		return variableType == ValueVariableTypes.local;
	}

	@Override
	public boolean isGlobalVariable()
	{
		return false;
	}
	
	@Override
	public boolean isConstant()
	{
		return variableType == ValueVariableTypes.constant;
	}

	@Override
	public boolean isPointerVariable()
	{
		return false;
	}

	@Override
	public boolean isValueVariable()
	{
		return true;
	}
}
