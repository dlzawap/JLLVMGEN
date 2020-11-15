package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jllvmgen.LLVMDataValue;
import jllvmgen.types.LLVMValueType;


public class TypeTest
{
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
			LLVMDataValue type1 = LLVMDataValue.create("s1", LLVMValueType.createf32());
			LLVMDataValue type2 = LLVMDataValue.create("s2", LLVMValueType.createf32());
			
			assertEquals(type1.getType(), type2.getType());
    	}
    }
}
