package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMVectorType implements ILLVMMemoryType
{
	private ILLVMMemoryType baseType;
	private int size;
	
	public LLVMVectorType() throws LLVMException {
		throw new LLVMException("Not implemented!");
	}
	
	public ILLVMMemoryType getBaseType()
	{
		return baseType;
	}
	
	@Override
	public boolean isValueType() {
		return false;
	}

	@Override
	public boolean isArrayType() {
		return false;
	}

	@Override
	public boolean isPointerType() {
		return false;
	}

	@Override
	public boolean isVectorType() {
		return true;
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
		return "<" + size + " x " + baseType.getTypeDefinitionString() + ">";
	}
	
	/**
	 * Returns true if base type and vector size are equal.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMVectorType)
		{
			LLVMVectorType other = (LLVMVectorType)obj;
			
			return this.baseType.equals(other.baseType) &&
			       this.size == other.size;
		}
		
		return false;
	}
}
