package com.eresh.spring.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Gorantla, Eresh on 20/Dec/2019
 **/
@Getter
@Setter
@NoArgsConstructor
@Document("userDetails")
public class UserDetails {

	@Id
	private String id;

	private PersonalInformation personalInformation;

	private List<Address> addresses = new ArrayList<>();

}
