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

package timedfuml.semantics.Loci;

import fuml.semantics.loci.Locus;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.syntax.commonbehavior.Behavior;

import fuml.syntax.structuredclassifiers.Class_;

import timedfuml.semantics.StructuredClassifiers.TimedObject;

public class TimedLocus extends Locus {

	public Object_ instantiate(Class_ type) {
		// Behaves like in fUML except that type instance are not
		// Object_ but TimedObject.
		Object_ object = null;
		if (type instanceof Behavior) {
			object = super.instantiate(type);
		} else {
			object = new TimedObject();
			object.addType(type);
			object.createFeatureValues();
			this.add(object);
		}
		return object;
	}
	
}
