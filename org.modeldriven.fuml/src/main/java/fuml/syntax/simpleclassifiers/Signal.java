
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

package fuml.syntax.simpleclassifiers;

public class Signal extends fuml.syntax.classification.Classifier {

	public fuml.syntax.classification.PropertyList ownedAttribute = new fuml.syntax.classification.PropertyList();

	public void addOwnedAttribute(
			fuml.syntax.classification.Property ownedAttribute) {
		super.addAttribute(ownedAttribute);
		super.addOwnedMember(ownedAttribute);

		this.ownedAttribute.addValue(ownedAttribute);
	} // addOwnedAttribute

} // Signal
