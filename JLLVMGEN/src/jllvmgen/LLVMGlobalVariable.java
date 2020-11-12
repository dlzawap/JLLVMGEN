package jllvmgen;

import jllvmgen.enums.LLVMRuntimePreemptionSpecifiers;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMPointerType;

//TODO: Rework
public class LLVMGlobalVariable extends LLVMDataPointer
{
	private String value;
	private LLVMRuntimePreemptionSpecifiers runtimePreemptionSpecifier;

	public LLVMGlobalVariable(String name, LLVMDataValue dataValue) throws LLVMException
	{
		super(name, LLVMPointerType.create(dataValue.getType()));
		
		this.value = dataValue.getValue();
	}
	
	public void setValue(String value) throws LLVMException
	{
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
