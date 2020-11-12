package jllvmgen.enums;

public enum LLVMFastMathFlags
{
	/**
	 * No NaNs - Allow optimizations to assume the arguments and result are not NaN. If an argument is a nan, or the result would be a nan, it produces a poison value instead.
	 */
	nnan,
	/**
	 * No Infs - Allow optimizations to assume the arguments and result are not +/-Inf. If an argument is +/-Inf, or the result would be +/-Inf, it produces a poison value instead.
	 */
	ninf,
	/**
	 * No Signed Zeros - Allow optimizations to treat the sign of a zero argument or result as insignificant. This does not imply that -0.0 is poison and/or guaranteed to not exist in the operation.
	 */
	nsz,
	/**
	 * Allow Reciprocal - Allow optimizations to use the reciprocal of an argument rather than perform division.
	 */
	arcp,
	/**
	 * Allow floating-point contraction (e.g. fusing a multiply followed by an addition into a fused multiply-and-add). This does not enable reassociating to form arbitrary contractions. For example, (a*b) + (c*d) + e can not be transformed into (a*b) + ((c*d) + e) to create two fma operations.
	 */
	contract,
	/**
	 * Approximate functions - Allow substitution of approximate calculations for functions (sin, log, sqrt, etc). See floating-point intrinsic definitions for places where this can apply to LLVM’s intrinsic math functions.
	 */
	afn,
	/**
	 * Allow reassociation transformations for floating-point instructions. This may dramatically change results in floating-point.
	 */
	reassoc,
	/**
	 * This flag implies all of the others.
	 */
	fast
}
