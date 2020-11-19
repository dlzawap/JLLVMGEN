package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;

public interface ILLVMVariableType
{
	public String getIdentifier();
	public String getIdentifierWithoutPrefix();
	public void setIdentifier(String identifier) throws LLVMException;
	public ILLVMMemoryType getType();
	
	public boolean isLocalVariable();
	public boolean isGlobalVariable();
	public boolean isConstant();
	public boolean isPointerVariable();
	public boolean isValueVariable();
}
