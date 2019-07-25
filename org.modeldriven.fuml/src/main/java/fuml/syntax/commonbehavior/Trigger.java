
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

package fuml.syntax.commonbehavior;

public class Trigger extends fuml.syntax.commonstructure.NamedElement {

	public fuml.syntax.commonbehavior.Event event = null;
	public Event getEvent() { return this.event; }
	public void setEvent(fuml.syntax.commonbehavior.Event event) {
		this.event = event;
	} // setEvent

} // Trigger
