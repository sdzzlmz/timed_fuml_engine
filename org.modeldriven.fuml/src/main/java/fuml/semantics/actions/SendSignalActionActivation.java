/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.semantics.commonbehavior.SignalEventOccurrence;
import fuml.semantics.simpleclassifiers.SignalInstance;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.InputPin;
import fuml.syntax.actions.InputPinList;
import fuml.syntax.actions.SendSignalAction;
import fuml.syntax.classification.Property;
import fuml.syntax.classification.PropertyList;
import fuml.syntax.simpleclassifiers.Signal;

public class SendSignalActionActivation extends
		fuml.semantics.actions.InvocationActionActivation {

	public void doAction() {
		// Get the value from the target pin. If the value is not a reference,
		// then do nothing.
		// Otherwise, construct a signal using the values from the argument pins
		// and send it to the referent object.

		SendSignalAction action = (SendSignalAction) (this.node);
		Value target = this.takeTokens(action.target).getValue(0);

		if (target instanceof Reference) {
			Signal signal = action.signal;

			SignalInstance signalInstance = new SignalInstance();
			signalInstance.type = signal;

			PropertyList attributes = signal.ownedAttribute;
			InputPinList argumentPins = action.argument;
			for (int i = 0; i < attributes.size(); i++) {
				Property attribute = attributes.getValue(i);
				InputPin argumentPin = argumentPins.getValue(i);
				ValueList values = this.takeTokens(argumentPin);
				signalInstance.setFeatureValue(attribute, values, 0);
			}

			SignalEventOccurrence signalEventOccurrence = new SignalEventOccurrence();
			signalEventOccurrence.signalInstance = (SignalInstance) signalInstance.copy();
			signalEventOccurrence.sendTo((Reference)target);
		}
	} // doAction

} // SendSignalActionActivation
