/*
 * Copyright 2017 Data Access Technologies, Inc., except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.syntax.classification.Operation;
import fuml.syntax.commonbehavior.CallEvent;
import fuml.syntax.commonbehavior.Trigger;

public class CallEventOccurrence extends EventOccurrence {
	
	public CallEventExecution execution = null;
	
	public Operation getOperation() {
		// Get the operation being called by this call event occurrence.
		
		return this.execution.getOperation();
	}

	@Override
	public boolean match(Trigger trigger) {
		// Match a trigger if it references a call event whose operation is the
		// operation of this call event occurrence.
		
		boolean matches = false;
		if (trigger.event instanceof CallEvent) {
			CallEvent callEvent = (CallEvent)trigger.event;
			matches = callEvent.operation == this.getOperation();
		}
		return matches;
	}

	@Override
	public ParameterValueList getParameterValues() {
		// Return the input parameter values from the call event execution for
		// this call event occurrence, which correspond to the values of the
		// operation input parameters for the call.
		
		return this.execution.getInputParameterValues();
	}
	
	public void setOutputParameterValues(ParameterValueList parameterValues) {
		// Set the output parameter values of the call event execution for
		// this call event occurrence, which correspond to the values of the
		// operation output parameters for the call.

		this.execution.setOutputParameterValues(parameterValues);
	}
	
	public void returnFromCall() {
		// Release the caller on return from the call.
		
		this.execution.releaseCaller();
	}

}
