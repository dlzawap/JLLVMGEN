package test;

import org.junit.jupiter.api.Test;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.LLVMModule;
import jllvmgen.instructions.others.LLVMCallInst;
import jllvmgen.instructions.terminator.LLVMRetInst;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVoidType;

public class CallTests
{
	private static LLVMModule module = LLVMModule.create("junittest.java");
	
    @Test
    public void equalPrimitiveTypes() throws Exception
    {
    	var otherFn = LLVMFunction.createWithoutParameters(module, "foo", LLVMVoidType.createVoid());
    	LLVMRetInst.createVoid(otherFn);
    	
    	var fn = LLVMFunction.createWithoutParameters(module, "main", LLVMValueType.createi32());
    	
    	LLVMCallInst.create(fn, LLVMVoidType.createVoid(), "@" + otherFn.getFunctionName(), null, null, null, null, null);
    	LLVMRetInst.create(fn, LLVMDataValue.createValueOnly(null, LLVMValueType.createi32(), "0"));
    	
    	System.out.println(module.getModuleDefinitionString());
    }
}
