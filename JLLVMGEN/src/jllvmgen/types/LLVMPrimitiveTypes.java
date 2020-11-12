package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public enum LLVMPrimitiveTypes
{
	_i8,
	_i16,
	_i32,
	_i64,
	_i128,
	_i256,
	_u8,
	_u16,
	_u32,
	_u64,
	_u128,
	_u256,
	_f32,
	_f64,
	_bool,
	_char;
	
	public String print(LLVMPrimitiveTypes type) throws LLVMException
	{
		switch (type)
		{
			case _i8:
				return "i8";
			case _i16:
				return "i16";
			case _i32:
				return "i32";
			case _i64:
				return "i64";
			case _i128:
				return "i128";
			case _i256:
				return "i256";
			case _u8:
				return "i8";
			case _u16:
				return "i16";
			case _u32:
				return "i32";
			case _u64:
				return "i64";
			case _u128:
				return "i128";
			case _u256:
				return "i256";
			case _f32:
				return "f32";
			case _f64:
				return "f64";
			case _bool:
				return "i1";
			case _char:
				return "i1";
			default:
				throw new LLVMException("Unknown primitive type. Type: " + type.toString());
		}
	}
}
