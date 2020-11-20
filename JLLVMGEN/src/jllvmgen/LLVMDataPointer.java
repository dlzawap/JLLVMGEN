package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMPointerType;

public class LLVMDataPointer implements ILLVMVariableType
{
	// Holds identifier without %-prefix.
	private String identifier;
	// Holds data value type. (pointer to: primitive type, struct, vector, array)
	private final LLVMPointerType type;
	private final PointerVariableTypes variableType;
	
	public enum PointerVariableTypes
	{
		local,
		global
	}
	
	public static LLVMDataPointer create(
			LLVMFunction fn,
			String identifier,
			ILLVMMemoryType type,
			PointerVariableTypes variableType) throws LLVMException
	{
		return new LLVMDataPointer(fn, identifier, type, variableType);
	}
	
	public static LLVMDataPointer createLocalVariable(
			String identifier,
			ILLVMMemoryType type) throws LLVMException
	{
		return new LLVMDataPointer(null, identifier, type, PointerVariableTypes.local);
	}
	
	public static LLVMDataPointer createGlobalVariable(
			LLVMFunction fn,
			String identifier,
			ILLVMMemoryType type) throws LLVMException
	{
		return new LLVMDataPointer(fn, identifier, type, PointerVariableTypes.global);
	}
	
	public LLVMDataPointer(
			LLVMFunction fn,
			String identifier,
			ILLVMMemoryType type,
			PointerVariableTypes variableType) throws LLVMException
	{
		if (identifier == null)
			throw new LLVMException("Parameter \"name\" is null or empty.");
		if (type == null)
			throw new LLVMException("Parameter \"type\" is null or empty.");
		
		setIdentifier(identifier);
		
		if (!type.isPointerType())
			throw new LLVMException("A data pointer type can't holds other types."
					+ "Actual type: " + type.getTypeDefinitionString());
			
		this.type = (LLVMPointerType)type;
		this.variableType = variableType;
		
		// Only global variables need to be registered on the module through the function.
		if (isGlobalVariable())
		{
			if (fn == null)
				throw new LLVMException("Parameter \"fn\" is null or empty."
						+ "Empty function parameter is only on local pointer variables allowed.");
			
			fn.registerGlobalVariable(this);
		}
	}

	@Override
	public String getIdentifier()
	{
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
	public LLVMPointerType getType()
	{
		return type;
	}

	@Override
	public boolean isLocalVariable()
	{
		return variableType == PointerVariableTypes.local;
	}

	@Override
	public boolean isGlobalVariable()
	{
		return variableType == PointerVariableTypes.global;
	}
	
	@Override
	public boolean isConstant()
	{
		return false;
	}

	@Override
	public boolean isPointerVariable()
	{
		return true;
	}

	@Override
	public boolean isValueVariable()
	{
		return false;
	}
}
