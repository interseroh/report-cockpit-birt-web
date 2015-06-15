/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.util;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ext.Provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public final class RestProviderBeanScanner {

	private RestProviderBeanScanner() {
	}

	public static List<Object> scan(ApplicationContext applicationContext,
			String... basePackages) {
		GenericApplicationContext genericAppContext = new GenericApplicationContext();
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(
				genericAppContext, false);

		scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
		scanner.scan(basePackages);
		genericAppContext.setParent(applicationContext);
		genericAppContext.refresh();

		return new ArrayList<>(genericAppContext.getBeansWithAnnotation(
				Provider.class).values());
	}
}
