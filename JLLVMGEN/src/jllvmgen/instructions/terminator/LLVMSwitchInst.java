package jllvmgen.instructions.terminator;

import jllvmgen.LLVMDataValue;
import jllvmgen.LLVMFunction;
import jllvmgen.instructions.ILLVMBaseInst;
import jllvmgen.misc.KeyValueList;
import jllvmgen.misc.LLVMException;
import jllvmgen.types.LLVMLabelType;

/**
 * The ‘switch’ instruction is used to transfer control flow to one of several different places.
 * It is a generalization of the ‘br’ instruction, allowing a branch to occur to one of many possible destinations.
 */
public class LLVMSwitchInst implements ILLVMBaseInst
{
	private LLVMDataValue value;
	private LLVMLabelType defaultDestination;
	private KeyValueList<LLVMDataValue, LLVMLabelType> comparisonValues;
	
	private LLVMSwitchInst(
			LLVMDataValue value,
			LLVMLabelType defaultDestination,
			KeyValueList<LLVMDataValue, LLVMLabelType> comparisonValues) throws LLVMException
	{
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (defaultDestination == null)
			throw new LLVMException("Parameter \"defaultDestination\" is null or empty.");
		if (comparisonValues == null)
			comparisonValues = new KeyValueList<LLVMDataValue, LLVMLabelType>();
		
		if (!value.getType().isValueType())
			throw new LLVMException("Parameter \"value\" holds data from an invalid value type. "
					+ "Actual data type: " + value.getType().getTypeDefinitionString());
		
		this.value = value;
		this.defaultDestination = defaultDestination;
		this.comparisonValues = comparisonValues;
	}
	
	public void addComparisonValue(LLVMDataValue value, LLVMLabelType destination) throws LLVMException
	{
		if (value == null)
			throw new LLVMException("Parameter \"value\" is null or empty.");
		if (destination == null)
			throw new LLVMException("Parameter \"destination\" is null or empty.");
		if (!value.isValueOnly())
			throw new LLVMException("Values only data types are allowed on switch instructions."
					+ "Actual identifier: " + value.getIdentifierOrValue());
		
		for (int i = 0; i < comparisonValues.getCount(); ++i)
		{
			if (comparisonValues.keyAt(i).equals(value))
				throw new LLVMException("Double defined comparison values are not allowed."
						+ "Value: " + value.getIdentifierOrValue());
		}
		
		comparisonValues.add(value, destination);
	}
	
	@Override
	public String getInstructionString() throws LLVMException 
	{
		StringBuilder sb = new StringBuilder("switch ");
		sb.append(value.getType().getTypeDefinitionString());
		sb.append(' ');
		sb.append(value.getIdentifierOrValue());
		sb.append(", label ");
		sb.append(defaultDestination.getIdentifier());
		sb.append(" [ ");
		
		for (int i = 0; i < comparisonValues.getCount(); ++i)
		{
			var value = comparisonValues.keyAt(i);
			var label = comparisonValues.valueAt(i);
			
			sb.append("\n\t").append(value.getType().getTypeDefinitionString())
			  .append(" ").append(value.getValue()).append(", label ")
			  .append(label.getIdentifier());
		}
		
		sb.append(" ] ");
		
		return sb.toString();
	}
	
	/*
	 * Factory functions.
	 */
	
	public static LLVMSwitchInst create(
			LLVMFunction fn,
			LLVMDataValue value,
			LLVMLabelType defaultDestination) throws LLVMException
	{
		var instruction = new LLVMSwitchInst(value, defaultDestination, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMSwitchInst create(
			LLVMFunction fn,
			LLVMDataValue value,
			LLVMLabelType defaultDestination,
			KeyValueList<LLVMDataValue, LLVMLabelType> comparisonValues) throws LLVMException
	{
		var instruction = new LLVMSwitchInst(value, defaultDestination, comparisonValues);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			fn.registerInstruction(instruction);
		
		return instruction;
	}

	public static LLVMSwitchInst createConditional(
			LLVMFunction fn,
			LLVMLabelType parentLabelType,
			LLVMDataValue value,
			LLVMLabelType defaultDestination) throws LLVMException
	{
		var instruction = new LLVMSwitchInst(value, defaultDestination, null);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	public static LLVMSwitchInst createConditional(
			LLVMFunction fn,
			LLVMLabelType parentLabelType,
			LLVMDataValue value,
			LLVMLabelType defaultDestination,
			KeyValueList<LLVMDataValue, LLVMLabelType> comparisonValues) throws LLVMException
	{
		var instruction = new LLVMSwitchInst(value, defaultDestination, comparisonValues);
		
		// Register instruction if automatic registration is enabled.
		if (fn.autoRegisterInstructions())
			parentLabelType.registerInstruction(instruction);
		
		return instruction;
	}
	
	/*
	 * End of factory functions.
	 */
}
