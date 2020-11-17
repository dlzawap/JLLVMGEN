package jllvmgen.enums;

import jllvmgen.misc.LLVMException;

public class LLVMCallingConvention
{
	private LLVMCallingConvention cc;
	private Integer number;
	
	
	public enum LLVMCallingConventions
	{
		ccc,
		fastccc,
		coldcc,
		cc_10,
		cc_11,
		webkit_jscc,
		anyreg_cc,
		preserve_mostcc,
		preserve_allcc,
		cxx_fast_tlscc,
		swiftcc,
		tailcc,
		cfguard_checkcc,
		cc; // + number
		
		public static String parse(LLVMCallingConventions cc) throws LLVMException
		{
			switch (cc)
			{
				case ccc:
					return "ccc";
				case fastccc:
					return "fastccc";
				case coldcc:
					return "coldcc";
				case cc_10:
					return "cc 10";
				case cc_11:
					return "cc_11";
				case webkit_jscc:
					return "webkit_jscc";
				case anyreg_cc:
					return "anyreg_cc";
				case preserve_mostcc:
					return "preserve_mostcc";
				case preserve_allcc:
					return "preserve_allc";
				case cxx_fast_tlscc:
					return "cxx_fast_tlscc";
				case swiftcc:
					return "swiftcc";
				case tailcc:
					return "tailcc";
				case cfguard_checkcc:
					return "cfguard_checkcc";
				case cc:
					return "cc";
				default:
					throw new LLVMException("Unknown calling convention. " + cc);
			}
		}
	}
}
