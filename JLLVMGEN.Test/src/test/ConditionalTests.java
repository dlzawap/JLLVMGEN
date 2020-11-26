package test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.LLVMModule;
import jllvmgen.enums.LLVMICompareConditions;
import jllvmgen.instructions.memory.LLVMAllocaInst;
import jllvmgen.instructions.memory.LLVMLoadInst;
import jllvmgen.instructions.memory.LLVMStoreInst;
import jllvmgen.instructions.others.LLVMICmpInst;
import jllvmgen.instructions.terminator.LLVMBrInst;
import jllvmgen.instructions.terminator.LLVMRetInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;
import jllvmgen.types.LLVMPointerType;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVoidType;

public class ConditionalTests
{
	private static LLVMModule module = LLVMModule.create("junittest.java");
	
    @Test
    public void conditionalTest() throws Exception
    {
    	var fn = LLVMFunction.createWithoutParameters(module, "foo", LLVMVoidType.createVoid());
    	
    	try
    	{
    		// Allocate memory
    		var p1 = LLVMAllocaInst.create(fn, fn.getNextFreeLocalVariableValueName(), LLVMPointerType.createi32());
    		var p2 = LLVMAllocaInst.create(fn, fn.getNextFreeLocalVariableValueName(), LLVMPointerType.createi32());
    	
    		// Store values into memory.
    		LLVMStoreInst.create(
    				fn,
    				LLVMDataValue.createValueOnly("remove", LLVMValueType.createi32(), "3"),
    				p1.getResult());
    		LLVMStoreInst.create(
    				fn,
    				LLVMDataValue.createValueOnly("remove", LLVMValueType.createi32(), "5"),
    				p2.getResult());
    		
    		// Load values.
    		var val1 = LLVMLoadInst.create(fn, p1.getResult());
    		var val2 = LLVMLoadInst.create(fn, p2.getResult());
    		
    		// Compare values.
    		var condition = LLVMICmpInst.create(
    				fn,
    				LLVMICompareConditions.eq,
    				val1.getResult(),
    				val2.getResult()).getResult();
    		
    		// Create conditional branch.
    		
//    		LLVMLabelType ifTrueLabelType = LLVMLabelType.create(fn, "iftrue");
//    		LLVMLabelType ifFalseLabelType = LLVMLabelType.create(fn, "iffalse");
    		
    		var cond = LLVMBrInst.createConditional(fn, condition);
    		
    		LLVMLabelType afterLabelType = LLVMLabelType.create(fn, "after", true);
    		
    		LLVMBrInst.createUnconditional(fn, cond.getIfTrueLabelType(), afterLabelType);
    		LLVMBrInst.createUnconditional(fn, cond.getIfFalseLabelType(), afterLabelType);
    		
    		LLVMRetInst.createVoid(fn);
    		
    		System.out.println(module.getModuleDefinitionString());
    	}
    	catch (LLVMException ex)
    	{
    		ex.printStackTrace();
    		fail(ex.getMessage());
    	}
    }
}
