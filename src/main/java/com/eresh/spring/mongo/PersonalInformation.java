package com.eresh.spring.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.Date;

/**
 * Created By Gorantla, Eresh on 20/Dec/2019
 **/
@Getter
@Setter
@NoArgsConstructor
public class PersonalInformation {

	@TextIndexed
	private String firstName;

	@TextIndexed
	private String lastName;

	private String emailId;

	private String mobileNumber;

	private Date dateOfBirth;

	@Indexed
	private Integer age;

	private String gender;
}
