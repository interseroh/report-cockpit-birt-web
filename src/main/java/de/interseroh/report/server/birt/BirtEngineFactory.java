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
package de.interseroh.report.server.birt;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtEngineFactory implements FactoryBean, ApplicationContextAware,
		DisposableBean {

	public static final String SPRING_KEY = "spring";
	private ApplicationContext applicationContext;

	private IReportEngine birtEngine;

	private Resource logDirectory;

	@Override
	public Object getObject() throws Exception {
		EngineConfig config = new EngineConfig();
		config.getAppContext().put(SPRING_KEY, this.applicationContext);

		try {
			Platform.startup(config);
		} catch (BirtException be) {
			throw new RuntimeException("Could not start the Birt engine!", be);
		}

		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		birtEngine = factory.createReportEngine(config);

		return birtEngine;
	}

	@Override
	public Class<?> getObjectType() {
		return IReportEngine.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void destroy() throws Exception {
		birtEngine.destroy();
		Platform.shutdown();
	}
}
