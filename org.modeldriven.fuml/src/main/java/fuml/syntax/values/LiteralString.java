
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

package fuml.syntax.values;

public class LiteralString extends
		fuml.syntax.values.LiteralSpecification {

	public String value = "";

	public void setValue(String value) {
		this.value = value;
	} // setValue

} // LiteralString
