package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMPointerType implements ILLVMMemoryType
{
	private ILLVMMemoryType baseType;

	public static LLVMPointerType create(ILLVMMemoryType baseType) throws LLVMException
	{
		return new LLVMPointerType(baseType);
	}
	
	public static LLVMPointerType createi8() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createi8());
	}
	
	public static LLVMPointerType createi16() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createi16());
	}
	
	public static LLVMPointerType createi32() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createi32());
	}
	
	public static LLVMPointerType createi64() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createi64());
	}
	
	public static LLVMPointerType createi128() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createi128());
	}
	
	public static LLVMPointerType createi256() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createi256());
	}
	
	public static LLVMPointerType createu8() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createu8());
	}
	
	public static LLVMPointerType createu16() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createu16());
	}
	
	public static LLVMPointerType createu32() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createu32());
	}
	
	public static LLVMPointerType createu64() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createu64());
	}
	
	public static LLVMPointerType createu128() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createu128());
	}
	
	public static LLVMPointerType createu256() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createu256());
	}
	
	public static LLVMPointerType createf32() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createf32());
	}
	
	public static LLVMPointerType createf64() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createf64());
	}
	
	public static LLVMPointerType createBool() throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)LLVMValueType.createBool());
	}
	
	public static LLVMPointerType createFromStruct(LLVMBaseStruct struct) throws LLVMException
	{
		return LLVMPointerType.create((ILLVMMemoryType)new LLVMValueType(struct));
	}
	
	public LLVMPointerType(ILLVMMemoryType baseType) throws LLVMException
	{
		if (baseType == null)
			throw new LLVMException("Parameter \"baseType\" is null or empty.");
		if (baseType.isVoidType())
			throw new LLVMException("Pointer of void types are not allowed.");
		
		this.baseType = baseType;
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
		return true;
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
	public String getTypeDefinitionString() throws LLVMException {
		return baseType.getTypeDefinitionString() + "*";
	}
	
	/**
	 * Returns true if both types are pointers from the same base type.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMPointerType)
		{
			LLVMPointerType other = (LLVMPointerType)obj;
			
			return this.baseType.equals(other.baseType);
		}
		
		return false;
	}
}
