/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST Initial API and implementation
 *   
 *****************************************************************************/

package timedfuml.actions;

import discreteevent.DEScheduler;
import discreteevent.actions.Action;
import fuml.semantics.loci.SemanticVisitor;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.semantics.structuredclassifiers.Reference;

import timedfuml.semantics.CommonBehaviors.TimedEventOccurrence;

public class _sendTimeEventOccurrence extends Action {

	// Time instant at which the timeout was registered
	protected double referenceInstant;
	
	// Target to which a time event is sent
	protected Object_ target;
	
	// Visitor that implied the timer setup
	protected SemanticVisitor visitor;
	
	public _sendTimeEventOccurrence(double referenceInstant, SemanticVisitor visitor, Object_ target) {
		this.referenceInstant = referenceInstant;
		this.visitor = visitor;
		this.target = target;
	}
	
	public SemanticVisitor getVisitor() {
		return this.visitor;
	}
	
	
	@Override
	public void execute() {
		// Register a time event occurrence to the target object. This
		// enables the target object classifier behavior to react (if possible)
		// to the fact that clock time has evolved.
		TimedEventOccurrence eventOccurrence = new TimedEventOccurrence();
		eventOccurrence.setReferenceInstant(this.referenceInstant);
		eventOccurrence.setOccurrenceInstant(DEScheduler.getInstance().getCurrentTime());
		Reference targetReference = new Reference();
		targetReference.setReferent(this.target);
		eventOccurrence.setTarget(targetReference);
		eventOccurrence.register();
	}

}
