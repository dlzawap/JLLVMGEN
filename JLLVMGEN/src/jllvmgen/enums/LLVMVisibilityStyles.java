package jllvmgen.enums;

import jllvmgen.misc.LLVMException;

public enum LLVMVisibilityStyles
{
	_default,
	_hidden,
	_protected;
	
	public static String parse(LLVMVisibilityStyles visibilityStyle) throws LLVMException
	{
		switch(visibilityStyle)
		{
			case _default:
				return "default";
			case _hidden:
				return "hidden";
			case _protected:
				return "protected";
			default:
				throw new LLVMException("Unknown visibility style. " + visibilityStyle.toString());
		}
	}
}
