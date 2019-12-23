package com.eresh.spring.config;

import lombok.Data;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created By Gorantla, Eresh on 20/Dec/2019
 **/
@Data
@ConfigurationProperties(prefix = "mongodb")
public class MongoPropertiesConfig {

	private MongoProperties local = new MongoProperties();


}
