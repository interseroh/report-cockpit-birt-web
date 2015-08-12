package de.interseroh.report.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.interseroh.report.webconfig.WebMvcConfig;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@WebAppConfiguration
public class ReportControllerTest {

	protected MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testCustomParameterView() throws Exception {
		this.mockMvc.perform(get("/reports/custom")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("Parameter")))
				.andExpect(content().string(containsString("radio")))
				.andDo(print());
	}

    @Test
    public void testCustomParameterViewWithMissingParameter() throws Exception {
        this.mockMvc.perform(get("/reports/cascade_parameters?customer=278")) //
                .andExpect(status().isBadRequest()) //
                .andDo(print());
    }

    @Test
    public void testCustomParameterViewWithWrongType() throws Exception {
        this.mockMvc.perform(get("/reports/cascade_parameters?customer=ABC")) //
                .andExpect(status().isBadRequest()) //
                .andDo(print());
    }


    @Test
	public void testCascadingParameterView() throws Exception {
		this.mockMvc.perform(get(
				"/reports/cascade_parameters/cascade/customerorders?customer=278")) //
				.andExpect(status().isOk()) //
				.andDo(print());
	}

}
