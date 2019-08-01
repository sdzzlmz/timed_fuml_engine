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

import UMLPrimitiveTypes.UMLPrimitiveTypesUtils;
import discreteevent.DEScheduler;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.loci.Executor;
import fuml.semantics.loci.Locus;

import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.IntegerValue;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterList;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.commonstructure.Type;
import fuml.syntax.simpleclassifiers.PrimitiveType;
import org.eclipse.emf.ecore.EObject;

import fuml.syntax.classification.ParameterDirectionKind;
import org.modeldriven.fuml.control.execution.RootExecution;
import org.modeldriven.fuml.control.queue.ExecutionController;
import timedfuml.actions._displayCurrentTimeAction;
import timedfuml.control.queue.TimedExecutionLoop;
import timedfuml.semantics.Loci.TimedExecutionFactory;
import timedfuml.semantics.Loci.TimedLocus;
import org.eclipse.uml2.uml.Class;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class TimedUmlExecutionEngine {
	protected EObject  executionEntryPoint;
	protected  Locus locus;
	protected ParameterValueList executionArguments;
	protected String[] executionArgs;
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
	

//	public void start(ProgressMonitor monitor) {
	public void start() {
		// we start the default fUML* execution but we know it should finish quick,
		// once all the Externally controlled visitors are suspended.
		this.initDEScheduler();
		this.doPreRunActions();
//		super.start(monitor);
		this.locus = this.initializeLocus();
		if (this.executionEntryPoint != null) {
			// initializes built-in primitive types
			this.initializeBuiltInPrimitiveTypes(locus);
			// Initializes opaque behavior executions
//			this.registerOpaqueBehaviorExecutions(locus);
			// Initializes system services
//			this.registerSystemServices(locus);
			// Initializes semantic strategies
//			this.registerSemanticStrategies(locus);
			// Initializes arguments
			this.initializeArguments(this.executionArgs);
			// Start execution
			this.run_();
		}
		this.doPostRunActions();
	}
	protected void initializeBuiltInPrimitiveTypes(Locus locus) {
		locus.getFactory().addBuiltInType(UMLPrimitiveTypesUtils.getReal(this.executionEntryPoint));
		locus.getFactory().addBuiltInType(UMLPrimitiveTypesUtils.getInteger(this.executionEntryPoint));
		locus.getFactory().addBuiltInType(UMLPrimitiveTypesUtils.getBoolean(this.executionEntryPoint));
		locus.getFactory().addBuiltInType(UMLPrimitiveTypesUtils.getString(this.executionEntryPoint));
	}
	public void initializeArguments(String[] args) {
		if (this.locus == null) {
			return;
		}
		this.executionArguments = new ParameterValueList();
		if (args == null) {
			return;
		}
		ValueList tmpArgs = new ValueList();
		if (! (this.executionEntryPoint instanceof Behavior)) {
			return ;
		}
		// analyzes arguments versus parameters of the main behavior
		ParameterList parameters = ((Behavior) this.executionEntryPoint).getOwnedParameters();
		if (parameters == null) {
			return;
		}
		ParameterList parametersWhichNeedArguments = new ParameterList();
		// There must be the same number of parameters (except the return parameter)
		for (Parameter p : parameters) {
			if (p.getDirection() != ParameterDirectionKind.return_) {
				parametersWhichNeedArguments.add(p);
			}
		}
		if (parametersWhichNeedArguments.size() != args.length) {
			return;
		}

		// iterates on parameters, and tries to create tokens corresponding to arguments
		int i = 0;
		for (Parameter p : parametersWhichNeedArguments) {
			Type t = p.getType();
			if (t != null) {
				// FIXME
				PrimitiveType pt = (PrimitiveType) this.locus.getFactory().getBuiltInType(t.getName());
				if (pt == null) {
					return;
				}
				if (pt.getName().equals("Integer")) {
					IntegerValue value = new IntegerValue();
					value.value = new Integer(args[i]);
					tmpArgs.add(value);
				} else if (pt.getName().equals("String")) {
					StringValue value = new StringValue();
					value.value = args[i];
					tmpArgs.add(value);
				} else if (pt.getName().equals("Boolean")) {
					BooleanValue value = new BooleanValue();
					value.value = new Boolean(args[i]);
					tmpArgs.add(value);
				} else if (pt.getName().equals("UnlimitedNatural")) {
					UnlimitedNaturalValue value = new UnlimitedNaturalValue();
					value.value = new UMLPrimitiveTypes.UnlimitedNatural(Integer.parseInt(args[i]));
					tmpArgs.add(value);
				} else {
					return; // Unsupported type. TODO Consider Real
				}
			}
			i++;
		}

		i = 0;
		// creates actual arguments
		for (Value v : tmpArgs) {
			ParameterValue arg = new ParameterValue();
			arg.setParameter(parameters.get(i));
			arg.setValues(new ValueList());
			arg.getValues().add(v);
			this.executionArguments.add(arg);
			i++;
		}
	}
}
