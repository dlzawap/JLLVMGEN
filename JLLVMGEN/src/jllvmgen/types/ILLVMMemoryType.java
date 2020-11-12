package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public interface ILLVMMemoryType {
	public boolean isValueType();
	public boolean isArrayType();
	public boolean isPointerType();
	public boolean isVectorType();
	public boolean isVoidType();
	public boolean isFunctionPointer();
	public boolean isFunctionType();
	
	public String getTypeDefinitionString() throws LLVMException;
}
