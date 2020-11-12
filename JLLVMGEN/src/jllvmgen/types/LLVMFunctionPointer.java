package jllvmgen.types;

import java.util.ArrayList;

import jllvmgen.misc.LLVMException;

public class LLVMFunctionPointer implements ILLVMMemoryType
{
	private ILLVMMemoryType returnType;
	private ArrayList<ILLVMMemoryType> parameters;

	private boolean isVarargFn;
	
	public static LLVMFunctionPointer create(ILLVMMemoryType returnType) throws LLVMException
	{
		return new LLVMFunctionPointer(returnType, null, false);
	}
	
	public static LLVMFunctionPointer create(ILLVMMemoryType returnType,
			ArrayList<ILLVMMemoryType> parameters) throws LLVMException
	{
		return new LLVMFunctionPointer(returnType, parameters, false);
	}
	
	public static LLVMFunctionPointer create(ILLVMMemoryType returnType,
			ArrayList<ILLVMMemoryType> parameters, boolean isVarargFn) throws LLVMException
	{
		return new LLVMFunctionPointer(returnType, parameters, isVarargFn);
	}
	
	public LLVMFunctionPointer(ILLVMMemoryType returnType,
			ArrayList<ILLVMMemoryType> parameters, boolean isVarargFn) throws LLVMException
	{
		if (returnType == null)
			throw new LLVMException("Parameter \"returnType\" is null or empty.");
		
		if (parameters == null && isVarargFn)
			throw new LLVMException("Variadic functions must have other parameters.");
		
		this.returnType = returnType;
		this.parameters = parameters;
		this.isVarargFn = isVarargFn;
	}

	@Override
	public boolean isValueType() {
		return false;
	}

	@Override
	public boolean isArrayType() {
		return false;
	}

	@Override
	public boolean isPointerType() {
		return false;
	}

	@Override
	public boolean isVectorType() {
		return false;
	}

	@Override
	public boolean isVoidType() {
		return false;
	}
	
	@Override
	public boolean isFunctionPointer() {
		return true;
	}

	@Override
	public boolean isFunctionType() {
		return false;
	}

	@Override
	public String getTypeDefinitionString() throws LLVMException
	{
		StringBuilder sb = new StringBuilder(returnType.getTypeDefinitionString());
		sb.append("(");
		
		for (int i = 0; i < parameters.size(); ++i)
		{
			if (i > 0)
				sb.append(", ");
			
			sb.append(parameters.get(i).getTypeDefinitionString());
		}
		
		if (isVarargFn)
			sb.append(", ...");
		
		sb.append(")*");
		
		return sb.toString();
	}
}
