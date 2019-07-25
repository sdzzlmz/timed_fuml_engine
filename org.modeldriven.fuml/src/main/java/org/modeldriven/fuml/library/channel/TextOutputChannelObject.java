/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.library.channel;

import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.library.libraryclass.OperationExecution;

import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.IntegerValue;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;

public abstract class TextOutputChannelObject extends OutputChannelObject {

    public abstract void writeString(String value, Status errorStatus);
    public abstract void writeNewLine(Status errorStatus);

    public void writeLine(String value, Status errorStatus) {
        this.writeString(value, errorStatus);
        this.writeNewLine(errorStatus);
    }

    public void writeInteger(int value, Status errorStatus) {
        this.writeString(Integer.toString(value), errorStatus);
    }

    public void writeReal(float value, Status errorStatus) {
        this.writeString(Float.toString(value), errorStatus);
    }

    public void writeBoolean(boolean value, Status errorStatus) {
        this.writeString(Boolean.toString(value), errorStatus);
    }

    public void writeUnlimitedNatural(UMLPrimitiveTypes.UnlimitedNatural value, Status errorStatus) {
        int naturalValue = value.naturalValue;
        
        if (naturalValue < 0) {
            this.writeString("*", errorStatus);
        } else {
            this.writeString(Integer.toString(naturalValue), errorStatus);
        }

    }

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        // Debug.println("[execute] operation = " + name);

        ParameterValue parameterValue = execution.getParameterValue("value");
        // if ((parameterValue != null) && (parameterValue.values.size() > 0)) {
        // Debug.println("[execute] argument = " +
        // parameterValue.values.getValue(0));
        // }
        
        Status status = new Status(this.locus, "TextOutputChannel");

        if (name.equals("writeNewLine")) {
            this.writeNewLine(status);            
            this.updateStatus(execution, status);
        } else if (name.equals("writeString")) {
            this.writeString(((StringValue) (parameterValue.values.getValue(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeLine")) {
            this.writeLine(((StringValue) (parameterValue.values.getValue(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeInteger")) {
            this.writeInteger(((IntegerValue) (parameterValue.values.getValue(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeReal")) {
            this.writeReal(((RealValue) (parameterValue.values.getValue(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeBoolean")) {
            this.writeBoolean(((BooleanValue) (parameterValue.values.getValue(0))).value, status);
            this.updateStatus(execution, status);
        } else if (name.equals("writeUnlimitedNatural")) {
            this.writeUnlimitedNatural(((UnlimitedNaturalValue) (parameterValue.values
                            .getValue(0))).value, status);
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
    }

} // TextOutputChannelObject
