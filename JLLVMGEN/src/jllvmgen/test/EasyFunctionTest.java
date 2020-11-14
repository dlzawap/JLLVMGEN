package jllvmgen.test;

import jllvmgen.LLVMFunction;
import jllvmgen.LLVMModule;
import jllvmgen.LLVMDataPointer;
import jllvmgen.LLVMDataValue;
import jllvmgen.instructions.binary.LLVMAddInst;
import jllvmgen.instructions.memory.LLVMAllocaInst;
import jllvmgen.instructions.memory.LLVMLoadInst;
import jllvmgen.instructions.memory.LLVMStoreInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.*;

// TODO: integrate junit into test project.

public class EasyFunctionTest {

	public static void main(String[] args)
	{
		try
		{
			/*
			 * f32 
			 */
			LLVMValueType value1 = jllvmgen.types.LLVMValueType.createf32();
			
			/*
			 * f32*
			 */
			LLVMPointerType pointerOfValue1 = LLVMPointerType.create(value1);
			
			System.out.println("f32 pointer type: \n\t" + pointerOfValue1.getTypeDefinitionString());
			
			/*
			 * [10 x f32*]
			 */
			LLVMArrayType arrayPointerOfValue1 = LLVMArrayType.create(pointerOfValue1, 10);
			
			System.out.println("f32 array type (size 10): \n\t" + arrayPointerOfValue1.getTypeDefinitionString());
			
			
			/*
			 * void
			 */
			LLVMVoidType voidType = LLVMVoidType.createVoid();
			
			System.out.println("void type: \n\t" + voidType.getTypeDefinitionString());
			
			// Tuple
			// LLVMLiteralStruct literalStruct = LLVMLiteralStruct.
			
			LLVMFunction fn = LLVMFunction.createWithoutParameters("foo", LLVMVoidType.createVoid());
			
//			LLVMAllocaInst.create(
//					fn,
//					LLVMPointer.create(
//						fn.getNextFreeLocalPointerValueName(),
//						LLVMPointerType.create(LLVMValueType.createf32())
//					)
//			);
			
			LLVMAllocaInst.create(
					fn,
					LLVMPointerType.createf32()
			);
			
			
			/*
			 * Add instruction
			 */
			{
				// Allocate memory for result.
				var resultPtr = LLVMAllocaInst.create(fn, LLVMPointerType.createi32());
				// Allocate memory for operands.
				var p1 = LLVMAllocaInst.create(fn, LLVMPointerType.createi32());
				var p2 = LLVMAllocaInst.create(fn, LLVMPointerType.createi32());
				
				// Place here store instruction.
				
				// Load value from operands.
				var op1 = LLVMLoadInst.createInst(fn, p1.getResult()).getResult();
				var op2 = LLVMLoadInst.createInst(fn, p2.getResult()).getResult();
				
				// Append add instruction.
				var add = LLVMAddInst.create(fn, op1, op2, false, true);
				
				// Store result into resultPtr.
				LLVMStoreInst.createInst(fn, add.getResult(), resultPtr.getResult());
			}
			
			
			fn.printDefinition();
			
			LLVMModule module = LLVMModule.create("hello.bol");
			module.registerFn(fn);
			
			System.out.println(module.foo());
			
			LLVMDataValue t1 = LLVMDataValue.create("s1", LLVMValueType.createf32());
			LLVMDataValue t2 = LLVMDataValue.create("s2", LLVMValueType.createf32());
			
			System.out.println(t1.getType().equals(t2.getType()));
			
			System.out.println(LLVMPrimitiveType.createf64().equals(LLVMPrimitiveType.createf64()));
			
//			LLVMFunction function = LLVMFunction.createVoidWithoutParameters("main");
//			
//			// Allocate local variable (i32);
//			LLVMAllocaInst var1 = LLVMAllocaInst.createInst(
//													function,
//													new LLVMPrimitiveType(LLVMPrimitiveTypes._i32),
//													1024);
//			
//			// Store value into local variable.
//			LLVMStoreInst.createInst(function,
//					new LLVMValue(function,
//							new LLVMValueType(new LLVMPrimitiveType(LLVMPrimitiveTypes._i32)), "3"),
//					var1.getResultPointer());
//			
//			
//			// Allocate multidimensional array.
//			KeyValueList<ILLVMBaseDataType, Integer> subTypes = new KeyValueList<ILLVMBaseDataType, Integer>();
//			subTypes.add(new LLVMPrimitiveType(LLVMPrimitiveTypes._i32), 4);
//			subTypes.add(new LLVMPrimitiveType(LLVMPrimitiveTypes._i32), 5);
//			
//			LLVMAllocaInst var2 = LLVMAllocaInst.createInst(
//					function,
//					new LLVMPrimitiveType(LLVMPrimitiveTypes._i32),
//					subTypes);
//			
//			// Test load instruction
//			LLVMLoadInst val1 = LLVMLoadInst.createInst(function, var1.getResultPointer());
//			
//			
//			function.printDefinition();
			
		}
		catch (LLVMException ex)
		{
			ex.printStackTrace();
		}
	}
}
