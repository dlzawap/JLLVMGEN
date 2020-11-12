package jllvmgen.types;

import java.util.ArrayList;
import java.util.Arrays;

import jllvmgen.misc.LLVMException;

public class LLVMFunctionType implements ILLVMMemoryType
{
	private ILLVMMemoryType returnType;
	private ArrayList<ILLVMMemoryType> parameters;

	private boolean isVarargFn;
	
	public static LLVMFunctionType create(ILLVMMemoryType returnType) throws LLVMException
	{
		return new LLVMFunctionType(returnType, null, false);
	}
	
	public static LLVMFunctionType create(ILLVMMemoryType returnType,
			ArrayList<ILLVMMemoryType> parameters) throws LLVMException
	{
		return new LLVMFunctionType(returnType, parameters, false);
	}
	
	public static LLVMFunctionType create(ILLVMMemoryType returnType,
			ILLVMMemoryType... parameters) throws LLVMException
	{
		ArrayList<ILLVMMemoryType> temp = new ArrayList<ILLVMMemoryType>();
		temp.addAll(Arrays.asList(parameters));
		
		return new LLVMFunctionType(returnType, temp, false);
	}
	
	public static LLVMFunctionType create(ILLVMMemoryType returnType,
			ArrayList<ILLVMMemoryType> parameters, boolean isVarargFn) throws LLVMException
	{
		return new LLVMFunctionType(returnType, parameters, isVarargFn);
	}
	
	public LLVMFunctionType(ILLVMMemoryType returnType,
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
		return false;
	}

	@Override
	public boolean isFunctionType() {
		return true;
	}

	@Override
	public String getTypeDefinitionString() throws LLVMException {
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
		
		sb.append(")");
		
		return sb.toString();
	}
}
