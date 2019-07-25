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
 * 	Sahar Guermazi
 *  CEA LIST Initial API and implementation
 *  
 *****************************************************************************/
package timedfuml.semantics.Loci;

import fuml.semantics.loci.ExecutionFactory;
import fuml.semantics.loci.SemanticVisitor;
import fuml.syntax.actions.AcceptCallAction;
import fuml.syntax.actions.AcceptEventAction;
import fuml.syntax.actions.SendSignalAction;
import fuml.syntax.commonstructure.Element;


import org.eclipse.uml2.uml.OpaqueAction;
import timedfuml.semantics.Actions.CompleteActions.TimedAcceptEventActionActivation;

import timedfuml.semantics.Timed_OpaqueActionActivation;
import timedfuml.semantics.Timed_SendSignalActionActivation;
import timedfuml.utils.DESchedulerUtils;

public class TimedExecutionFactory extends ExecutionFactory {

	@Override
	public SemanticVisitor instantiateVisitor(Element element) {
		// Extends fUML semantics in the sense that newly introduced
		// semantic visitors are instantiated instead of fUML visitors
		SemanticVisitor visitor = null;
		if (element instanceof AcceptEventAction 
				&& !(element instanceof AcceptCallAction)
				&& DESchedulerUtils.isTimeTriggered(((AcceptEventAction)element).getTriggers())) {
			visitor = new TimedAcceptEventActionActivation();
		} else if (element instanceof OpaqueAction) {
			visitor = new Timed_OpaqueActionActivation();
		} else if (element instanceof SendSignalAction) {
			visitor = new Timed_SendSignalActionActivation();
		}
		// Not taking stateMachineExecution into consideration temporarily,
//		else if(element instanceof StateMachine){
//			visitor = new TimedStateMachineExecution();
//		}
		else {
			visitor = super.instantiateVisitor(element);
		}
		return visitor;
	}

}
