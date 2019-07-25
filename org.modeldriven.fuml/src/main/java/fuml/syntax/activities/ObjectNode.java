
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

package fuml.syntax.activities;

public abstract class ObjectNode extends
		fuml.syntax.activities.ActivityNode {

	public fuml.syntax.commonstructure.TypedElement typedElement = new fuml.syntax.commonstructure.TypedElement();

	public void setType(fuml.syntax.commonstructure.Type type) {
		this.typedElement.type = type;
	} // setType

} // ObjectNode
