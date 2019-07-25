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

import fuml.semantics.commonbehavior.ObjectActivation;


public class TimedObjectActivation extends ObjectActivation {


	public void register(TimedEventOccurrence timedEventOccurrence) {
		// Add the timed event occurrence at the end of the event pool.
		// Notify that that a new event occurrence was placed in the event pool.
		this.eventPool.add(timedEventOccurrence);
		this.notifyEventArrival();
	}

}
