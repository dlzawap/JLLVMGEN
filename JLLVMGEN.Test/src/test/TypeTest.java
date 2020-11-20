package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.LLVMModule;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.ILLVMMemoryType;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVoidType;


public class TypeTest
{
	@Test
	public void testDataValueTypesCreation()
	{
		// Create constant with empty function argument.
		try
		{
			LLVMDataValue.createConstant(null, "s1", LLVMValueType.createBool(), null);
			fail("Excepted a LLVMException to be thrown.");
		}
		catch(LLVMException ex)
		{
			assertNotNull(ex);
		}
		
		// Create constant with empty value.
		try
		{
			var fn = LLVMFunction.createWithoutParameters(
					LLVMModule.create("junittest.java"),
					"junittest",
					LLVMVoidType.createVoid());
			
			LLVMDataValue.createConstant(fn, "s1", LLVMValueType.createBool(), null);
			fail("Excepted a LLVMException to be thrown.");
		}
		catch(LLVMException ex)
		{
			assertNotNull(ex);
		}
	}
	
    @Test
    public void equalPrimitiveTypes() throws Exception
    {
    	{
			var type1 = LLVMValueType.createf32();
			var type2 =  LLVMValueType.createf32();
			
			assertTrue(type1.equals(type2));
    	}
    	
    	{
			var type1 = LLVMValueType.createf64();
			var type2 =  LLVMValueType.createf64();
			
			assertTrue(type1.equals(type2));
    	}
    	
    	{
			var type1 = LLVMValueType.createi8();
			var type2 =  LLVMValueType.createi8();
			
			assertTrue(type1.equals(type2));
    	}
    	
    	{
			var type1 = LLVMValueType.createu8();
			var type2 =  LLVMValueType.createu8();
			
			assertTrue(type1.equals(type2));
    	}
    	
    	{
			var type1 = LLVMValueType.createu8();
			var type2 =  LLVMValueType.createi8();
			
			assertFalse(type1.equals(type2));
    	}
    	
    	{
			var type1 = LLVMValueType.createf32();
			var type2 =  LLVMValueType.createf64();
			
			assertFalse(type1.equals(type2));
    	}
    	
    	{
			LLVMDataValue type1 = LLVMDataValue.createLocalVariable("s1", LLVMValueType.createf32());
			LLVMDataValue type2 = LLVMDataValue.createLocalVariable("s2", LLVMValueType.createf32());
			
			assertEquals(type1.getType(), type2.getType());
    	}
    }
}
