package org.modeldriven.fuml.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.IntegerValue;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.classification.Parameter;

/**
  */
public abstract class FUMLTest extends TestCase {

    protected Log log = LogFactory.getLog(getClass().getName());

    public FUMLTest() {
    }

    public FUMLTest(String name) {
        super(name);
    }

    /** Get the MBeanServerConnection from JNDI */
    public MBeanServerConnection getServer() throws NamingException {
        throw new RuntimeException("not implemented");
    }

    /** True, if -Djbossws.integration.target=jboss?? */
    public boolean isTargetJBoss() {
        return true;
    }

    /** True, if -Djbossws.integration.target=tomcat */
    public boolean isTargetTomcat() {
        return false;
    }

    /**
     * Get the client's env context for a given name.
     */
    protected InitialContext getInitialContext(String clientName) throws NamingException {
        InitialContext iniCtx = new InitialContext();
        Hashtable env = iniCtx.getEnvironment();
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming.client");
        env.put("j2ee.clientName", clientName);
        return new InitialContext(env);
    }

    /**
     * Get the client's env context
     */
    protected InitialContext getInitialContext() throws NamingException {
        return getInitialContext("jbossws-client");
    }

    public boolean hasJDK15() {
        try {
            Class.forName("java.lang.Enum");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static String getServerHost() {
        String hostName = System.getProperty("jbosstest.host.name", "localhost");
        return hostName;
    }
    
    public static void assertEqualValues(String label, ParameterValueList parameterValues, String parameterName, Object...expectedValues) throws Exception {
    	for (ParameterValue parameterValue: parameterValues) {
    		Parameter parameter = parameterValue.parameter;
    		if (parameter != null && parameterName.equals(parameter.name)) {
    			assertEqualValues(label, parameterValue, expectedValues);
    			return;
    		}
    	}
    	fail(label + ": No such parameter.");
    }

    public static void assertEqualValues(String label, ParameterValue parameterValue, Object... expectedValues) throws Exception {
    	assertEqualValues(label, parameterValue.values, expectedValues);
    }
    
    public static void assertEqualValues(String label, ValueList values, Object... expectedValues) throws Exception {
    	assertEquals(label + ": values.size()", expectedValues.length, values.size());
    	for (int i = 0; i < values.size(); i++) {
    		Value value = values.get(i);
    		assertEquals(label + ": value[" + i + "]", expectedValues[i], valueOf(value));
    	}
    }
    
    public static Object valueOf(Value value) {
    	if (value instanceof BooleanValue) {
    		return ((BooleanValue)value).value;
    	} else if (value instanceof IntegerValue) {
    		return ((IntegerValue)value).value;
    	} else if (value instanceof RealValue) {
    		return ((RealValue)value).value;
    	} else if (value instanceof UnlimitedNaturalValue) {
    		return ((UnlimitedNaturalValue)value).value.naturalValue;
    	} else if (value instanceof StringValue) {
    		return ((StringValue)value).value;
    	} else {
    		return null;
    	}
    }
    
    public static void assertAllEquals(String label, Value expectedValue, ValueList values) {
    	for (int i = 0; i < values.size(); i++) {
    		// NOTE: Uses fUML-specific Value::equals method.
    		assertTrue(label + "value[" + i + "]", values.get(i).equals(expectedValue));
    	}
    }
    
    public static void assertEquals(Object exp, Object was) {
        if (exp instanceof Object[] && was instanceof Object[])
            assertEqualsArray((Object[]) exp, (Object[]) was);
        else if (exp instanceof byte[] && was instanceof byte[])
            assertEqualsArray((byte[]) exp, (byte[]) was);
        else if (exp instanceof boolean[] && was instanceof boolean[])
            assertEqualsArray((boolean[]) exp, (boolean[]) was);
        else if (exp instanceof short[] && was instanceof short[])
            assertEqualsArray((short[]) exp, (short[]) was);
        else if (exp instanceof int[] && was instanceof int[])
            assertEqualsArray((int[]) exp, (int[]) was);
        else if (exp instanceof long[] && was instanceof long[])
            assertEqualsArray((long[]) exp, (long[]) was);
        else if (exp instanceof float[] && was instanceof float[])
            assertEqualsArray((float[]) exp, (float[]) was);
        else if (exp instanceof double[] && was instanceof double[])
            assertEqualsArray((double[]) exp, (double[]) was);
        else
            TestCase.assertEquals(exp, was);
    }

    private static void assertEqualsArray(Object[] exp, Object[] was) {
        if (exp == null && was == null)
            return;

        if (exp != null && was != null) {
            if (exp.length != was.length) {
                fail("Expected <" + exp.length + "> array items, but was <" + was.length + ">");
            } else {
                for (int i = 0; i < exp.length; i++) {

                    Object compExp = exp[i];
                    Object compWas = was[i];
                    assertEquals(compExp, compWas);
                }
            }
        } else if (exp == null) {
            fail("Expected a null array, but was: " + Arrays.asList(was));
        } else if (was == null) {
            fail("Expected " + Arrays.asList(exp) + ", but was: null");
        }
    }

    private static void assertEqualsArray(byte[] exp, byte[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(boolean[] exp, boolean[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(short[] exp, short[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(int[] exp, int[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(long[] exp, long[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(float[] exp, float[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    private static void assertEqualsArray(double[] exp, double[] was) {
        assertTrue("Arrays don't match", Arrays.equals(exp, was));
    }

    public byte[] getContent(InputStream source) throws IOException {
        byte[] buf = new byte[4000];
        ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
        try {
            int len = -1;
            while ((len = source.read(buf)) != -1)
                os.write(buf, 0, len);
        } finally {
            source.close();
            os.flush();
        }
        return os.toByteArray();
    }
}
