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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.modeldriven.fuml.control.execution;

import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.commonbehavior.ObjectActivation;
import fuml.semantics.values.Value;

import org.modeldriven.fuml.control.queue.ExecutionController;

public class EventDispatchLoopExecution extends Execution {

	// The object activation that handles this event dispatch loop
	public ObjectActivation self;
	
	// The number of events to be dispatched in the next iterations
	// of the event dispatch loop
	private int signalCount = 0;
	
	public void newSignalArrival(){
		// Notify the dispatch loop about the arrival of a new events
		// at the event pool. Request a new RTC step to be performed
		// if this is the first event. This request is encoded by enqueuing
		// the event dispatch loop execution to the execution queue manager.
		this.signalCount = this.signalCount + 1;
		if(this.signalCount == 1){
			ExecutionController.getInstance().getExecutionLoop().enqueue(this);
		}
	}
	
	public void dispatchNextEvent(){
		// Perform a RTC step
		this.self.dispatchNextEvent();
	}

	@Override
	public void execute() {
		// Dispatch the next event and if other event remain to be dispatched
		// then request for another RTC to be performed by enqueuing the event
		// dispatch loop execution to the execution manager.
		this.dispatchNextEvent();
		this.signalCount = this.signalCount - 1;
		if(this.signalCount > 0){
			ExecutionController.getInstance().getExecutionLoop().enqueue(this);
		}
	}

	@Override
	public Value new_() {
		EventDispatchLoopExecution dispatchLoopExecution = new EventDispatchLoopExecution();
		dispatchLoopExecution.self = this.self;
		return dispatchLoopExecution;
	}
	
	@Override
	public String toString() {
		return "EventDispatchLoopExecution("+this.objectActivation+")";
	}
	
}

