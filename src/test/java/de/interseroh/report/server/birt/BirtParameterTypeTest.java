package de.interseroh.report.server.birt;

import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtParameterTypeTest {

    @Test
    public void testGetType() throws Exception {
        assertThat(BirtParameterType.valueOf(IParameterDefnBase.SCALAR_PARAMETER).getType(), is(IParameterDefnBase.SCALAR_PARAMETER));
    }

    @Test(expected = UnknownParameterTypeException.class)
    public void testUnknownParameterTypeException() throws Exception {
        BirtParameterType.valueOf(-1);
    }
}