package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMPrimitiveType implements ILLVMCodeCreationFunctionality
{
	private LLVMPrimitiveTypes type;
	
	public static LLVMPrimitiveType createi8()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._i8);
	}
	
	public static LLVMPrimitiveType createi16()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._i16);
	}
	
	public static LLVMPrimitiveType createi32()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._i32);
	}
	
	public static LLVMPrimitiveType createi64()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._i64);
	}
	
	public static LLVMPrimitiveType createi128()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._i128);
	}
	
	public static LLVMPrimitiveType createi256()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._i256);
	}
	
	public static LLVMPrimitiveType createu8()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._u8);
	}
	
	public static LLVMPrimitiveType createu16()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._u16);
	}
	
	public static LLVMPrimitiveType createu32()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._u32);
	}
	
	public static LLVMPrimitiveType createu64()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._u64);
	}
	
	public static LLVMPrimitiveType createu128()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._u128);
	}
	
	public static LLVMPrimitiveType createu256()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._u256);
	}
	
	public static LLVMPrimitiveType createf32()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._f32);
	}
	
	public static LLVMPrimitiveType createf64()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._f64);
	}
	
	public static LLVMPrimitiveType createBool()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._bool);
	}
	
	public static LLVMPrimitiveType createChar()
	{
		return new LLVMPrimitiveType(LLVMPrimitiveTypes._char);
	}
	
	public LLVMPrimitiveType(LLVMPrimitiveTypes type)
	{
		this.type = type;	
	}

	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		return type.print(type);
	}
	
	public LLVMPrimitiveTypes getType()
	{
		return type;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj instanceof LLVMPrimitiveType)
			return this.getType() == ((LLVMPrimitiveType)obj).getType();
		
		return false;
	}
}
