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

import discreteevent.DEScheduler;
import discreteevent.Event;
import discreteevent.actions.Action;
import fuml.semantics.actions.ActionActivation;
import fuml.syntax.values.LiteralReal;


import org.eclipse.uml2.uml.Duration;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationInterval;
import timedfuml.actions._sendOfferAction;

/**
 * @author sg239226
 *
 */
public class Timed_OpaqueActionActivation extends ActionActivation {

	public Timed_OpaqueActionActivation() {
		super();
	}

	public void sendOffers() {
		// FIXME control delegation

		if (((org.eclipse.uml2.uml.Action) node).getLocalPreconditions() != null) {
			double duration = 0.0;
			DurationConstraint durationConstraint = (DurationConstraint) ((org.eclipse.uml2.uml.Action) node).getLocalPostconditions().get(0);
			DurationInterval durationInterval = (DurationInterval) durationConstraint.getSpecification();
			LiteralReal durationValue = (LiteralReal) ((Duration) durationInterval.getMax()).getExpr();
			_sendOfferAction sendOfferAction = new _sendOfferAction(this);
			// double duration = 0.0 ;
			if (durationValue instanceof LiteralReal) {
				duration = ((LiteralReal) durationValue).getValue();
			}
			this.suspend();
			DEScheduler.getInstance().pushEvent(new Event(duration, (Action)sendOfferAction));
		} else {
			super.sendOffers();
		}
	}

	// public CentralBufferNodeActivation getSourceNode() {
	// // Makes the hypothesis that there is only one incoming edge,
	// // and the source of this edge is a CentralBufferNodeActivation
	// CentralBufferNodeActivation sourceNode = null ;
	// if (this.incomingEdges.size() > 0) {
	// sourceNode = (CentralBufferNodeActivation)this.incomingEdges.get(0).source ;
	// }
	// return sourceNode ;
	// }

	public void sendOffersDefault() {
		this.resume();
		super.sendOffers();
	}

//	/**
//	 * @see org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.ActionActivation#doAction()
//	 *
//	 */
	@Override
	public void doAction() {
		// Do nothing
	}
}
