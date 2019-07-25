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
package timedfuml;

import discreteevent.DEScheduler;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.loci.Executor;
import fuml.semantics.loci.Locus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_Executor;
import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.control.execution.RootExecution;
import org.eclipse.papyrus.moka.fuml.control.queue.ExecutionController;
import org.eclipse.papyrus.moka.fuml.statemachines.StateMachineExecutionEngine;
import org.eclipse.papyrus.moka.timedfuml.actions._displayCurrentTimeAction;
import org.eclipse.papyrus.moka.timedfuml.control.queue.TimedExecutionLoop;
import org.eclipse.papyrus.moka.timedfuml.semantics.Loci.TimedExecutionFactory;
import org.eclipse.papyrus.moka.timedfuml.semantics.Loci.TimedLocus;
import timedfuml.actions._displayCurrentTimeAction;
import timedfuml.semantics.Loci.TimedExecutionFactory;
import timedfuml.semantics.Loci.TimedLocus;

import java.util.List;

public class TimedUmlExecutionEngine {
	protected  Locus locus;
	protected List<ParameterValue> executionArguments;
	protected double getStopTime() {
		// Scheduler stop time
		return -1.0;
	}
	
	protected void initDEScheduler(){
		// Initialize the scheduler
		DEScheduler.init(this.getStopTime());
	}
	
	protected void doPreRunActions() {
		// This method can be overridden to perform pre-run initializations that can be
		// needed for a given customization. Typically useful to register pre-step
		// actions to the DEScheduler
		DEScheduler.getInstance().pushPreStepAction(new _displayCurrentTimeAction());
	}

	protected void doPostRunActions() {
		// This method can be overridden to perform post-run finalization that can be
		// needed for a given customization.
	}
	

	public Locus initializeLocus() {
		this.locus = new TimedLocus();
		locus.setExecutor(new Executor());
		locus.setFactory(new TimedExecutionFactory());
		return this.locus;
	}
	

	protected void run_() {
		// Starts the execution loop
		RootExecution rootExecution = new RootExecution((Class)this.executionEntryPoint, this.executionArguments, locus);
		ExecutionController.getInstance().setExecutionLoop(new TimedExecutionLoop());
		ExecutionController.getInstance().start(rootExecution);
	}
	

	public void start(ProgressMonitor monitor) {
		// we start the default fUML* execution but we know it should finish quick,
		// once all the Externally controlled visitors are suspended.
		this.initDEScheduler();
		this.doPreRunActions();
		super.start(monitor);
		this.doPostRunActions();
	}

}
