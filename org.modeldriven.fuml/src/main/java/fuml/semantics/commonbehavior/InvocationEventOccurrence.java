/*
 * Copyright 2015-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.syntax.commonbehavior.Trigger;

public class InvocationEventOccurrence extends EventOccurrence {

	public Execution execution;

	@Override
	public boolean match(Trigger trigger) {
		// An invocation event occurrence does not match any triggers.
		
		return false;
	}

	@Override
	public ParameterValueList getParameterValues() {
		// An invocation event occurrence does not have any associated data.
		
		return new ParameterValueList();
	}
	
}
