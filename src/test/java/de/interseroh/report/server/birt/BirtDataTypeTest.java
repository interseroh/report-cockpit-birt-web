package de.interseroh.report.server.birt;

import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtDataTypeTest {

    @Test
    public void testGetType() throws Exception {
        assertThat(BirtDataType.valueOf(IParameterDefn.TYPE_FLOAT).getType(), is(IParameterDefn.TYPE_FLOAT));
    }

    @Test(expected = UnknownDataTypeException.class)
    public void testUnknownDataTypeException() throws Exception {
        BirtDataType.valueOf(-1);
    }
}