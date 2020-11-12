package jllvmgen.types;

public class LLVMVoidType implements ILLVMMemoryType, ILLVMCodeCreationFunctionality
{
	public static LLVMVoidType createVoid()
	{
		return new LLVMVoidType();
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
		return false;
	}

	@Override
	public boolean isVoidType() {
		return true;
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
	public String getTypeDefinitionString() {
		return "void";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		return obj instanceof LLVMVoidType;
	}
}
