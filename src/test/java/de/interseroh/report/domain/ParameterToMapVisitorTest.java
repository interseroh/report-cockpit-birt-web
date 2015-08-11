package de.interseroh.report.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.interseroh.report.domain.visitors.ParameterToMapVisitor;
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

		params.add(new ParameterGroup() //
				.withName("group1").withSynthetic(false)
				.addScalarParameter(GenericParameter.newInstance(String.class) //
						.withName("Group1Scalar1String"))
				.addScalarParameter(GenericParameter.newInstance(Boolean.class) //
						.withName("Group1Scalar2Boolean")));
		params.add(new ParameterGroup() //
				.withName("group2").withSynthetic(true)
				.addScalarParameter(GenericParameter.newInstance(String.class) //
						.withName("Group2Scalar1String"))
				.addScalarParameter(GenericParameter.newInstance(Boolean.class) //
						.withName("Group2Scalar2Boolean")));
		params.add(new ParameterGroup() //
				.withName("group3").withCascading(true)
				.addScalarParameter(SelectionParameter.newInstance(Boolean.class)
						.withName("Group3Scalar1Radio"))
				.addScalarParameter(SelectionParameter.newInstance(String.class)
						.withName("Group3Scalar2Select"))
				.addScalarParameter(SelectionParameter.newInstance(Integer[].class)
						.withName("Group3Scalar3Multi")));
		return params;
	}
}