package jllvmgen.instructions;

import jllvmgen.misc.LLVMException;

public interface ILLVMBaseInst {
	public String getInstructionString() throws LLVMException;
}
