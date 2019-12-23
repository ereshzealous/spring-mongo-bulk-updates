package com.eresh.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created By Gorantla, Eresh on 20/Dec/2019
 **/
@Configuration
@EnableMongoRepositories(mongoTemplateRef = LocalMongoConfig.MONGO_TEMPLATE)
public class LocalMongoConfig {
	public static final String MONGO_TEMPLATE = "localMongoTemplate";
}

