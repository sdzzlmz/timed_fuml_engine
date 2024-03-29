/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * All modifications copyright 2009-2016 Data Access Technologies, Inc. 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 */
package org.modeldriven.fuml.environment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.values.Value;
import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.classification.ParameterList;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.commonstructure.Type;

public class ExecutionEnvironment {
    
    private static Log log = LogFactory.getLog(ExecutionEnvironment.class);
    private Environment environment;
    
    @SuppressWarnings("unused")
    private ExecutionEnvironment() {}
    
    public ExecutionEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ParameterValueList execute(Behavior behavior) {
        ParameterList parameters = behavior.ownedParameter;
        ParameterValueList parameterValues = this.createDefaultInputValues(parameters);

        if (parameterValues == null)
            return null;

        if (log.isDebugEnabled())
            log.debug("executing the behavior...");
        
        ParameterValueList outputParameterValues = this.environment.locus.executor.execute(
                behavior, null, parameterValues);

        for (int i = 0; i < outputParameterValues.size(); i++) {
            ParameterValue outputParameterValue = outputParameterValues.getValue(i);
            if (log.isDebugEnabled())
                log.debug("output parameter '" + outputParameterValue.parameter.name
                    + "' has " + outputParameterValue.values.size() + " value(s)");
            for (int j = 0; j < outputParameterValue.values.size(); j++) {
                if (log.isDebugEnabled())
                    log.debug("value [" + j + "] = "
                        + outputParameterValue.values.getValue(j));
            }
        }
        
        return outputParameterValues;
    }
    
    protected ParameterValueList createDefaultInputValues(ParameterList parameters) {
        
        if (log.isDebugEnabled())
            log.debug("creating " + parameters.size() + " parameter(s):");
        ParameterValueList parameterValues = new ParameterValueList();

        for (int i = 0; i < parameters.size(); i++) {
            if (log.isDebugEnabled())
                log.debug("checking parameter '" + parameters.getValue(i).name + "'");
            ParameterDirectionKind direction = parameters.getValue(i).direction;
            if (direction == null)
                throw new EnvironmentException("expected 'direction' for parameter, '"
                        + parameters.getValue(i).name + "'");
            if (direction.equals(ParameterDirectionKind.in)
                    || direction.equals(ParameterDirectionKind.inout)) {
                if (log.isDebugEnabled())
                    log.debug("creating parameter value for parameter '"
                                + parameters.getValue(i).name + "'");
                ParameterValue parameterValue = new ParameterValue();
                parameterValue.parameter = parameters.getValue(i);

                Type type = parameters.getValue(i).type;
                Value value = this.environment.makeValue((Classifier)(type));

                if (value == null) {
                    log.error("expected parameter value");
                    return null;
                }

                if (log.isDebugEnabled())
                    log.debug("value = " + value);
                parameterValue.values.addValue(value);
                parameterValues.addValue(parameterValue);
            }
        }

        return parameterValues;
    }
    
}
