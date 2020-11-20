package jllvmgen.enums;

import jllvmgen.misc.LLVMException;

public enum LLVMLinkageTypes
{
	_private,
	_internal,
	_available_externally,
	_linkonce,
	_weak,
	_common,
	_appending,
	_extern_weak,
	_linkonce_odr,
	_weak_odr,
	_external;
	
	public static String parse(LLVMLinkageTypes linkageType) throws LLVMException
	{
		switch (linkageType)
		{
			case _private:
				return "private";
			case _internal:
				return "internal";
			case _available_externally:
				return "available_externally";
			case _linkonce:
				return "linkonce";
			case _weak:
				return "weak";
			case _common:
				return "common";
			case _appending:
				return "appending";
			case _extern_weak:
				return "extern_weak";
			case _linkonce_odr:
				return "linkonce_odr";
			case _weak_odr:
				return "weak_ord";
			case _external:
				return "external";
		}
		
		throw new LLVMException("Unknown linkage type. Type: " + linkageType);
	}
}
