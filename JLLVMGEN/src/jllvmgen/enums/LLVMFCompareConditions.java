package jllvmgen.enums;

public enum LLVMFCompareConditions
{
	/**
	 * no comparison, always returns false
	 */
	_false,
	/**
	 * ordered and equal
	 */
	oeq,
	/**
	 * ordered and greater than
	 */
	ogt,
	/**
	 * ordered and greater than or equal
	 */
	oge,
	/**
	 * ordered and less than
	 */
	olt,
	/**
	 * ordered and less than or equal
	 */
	ole,
	/**
	 * ordered and not equal
	 */
	one,
	/**
	 * ordered (no nans)
	 */
	ord,
	/**
	 * unordered or equal
	 */
	ueq,
	/**
	 * unordered or greater than
	 */
	ugt,
	/**
	 * unordered or greater than or equal
	 */
	uge,
	/**
	 * unordered or less than
	 */
	ult,
	/**
	 * unordered or less than or equal
	 */
	ule,
	/**
	 * unordered or not equal
	 */
	une,
	/**
	 * unordered (either nans)
	 */
	uno,
	/**
	 * no comparison, always returns true
	 */
	_true;
	
	public static String parse(LLVMFCompareConditions fCompareCond)
	{
		switch (fCompareCond)
		{
			case _false:
				return "false";
			case _true:
				return "true";
			default:
				return fCompareCond.toString();
		}
	}
}
