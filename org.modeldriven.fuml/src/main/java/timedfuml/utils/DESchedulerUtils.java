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
package timedfuml.utils;

import discreteevent.DEScheduler;
import discreteevent.Event;
import fuml.semantics.loci.SemanticVisitor;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.semantics.values.Evaluation;
import fuml.semantics.values.Value;
import fuml.syntax.commonbehavior.TriggerList;
import fuml.syntax.commonbehavior.Trigger;

import org.eclipse.uml2.uml.TimeEvent;
//import org.eclipse.uml2.uml.Trigger;
import timedfuml.actions._sendTimeEventOccurrence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DESchedulerUtils {

	public static boolean isTimeTriggered(TriggerList triggers) {
		// Determine if there is at least a trigger that has a TimeEvent.
		// Return true if one trigger can be found false otherwise
		boolean timeTriggered = false;
		Iterator<Trigger> triggerIterator = triggers.iterator();
		while(!timeTriggered && triggerIterator.hasNext()) {
			timeTriggered = triggerIterator.next().getEvent() instanceof TimeEvent;
		}
		return timeTriggered;
	}
	
	public static void pushEvents(TriggerList triggers, SemanticVisitor visitor, Object_ context) {
		// Register timers for triggers that may accept a time event in the future.
		// When the timer will fire the context object will receive a time event
		// occurrence.
		for (Trigger trigger : triggers) {
			pushEvent(trigger, visitor, context);
		}
	}

	private static void pushEvent(Trigger trigger, SemanticVisitor visitor, Object_ context) {
		// Register a timer for a trigger associated to a time event. The specification
		// of the timer consists in an event registered to the DEScheduler. This event is time 
		// stamped with the instant at which the timer shall fire.
		if (context != null && trigger != null && trigger.getEvent() instanceof TimeEvent) {
			TimeEvent timeEvent = (TimeEvent) trigger.getEvent();
			if (timeEvent.getWhen() != null && timeEvent.getWhen().getExpr() != null) {
				Evaluation evaluation = context.getLocus().getFactory()
						.createEvaluation(timeEvent.getWhen().getExpr());
				if(evaluation != null) {
					if(evaluation instanceof OpaqueExpressionEvaluation) {
						((OpaqueExpressionEvaluation)evaluation).setContext(context);
					}
					Value value = evaluation.evaluate();
					if(value != null && value instanceof RealValue) {
						double clockTime = DEScheduler.getInstance().getCurrentTime();
						Event clockEvent = new Event((double)((RealValue)value).getValue(), new _sendTimeEventOccurrence(clockTime, visitor, context));
						if(timeEvent.isRelative()){
							DEScheduler.getInstance().pushEvent(clockEvent);
						}else {
							DEScheduler.getInstance().pushEvent(clockEvent, ((RealValue)value).getValue());
						}
					}
				}
			}
		}
	}
	
	public static void cancelEvents(SemanticVisitor visitor) {
		// Cancel all timers that may have been installed by the semantic
		// visitors that was fired by another event.
		List<Event> toRemoveEvents = new ArrayList<Event>();
		List<Event> schedulerEvents = DEScheduler.getInstance().getEvents();
		for(Event event : schedulerEvents) {
			if(event.getAction() instanceof _sendTimeEventOccurrence
					&& ((_sendTimeEventOccurrence)event.getAction()).getVisitor() == visitor) {
				toRemoveEvents.add(event);
			}
		}
		DEScheduler.getInstance().removeAllEvents(toRemoveEvents);
	}
}
