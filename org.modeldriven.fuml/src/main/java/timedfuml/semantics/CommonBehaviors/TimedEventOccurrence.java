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

package timedfuml.semantics.CommonBehaviors;

import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.ValueList;
import fuml.syntax.values.ValueSpecification;
import fuml.semantics.commonbehavior.EventOccurrence;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.syntax.commonbehavior.Trigger;
import fuml.semantics.values.Evaluation;



import org.eclipse.uml2.uml.TimeEvent;


import java.util.ArrayList;
import java.util.List;

public class TimedEventOccurrence extends EventOccurrence {

	// Time at which the clock accounted for the future
	// time event occurrence
	public RealValue referenceInstant;

	// Instant of time at which this event occurrence
	// was generated
	public RealValue occurrenceInstant;


	public boolean match(Trigger trigger) {
		// Define the rule to match this event occurrence against a trigger
		// This event occurrence matches if the following condition hold:
		// 1] If the trigger is for a TimeEvent
		// 2] If the time expression can be evaluated
		// 3] If the occurrence instant (i.e., the time at which this occurrence
		// occurred) matches the time at which the model element referencing the
		// trigger was expected to fire. Note that the triggering time can either
		// relative or absolute.
		// TODO matches = false at first
		boolean matches = true;
		if (trigger.getEvent() instanceof TimeEvent) {
			TimeEvent timeEvent = (TimeEvent) trigger.getEvent();
			if (timeEvent.getWhen() != null && timeEvent.getWhen().getExpr() != null) {
				Evaluation evaluation = this.target.getReferent().getLocus().getFactory()
						.createEvaluation(timeEvent.getWhen().getExpr());
				// TODO the matching process
//				if (evaluation != null) {
//					if (evaluation instanceof ISM_OpaqueExpressionEvaluation) {
//						((ISM_OpaqueExpressionEvaluation) evaluation).setContext(this.target.getReferent());
//					}
//					IRealValue evaluatedInstant = (IRealValue) evaluation.evaluate();
//					if (timeEvent.isRelative()) {
//						matches = this.referenceInstant.getValue()
//								+ evaluatedInstant.getValue() == this.occurrenceInstant.getValue();
//					} else {
//						matches = this.occurrenceInstant.equals(evaluatedInstant);
//					}
//				}
			}
		}
		return matches;
	}

	@Override
	public ParameterValueList getParameterValues() {
		// Return a single parameter value. This latter provides the time
		// at which this event occurrence occurred.
		ParameterValueList parameterValues = new ParameterValueList();
		ParameterValue parameterValue = new ParameterValue();
		ValueList values = new ValueList();
		values.add(this.occurrenceInstant);
		parameterValue.setValues(values);
		parameterValues.add(parameterValue);
		return parameterValues;
	}

	@Override
	public void sendTo(Reference target) {
		// Do nothing - the timed event is not sent to a target.
		// It is registered by an external clock into the event pool
		// of the active object.
	}

	@Override
	public void doSend() {
		// Do nothing - the timed event is not sent to a target.
		// It is registered by an external clock into the event pool
		// of the active object.
	}


	public void _startObjectBehavior() {
		// Do nothing - the classifier behavior of that object is empty
	}

	public void register() {
		// Register this time event occurrence in the event
		// pool of the target object.
		TimedObjectActivation objectActivation = (TimedObjectActivation) this.target.getReferent()
				.getObjectActivation();
		if(objectActivation != null) {
			objectActivation.register(this);
		}
	}


	public void setReferenceInstant(double instant) {
		// Set the reference time at which the production of this
		// event occurrence was scheduled
		if (this.referenceInstant == null) {
			this.referenceInstant = new RealValue();
		}
		this.referenceInstant.setValue((float)instant);
	}


	public RealValue getReferenceInstance() {
		// Return the reference time (can be null)
		return this.referenceInstant;
	}


	public void setOccurrenceInstant(double instant) {
		// Set the time at which this event occurrence actually
		// occurred.
		if (this.occurrenceInstant == null) {
			this.occurrenceInstant = new RealValue();
		}
		this.occurrenceInstant.setValue((float) instant);
	}


	public RealValue getOccurrenceInstant() {
		// Return the time at which the occurrence occurred (can be null).
		return this.occurrenceInstant;
	}

}
