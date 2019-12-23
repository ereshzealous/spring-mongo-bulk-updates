package com.eresh.spring.service;

import com.eresh.spring.mongo.Address;
import com.eresh.spring.mongo.PersonalInformation;
import com.eresh.spring.mongo.UserDetails;
import com.eresh.spring.mongo.repositories.UserDetailsRepository;
import com.eresh.spring.util.MongoQueryUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created By Gorantla, Eresh on 23/Dec/2019
 **/
@Service
public class UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	MongoOperations operations;

	private final String COLLECTION_NAME = "userDetails";

	@Autowired
	UserDetailsRepository userDetailsRepository;

	private UserDetails modifyUserDetails(UserDetails userDetails) {
		userDetails.getPersonalInformation()
		           .setMobileNumber(RandomStringUtils.randomNumeric(10));
		return userDetails;
	}

	public void saveUserDetails() {
		List<UserDetails> userDetails;
		Instant start = Instant.now();
		Page<UserDetails> userDetailsPage = userDetailsRepository.findAll(PageRequest.of(0, 5000));
		userDetails = userDetailsPage.getContent();
		userDetails = userDetails.stream()
		                         .map(data -> this.modifyUserDetails(data))
		                         .collect(Collectors.toList());
		userDetailsRepository.saveAll(userDetails);
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish)
		                           .toMillis();
		logger.info(String.format("Time taken to insert data with template => %d", timeElapsed));
	}

	public void bulkOperation() {
		MongoCollection<Document> userDetailsMongoCollection = mongoTemplate.getCollection("userDetails");
		BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, UserDetails.class);
		List<WriteModel<Document>> documents = new ArrayList<WriteModel<Document>>();
		List<UserDetails> userDetails;
		Page<UserDetails> userDetailsPage = userDetailsRepository.findAll(PageRequest.of(0, 5000));
		userDetails = userDetailsPage.getContent();
		userDetails = userDetails.stream()
		                         .map(data -> this.modifyUserDetails(data))
		                         .collect(Collectors.toList());
		userDetails.forEach(details -> {
			if (StringUtils.isBlank(details.getId())) {
				Document document = new Document();
				generateUserDetailsDocument(details, document);
				documents.add(new InsertOneModel<>(document));
			} else {
				Document document = new Document();
				generateUserDetailsDocument(details, document);
				UpdateOptions updateOptions = new UpdateOptions();
				updateOptions.upsert(true);
				documents.add(new ReplaceOneModel<>(new Document("_id", details.getId()), document));
			}
		});
		Instant start = Instant.now();
		userDetailsMongoCollection.bulkWrite(documents, new BulkWriteOptions().ordered(false));
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish)
		                           .toMillis();

		logger.info(String.format("Time taken to insert data from bulk operation => %d", timeElapsed));
	}

	private void generateUserDetailsDocument(UserDetails userDetails, Document document) {
		if (StringUtils.isNotBlank(userDetails.getId())) {
			MongoQueryUtil.appendMongoDocument(document, "id", userDetails.getId(), String.class);
		}
		MongoQueryUtil.appendMongoDocument(document, "personalInformation", userDetails.getPersonalInformation(), PersonalInformation.class);
		MongoQueryUtil.appendMongoDocument(document, "addresses", userDetails.getAddresses());
	}

	private UserDetails generateUserDetails() {
		UserDetails userDetails = new UserDetails();
		PersonalInformation information = new PersonalInformation();
		ZonedDateTime dateOfBirth = ZonedDateTime.now()
		                                         .plusYears(-(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		information.setDateOfBirth(Date.from(dateOfBirth.toInstant()));
		Period period = Period.between(dateOfBirth.toLocalDate(), LocalDate.now());
		information.setAge(period.getYears());
		information.setEmailId(RandomStringUtils.randomAlphabetic(10, 30));
		information.setGender(information.getAge() % 2 == 0 ? "Male" : "Female");
		information.setFirstName(RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		information.setLastName(RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		information.setMobileNumber(RandomStringUtils.randomNumeric(10));
		userDetails.setPersonalInformation(information);
		Integer numberOfAddresses = NumberUtils.toInt(RandomStringUtils.randomNumeric(1));
		userDetails.setAddresses(IntStream.range(0, numberOfAddresses)
		                                  .mapToObj(index -> this.getAddress())
		                                  .collect(Collectors.toList()));
		return userDetails;
	}

	private Address getAddress() {
		Address address = new Address();
		address.setAddress1(
				RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		address.setAddress2(
				RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		address.setCity(RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		address.setCountry("India");
		address.setLocality(
				RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		address.setState(RandomStringUtils.randomAlphabetic(NumberUtils.toInt(RandomStringUtils.randomNumeric(2))));
		address.setZipCode(RandomStringUtils.randomNumeric(6));
		return address;
	}

}
