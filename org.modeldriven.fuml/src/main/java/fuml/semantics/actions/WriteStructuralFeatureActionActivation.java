
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

public abstract class WriteStructuralFeatureActionActivation
		extends
		fuml.semantics.actions.StructuralFeatureActionActivation {

	public int position(fuml.semantics.values.Value value,
			fuml.semantics.values.ValueList list, int startAt) {
		// Return the position (counting from 1) of the first occurance of the
		// given value in the given list at or after the starting index, or 0 if
		// it is not found.

		boolean found = false;
		int i = startAt;
		while (!found & i <= list.size()) {
			found = list.getValue(i - 1).equals(value);
			i = i + 1;
		}

		if (!found) {
			i = 1;
		}

		return i - 1;
	} // position

} // WriteStructuralFeatureActionActivation
