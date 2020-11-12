package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMArrayType implements ILLVMMemoryType
{
	private ILLVMMemoryType baseType;
	private int size;
	
	public static LLVMArrayType create(ILLVMMemoryType baseType, int size) throws LLVMException
	{
		return new LLVMArrayType(baseType, size);
	}
	
	public LLVMArrayType(ILLVMMemoryType baseType, int size) throws LLVMException
	{
		if (baseType == null)
			throw new LLVMException("Parameter \"baseType\" is null or empty.");
		if (baseType.isVoidType())
			throw new LLVMException("Array of void types are not allowed.");
		if (size < 0)
			throw new LLVMException("Array size must be equal or bigger than 0. Size: " + size);
		
		this.baseType = baseType;
		this.size = size;
	}
	
	public ILLVMMemoryType getBaseType()
	{
		return baseType;
	}
	
	public int getArraySize()
	{
		return size;
	}

	@Override
	public boolean isValueType() {
		return false;
	}

	@Override
	public boolean isArrayType() {
		return true;
	}

	@Override
	public boolean isPointerType() {
		return false;
	}

	@Override
	public boolean isVectorType() {
		return false;
	}

	@Override
	public boolean isVoidType() {
		return false;
	}
	
	@Override
	public boolean isFunctionPointer() {
		return false;
	}

	@Override
	public boolean isFunctionType() {
		return false;
	}
	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		return "[" + size + " x " + baseType.getTypeDefinitionString() + "]";
	}
	
	
	/**
	 * Returns true if base type and array size are equal.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMArrayType)
		{
			LLVMArrayType other = (LLVMArrayType)obj;
			
			return this.baseType.equals(other.baseType) &&
			       this.getArraySize() == other.getArraySize();
		}
		
		return false;
	}
}
