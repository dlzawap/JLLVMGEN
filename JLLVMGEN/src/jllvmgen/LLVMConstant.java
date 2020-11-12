package jllvmgen;

import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMPointerType;

public class LLVMConstant extends LLVMDataPointer
{
	private String value;

	public LLVMConstant(String name, LLVMDataValue dataValue) throws LLVMException
	{
		super(name, LLVMPointerType.create(dataValue.getType()));
		
		this.value = dataValue.getValue();
	}
}
