/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package timedfuml.semantics;

import fuml.semantics.actions.SendSignalActionActivation;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;


import fuml.syntax.actions.InputPin;
import org.eclipse.uml2.uml.SendSignalAction;

import java.util.List;

public class Timed_SendSignalActionActivation extends SendSignalActionActivation {

	public ValueList takeTokens(InputPin pin) {
		// FIXME generalize this for the SIMEX profile
		// Deals with "implicit this"
		ValueList values = super.takeTokens(pin);
		if (values.isEmpty()) {
			// check if the pin is the target pin,
			// and automatically adds a reference to the context object
			SendSignalAction sendSignalAction = (SendSignalAction) this.node;
			if (sendSignalAction.getTarget() == pin) {
				Reference targetReference = new Reference();
				Value target = this.getExecutionContext();
				targetReference.setReferent((Object_) target);
//				targetReference.setCompositeReferent((Object) target);
				values.add(targetReference);
			}
		}
		return values;
	}
}
