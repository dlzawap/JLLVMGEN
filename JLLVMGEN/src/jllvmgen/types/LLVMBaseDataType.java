package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public abstract class LLVMBaseDataType implements ILLVMCodeCreationFunctionality
{
	protected final LLVMPrimitiveType primitiveType;
	protected final LLVMBaseStruct structType;
	
	public LLVMBaseDataType(LLVMPrimitiveType primitiveType)
	{
		this.primitiveType = primitiveType;
		this.structType = null;
	}

	public LLVMBaseDataType(LLVMBaseStruct structType)
	{
		this.primitiveType = null;
		this.structType = structType;
	}
	
	public boolean isPrimitiveType() {
		return primitiveType != null;
	}

	public boolean isIdentifiedStructType() {
		return structType != null && structType instanceof LLVMIdentifiedStruct;
	}
	
	public boolean isLiteralStructType() {
		return structType != null && structType instanceof LLVMLiteralStruct;
	}
	
	public boolean isPackedStruct() {
		return structType != null && structType instanceof LLVMPackedStruct;
	}
	
	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		if (isPrimitiveType())
			return primitiveType.getTypeDefinitionString();
		else if (isIdentifiedStructType() ||
			isLiteralStructType() ||
			isPackedStruct())
			return structType.getTypeDefinitionString();
		else
			throw new LLVMException("Something went wrong during the code creation."
					+ "Primitive and struct types are both null.");
	}
	
	/**
	 * Returns true if both data types are from the same type.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMBaseDataType)
		{
			LLVMBaseDataType other = (LLVMBaseDataType)obj;
			
			if (isPrimitiveType())
				return primitiveType.equals(other.primitiveType);
			else if (isIdentifiedStructType() ||
					 isLiteralStructType() ||
					 isPackedStruct())
				return structType.equals(other.structType);
			else
				return false;
		}
		
		return false;
	}
}
