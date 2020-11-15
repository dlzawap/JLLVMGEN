package jllvmgen;

import jllvmgen.instructions.binary.LLVMAddInst;
import jllvmgen.instructions.memory.LLVMAllocaInst;
import jllvmgen.instructions.memory.LLVMLoadInst;
import jllvmgen.instructions.memory.LLVMStoreInst;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMArrayType;
import jllvmgen.types.LLVMPointerType;
import jllvmgen.types.LLVMPrimitiveType;
import jllvmgen.types.LLVMValueType;
import jllvmgen.types.LLVMVoidType;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
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

			// LLVMAllocaInst.create(
			// fn,
			// LLVMPointer.create(
			// fn.getNextFreeLocalPointerValueName(),
			// LLVMPointerType.create(LLVMValueType.createf32())
			// )
			// );

			// LLVMAllocaInst.create(
			// fn,
			// LLVMPointerType.createf32()
			// );

			/*
			 * Add instruction
			 */
			{
				// Allocate memory for result.
				var resultPtr = LLVMAllocaInst.create(fn, "result", LLVMPointerType.createi32(), 4).getResult();
				// Allocate memory for operands.
				var p1 = LLVMAllocaInst
						.create(fn, fn.getNextFreeLocalPointerValueName(), LLVMPointerType.createi32(), 4).getResult();
				var p2 = LLVMAllocaInst
						.create(fn, fn.getNextFreeLocalPointerValueName(), LLVMPointerType.createi32(), 4).getResult();

				// Store value into data pointer.
				LLVMStoreInst.createInst(fn, LLVMDataValue.createConstant(LLVMValueType.createi32(), "5"), p1);
				LLVMStoreInst.createInst(fn, LLVMDataValue.createConstant(LLVMValueType.createi32(), "4"), p2);

				// Load value from operands.
				var op1 = LLVMLoadInst.createInst(fn, p1).getResult();
				var op2 = LLVMLoadInst.createInst(fn, p2).getResult();

				// Append add instruction.
				var add = LLVMAddInst.create(fn, op1, op2, false, true);

				// Store result into resultPtr.
				LLVMStoreInst.createInst(fn, add.getResult(), resultPtr);
			}

			fn.printDefinition();

			LLVMModule module = LLVMModule.create("hello.bol");
			module.registerFn(fn);

			System.out.println(module.foo());

			LLVMDataValue t1 = LLVMDataValue.create("s1", LLVMValueType.createf32());
			LLVMDataValue t2 = LLVMDataValue.create("s2", LLVMValueType.createf32());

			System.out.println(t1.getType().equals(t2.getType()));

			System.out.println(LLVMPrimitiveType.createf64().equals(LLVMPrimitiveType.createf64()));
		} catch (LLVMException ex) {
			ex.printStackTrace();
		}
	}
}
