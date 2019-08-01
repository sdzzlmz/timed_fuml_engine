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
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.loci.Locus;
import fuml.semantics.values.Value;

import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.ClassifierList;
import fuml.syntax.commonbehavior.Behavior;
import org.eclipse.uml2.uml.Class;

import java.util.ArrayList;
import java.util.List;

public class RootExecution extends Execution {

	// Class to be executed
	protected Class executedClass;

	public RootExecution() {
	}

	public RootExecution(Class executedClass, ParameterValueList inputParameterValues, Locus locus) {
		this.executedClass = executedClass;
		this.setInputParameterValues(inputParameterValues);
		this.locus = locus;
	}

	public void setInputParameterValues(ParameterValueList inputParameterValues) {
		// Set input parameter values of the root execution
		for (ParameterValue inputParameterValue : inputParameterValues) {
			this.setParameterValue(inputParameterValue);
		}
	}

	@Override
	public void execute() {
		// If the class is active then it is instantiated and
		// started on its own thread of execution. Conversely,
		// if it is not active then it shall be a behavior. In
		// such situation the behavior gets executed otherwise
		// this operation has no effect.
		if (this.executedClass.isActive()) {
			this.locus.getExecutor().start((fuml.syntax.structuredclassifiers.Class_)executedClass, this.parameterValues);
		} else if (this.executedClass instanceof Behavior) {
			ParameterValueList outputParameterValues = this.locus.getExecutor()
					.execute((Behavior) this.executedClass, null, this.parameterValues);
			for (ParameterValue parameterValue : outputParameterValues) {
				this.setParameterValue(parameterValue);
			}
		}
	}

	@Override
	public Value new_() {
		return new RootExecution();
	}

	@Override
	public ClassifierList getTypes() {
		ClassifierList types = new ClassifierList();
		types.add((Classifier) this.executedClass);
		return types;
	}

	@Override
	public String toString() {
		return "RootExecution()";
	}
}
