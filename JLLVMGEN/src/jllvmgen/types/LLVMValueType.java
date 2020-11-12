package jllvmgen.types;


/**
 * @author Manuel
 * LLVMValueTypes can only be created from primitive- and struct types.
 */
public class LLVMValueType extends LLVMBaseDataType implements ILLVMMemoryType
{
	public static LLVMValueType createi8()
	{
		return new LLVMValueType(LLVMPrimitiveType.createi8());
	}
	
	public static LLVMValueType createi16()
	{
		return new LLVMValueType(LLVMPrimitiveType.createi16());
	}
	
	public static LLVMValueType createi32()
	{
		return new LLVMValueType(LLVMPrimitiveType.createi32());
	}
	
	public static LLVMValueType createi64()
	{
		return new LLVMValueType(LLVMPrimitiveType.createi64());
	}
	
	public static LLVMValueType createi128()
	{
		return new LLVMValueType(LLVMPrimitiveType.createi128());
	}
	
	public static LLVMValueType createi256()
	{
		return new LLVMValueType(LLVMPrimitiveType.createi256());
	}
	
	public static LLVMValueType createu8()
	{
		return new LLVMValueType(LLVMPrimitiveType.createu8());
	}
	
	public static LLVMValueType createu16()
	{
		return new LLVMValueType(LLVMPrimitiveType.createu16());
	}
	
	public static LLVMValueType createu32()
	{
		return new LLVMValueType(LLVMPrimitiveType.createu32());
	}
	
	public static LLVMValueType createu64()
	{
		return new LLVMValueType(LLVMPrimitiveType.createu64());
	}
	
	public static LLVMValueType createu128()
	{
		return new LLVMValueType(LLVMPrimitiveType.createu128());
	}
	
	public static LLVMValueType createu256()
	{
		return new LLVMValueType(LLVMPrimitiveType.createu256());
	}
	
	public static LLVMValueType createf32()
	{
		return new LLVMValueType(LLVMPrimitiveType.createf32());
	}
	
	public static LLVMValueType createf64()
	{
		return new LLVMValueType(LLVMPrimitiveType.createf64());
	}
	
	public static LLVMValueType createBool()
	{
		return new LLVMValueType(LLVMPrimitiveType.createBool());
	}
	
	public static LLVMValueType createChar()
	{
		return new LLVMValueType(LLVMPrimitiveType.createChar());
	}
	
	public static LLVMValueType createFromStruct(LLVMBaseStruct struct)
	{
		return new LLVMValueType(struct);
	}
	
	public LLVMValueType(LLVMPrimitiveType primitiveType)
	{
		super(primitiveType);
	}
	
	public LLVMValueType(LLVMBaseStruct structType)
	{
		super(structType);
	}
	
	public boolean holdsFloatingPoint()
	{
		if (!isPrimitiveType())
			return false;
		
		switch (super.primitiveType.getType())
		{
			case _f32:
				return true;
			case _f64:
				return true;
			default:
				return false;
		}
	}
	
	public boolean holdsPrimInt()
	{
		if (!isPrimitiveType())
			return false;
		
		switch (super.primitiveType.getType())
		{
			case _i8:
				return true;
			case _i16:
				return true;
			case _i32:
				return true;
			case _i64:
				return true;
			case _i128:
				return true;
			case _i256:
				return true;
			case _u8:
				return true;
			case _u16:
				return true;
			case _u32:
				return true;
			case _u64:
				return true;
			case _u128:
				return true;
			case _u256:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean isValueType() {
		return true;
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
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMValueType)
			return super.equals(obj);
		
		return false;
	}
}
