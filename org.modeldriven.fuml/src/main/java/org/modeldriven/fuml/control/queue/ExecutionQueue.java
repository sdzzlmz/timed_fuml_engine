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

package org.modeldriven.fuml.control.queue;

import fuml.semantics.commonbehavior.Execution;


import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;

public final class ExecutionQueue extends AbstractQueue<Execution> {

	// The pool of execution maintained by the queue
	private ArrayList<Execution> executionPool;
	
	public ExecutionQueue(){
		this.executionPool = new ArrayList<Execution>();
	}
	
	public boolean offer(Execution e) {
		// The provided execution is always added at the end of the queue.
		// This operation returns false if the provided execution is null
		// and true otherwise.
		if(e == null){
			return false;
		}
		this.executionPool.add(e);
		return true;
	}

	public Execution poll() {
		// Retrieves and remove the head of the queue. Null is returned if
		// queue is empty.
		if(this.executionPool.isEmpty()){
			return null;
		}
		return this.executionPool.remove(0);
	}

	public Execution peek() {
		// Retrieve queue head. Null is returned if the queue is empty.
		// Head is not removed from the list.
		if(this.executionPool.isEmpty()){
			return null;
		}
		return this.executionPool.get(0);
	}

	@Override
	public Iterator<Execution> iterator() {
		// Return an iterator over the underlying execution pool
		return this.executionPool.iterator();
	}

	@Override
	public int size() {
		// Return size of the the underlying execution pool
		return this.executionPool.size();
	}

}