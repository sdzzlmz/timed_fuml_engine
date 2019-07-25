/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
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
 *
 *****************************************************************************/

package timedfuml.semantics.StructuredClassifiers;


import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.syntax.structuredclassifiers.Class_;

import timedfuml.semantics.CommonBehaviors.TimedObjectActivation;

import java.util.List;

public class TimedObject extends Object_ {

	@Override
	public void startBehavior(Class_ classifier, ParameterValueList inputs) {
		///The behavior captured here is almost identical to the one provide by SM_Object.
		// Instead of using a simple ObjectActivation we use a TimedObjectActivation.
		// This specialized kind of ObjectActivation allows the registering of time events.
		if (this.objectActivation == null) {
			this.objectActivation = new TimedObjectActivation();
			this.objectActivation.setObject(this);
		}
		this.objectActivation.startBehavior(classifier, inputs);
	}
	
}
