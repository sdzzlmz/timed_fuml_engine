package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class SimpleActivitiesTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(SimpleActivitiesTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(SimpleActivitiesTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testSimpleActivities() throws Exception {
        log.info("testSimpleActivities");        
        ParameterValueList output = this.testSuite.testSimpleActivites();        
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 9, output.size());        
        assertEqualValues("Coper.output", output.get(0), 0);
        assertEqualValues("CoperCaller.output", output.get(1), 0);
        assertEqualValues("SimpleDecision0.output_0", output.get(2), 0);
        assertEqualValues("SimpleDecision0.output_1", output.get(3));
        assertEqualValues("SimpleDecision1.output_0", output.get(4));
        assertEqualValues("SimpleDecision1.output_1", output.get(5), 1);
        assertEqualValues("DecisionJoin.output", output.get(6), 0, 1);
        assertEqualValues("ForkMerge", output.get(7), 0, 0);
        assertEqualValues("ForkMergeData", output.get(8), 0, 0);        
    }
    
}