package jllvmgen.types;

import jllvmgen.misc.LLVMException;

public class LLVMLiteralStruct extends LLVMBaseStruct
{
	@Override
	public void addMember(ILLVMMemoryType member) throws LLVMException
	{
		if (member.isValueType())
		{
			LLVMValueType temp = (LLVMValueType)member;
			if (temp.isIdentifiedStructType() ||
				temp.isLiteralStructType() ||
				temp.isPackedStruct())
			{
				throw new LLVMException("Literal structs can't be recursive.");
			}
		}
		
		super.addMember(member);
	}
	
	@Override
	public void addMembers(ILLVMMemoryType... members) throws LLVMException
	{
		for (ILLVMMemoryType m : members)
		{
			if (m.isValueType())
			{
				LLVMValueType temp = (LLVMValueType)m;
				if (temp.isIdentifiedStructType() ||
					temp.isLiteralStructType() ||
					temp.isPackedStruct())
				{
					throw new LLVMException("Literal structs can't be recursive.");
				}
			}
			
			super.addMember(m);
		}
	}
	
	@Override
	public boolean isIdentifiedStruct() throws LLVMException
	{
		return false;
	}
	
	@Override
	public boolean isLiteralStruct() throws LLVMException
	{
		return false;
	}
	
	@Override
	public boolean isPackedStruct() throws LLVMException
	{
		return true;
	}
}
