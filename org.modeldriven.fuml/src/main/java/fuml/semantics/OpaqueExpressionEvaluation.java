package fuml.semantics;

import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.semantics.values.Evaluation;
import fuml.semantics.values.Value;

import fuml.semantics.values.ValueList;
import fuml.syntax.commonbehavior.Behavior;
import org.eclipse.uml2.uml.OpaqueExpression;


import java.util.ArrayList;
import java.util.List;

public class OpaqueExpressionEvaluation extends Evaluation {
    // The context is basically the execution context of the state-machine.
    // This provides the possibility for the behavior associated to the evaluated
    // opaque expression to access features available at the context.
    public Object_ context;
    public Value evaluate() {
        List<Value> evaluation = this.executeExpressionBehavior();
        if (evaluation.size() > 0) {
            return evaluation.get(0);
        } else {
            return null;
        }
    }
    public List<Value> executeExpressionBehavior() {
        // If a behavior is associated with the context OpaqueExpression,
        // then execute this behavior, and return computed values.
        // Otherwise, return an empty list of values.
        List<Value> evaluation = new ArrayList<Value>();
        OpaqueExpression expression = (OpaqueExpression) this.specification;
        Behavior behavior = (fuml.syntax.commonbehavior.Behavior)expression.getBehavior();
        if (behavior != null) {
            ParameterValueList inputs = new ParameterValueList();
            ParameterValueList results = this.locus.getExecutor().execute(behavior, null, inputs);
            for (int i = 0; i < results.size(); i++) { // results.size should be 1
                ParameterValue parameterValue = results.get(i);
                ValueList values = parameterValue.getValues();
                for (int j = 0; j < values.size(); j++) {
                    evaluation.add(values.get(j));
                }
            }
        }
        return evaluation;
    }
    public void setContext(Object_ context) { this.context = context;}
    public Object_ getContext() { return this.context;}
}
