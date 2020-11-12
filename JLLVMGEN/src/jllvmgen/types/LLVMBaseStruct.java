package jllvmgen.types;

import java.util.ArrayList;

import jllvmgen.misc.LLVMException;

public abstract class LLVMBaseStruct implements ILLVMCodeCreationFunctionality
{
	private ArrayList<ILLVMMemoryType> members = new ArrayList<ILLVMMemoryType>();
	
	
	public void addMember(ILLVMMemoryType member) throws LLVMException
	{
		this.members.add(member);
	}
	
	public void addMembers(ILLVMMemoryType... members) throws LLVMException
	{
		if (members == null)
			throw new LLVMException("Collection members is null.");
		
		for (ILLVMMemoryType m : members)
		{
			if (m == null)
				throw new LLVMException("Member is null.");
			
			this.members.add(m);
		}
	}
	
	public void removeMember(int index) throws LLVMException
	{
		if (index > members.size())
			throw new LLVMException("Index is out of range. Size:" + this.members.size() + ", Index: " + index);
		this.members.remove(index);
	}
	
	public ILLVMMemoryType memberAt(int index)
	{
		return members.get(index);
	}
	
	public int memberCount()
	{
		return members.size();
	}
	
	public boolean isIdentifiedStruct() throws LLVMException
	{
		throw new LLVMException("isIdentifiedStruct() is not implemented.");
	}
	
	public boolean isLiteralStruct() throws LLVMException
	{
		throw new LLVMException("isIdentifiedStruct() is not implemented.");
	}
	
	public boolean isPackedStruct() throws LLVMException
	{
		throw new LLVMException("isIdentifiedStruct() is not implemented.");
	}
	
	@Override
	public String getTypeDefinitionString() throws LLVMException {
		StringBuilder sb = new StringBuilder("{");
		
		for (int i = 0; i < members.size(); ++i)
		{
			if (i == 0)
				sb.append(" " + members.get(i).getTypeDefinitionString());
			else
				sb.append(", " + members.get(i).getTypeDefinitionString());
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
