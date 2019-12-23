package com.eresh.spring.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import lombok.RequiredArgsConstructor;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Created By Gorantla, Eresh on 20/Dec/2019
 **/
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MongoPropertiesConfig.class)
public class MongoConfiguration {

	private final MongoPropertiesConfig mongoPropertiesConfig;

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(getLocalFactory(this.mongoPropertiesConfig.getLocal()));
	}

	@Bean
	public MongoDbFactory getLocalFactory(final MongoProperties mongo) {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder()
		                                                                                                                       .automatic(true)
		                                                                                                                       .build()));
		return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(mongo.getHost()), MongoClientOptions.builder()
		                                                                                                      .codecRegistry(pojoCodecRegistry)
		                                                                                                      .build()), mongo.getDatabase());
	}
}
