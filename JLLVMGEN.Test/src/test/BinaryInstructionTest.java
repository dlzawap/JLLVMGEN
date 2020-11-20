package test;

import org.junit.jupiter.api.Test;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.LLVMModule;
import jllvmgen.instructions.binary.LLVMAddInst;
import jllvmgen.instructions.memory.LLVMAllocaInst;
import jllvmgen.instructions.memory.LLVMLoadInst;
import jllvmgen.instructions.memory.LLVMStoreInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMPointerType;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVoidType;

public class BinaryInstructionTest
{
    @Test
    public void equalPrimitiveTypes() throws Exception
    {
    	var module = LLVMModule.create("junittest.java");
    	var fn = LLVMFunction.createWithoutParameters(module, "foo", LLVMVoidType.createVoid());
    	
    	
    	try
    	{
			// Allocate memory for result.
			var resultPtr = LLVMAllocaInst.create(fn, "result", LLVMPointerType.createi32(), 4).getResult();
			// Allocate memory for operands.
			var p1 = LLVMAllocaInst
					.create(fn, fn.getNextFreeLocalPointerValueName(), LLVMPointerType.createi32(), 4).getResult();
			var p2 = LLVMAllocaInst
					.create(fn, fn.getNextFreeLocalPointerValueName(), LLVMPointerType.createi32(), 4).getResult();
	
			// Store value into data pointer.
			LLVMStoreInst.createInst(fn, LLVMDataValue.createConstant(fn, "const1", LLVMValueType.createi32(), "5"), p1);
			LLVMStoreInst.createInst(fn, LLVMDataValue.createConstant(fn, "const2", LLVMValueType.createi32(), "4"), p2);
	
			// Load value from operands.
			var op1 = LLVMLoadInst.createInst(fn, p1).getResult();
			var op2 = LLVMLoadInst.createInst(fn, p2).getResult();
	
			// Append add instruction.
			var add = LLVMAddInst.create(fn, op1, op2, false, true);
	
			// Store result into resultPtr.
			LLVMStoreInst.createInst(fn, add.getResult(), resultPtr);
    	}
    	catch (LLVMException ex)
    	{
    		ex.printStackTrace();
    	}
    }
}
