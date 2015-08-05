package de.interseroh.report.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterToMapVisitorTest {

	@Test
	public void testBuild() throws Exception {
		List<Parameter> params = buildTestData();

		Map<String, Parameter> map = new ParameterToMapVisitor(params).build();

		assertThat(map, hasKey("Group1Scalar1String"));
		assertThat(map, hasKey("Group1Scalar2Boolean"));
		assertThat(map, hasKey("Group2Scalar1String"));
		assertThat(map, hasKey("Group2Scalar2Boolean"));
		assertThat(map, hasKey("Group3Scalar1Radio"));
		assertThat(map, hasKey("Group3Scalar2Select"));
		assertThat(map, hasKey("Group3Scalar3Multi"));

		assertThat(map, not(hasKey("group1")));
		assertThat(map, not(hasKey("group2")));
		assertThat(map, hasKey("group3"));

	}

	private List<Parameter> buildTestData() {
		List<Parameter> params = new ArrayList<>();

		params.add(new DefaultGroupParameter() //
				.withName("group1").withSynthetic(false)
				.addScalarParameter(new StringParameter() //
						.withName("Group1Scalar1String"))
				.addScalarParameter(new BooleanParameter() //
						.withName("Group1Scalar2Boolean")));
		params.add(new DefaultGroupParameter() //
				.withName("group2").withSynthetic(true)
				.addScalarParameter(new StringParameter() //
						.withName("Group2Scalar1String"))
				.addScalarParameter(new BooleanParameter() //
						.withName("Group2Scalar2Boolean")));
		params.add(new DefaultGroupParameter() //
				.withName("group3").withCascading(true)
				.addScalarParameter(new RadioSelectParameter<Boolean>()
						.withName("Group3Scalar1Radio"))
				.addScalarParameter(new SingleSelectParameter<String>()
						.withName("Group3Scalar2Select"))
				.addScalarParameter(new MultiSelectParameter<List<String>>()
						.withName("Group3Scalar3Multi")));
		return params;
	}
}