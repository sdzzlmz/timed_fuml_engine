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

import fuml.semantics.commonbehavior.EventOccurrence;
import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.values.Value;


public class EventOccurrenceSendingExecution extends Execution {

	// Event occurrence that is required to be sent
	public EventOccurrence self;

	public void execute() {
		// Send the event occurrence to the etarget object
		this.self.doSend();
	}


	public Value new_() {
		EventOccurrenceSendingExecution sendingExecution = new EventOccurrenceSendingExecution();
		sendingExecution.self = this.self;
		return sendingExecution;
	}


	public String toString() {
		return "EventOccurrenceSendingExecution("+this.self+")";
	}
}