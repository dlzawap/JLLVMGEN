package jllvmgen.enums;

public enum LLVMRuntimePreemptionSpecifiers {
	/**
	 * Indicates that the function or variable may be replaced by a symbol from outside the linkage unit at runtime.
	 */
	dso_preemptable,
	/**
	 * The compiler may assume that a function or variable marked as dso_local will resolve to a symbol within the same linkage unit. Direct access will be generated even if the definition is not within this compilation unit.
	 */
	dso_local
}
