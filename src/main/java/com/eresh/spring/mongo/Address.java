package com.eresh.spring.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created By Gorantla, Eresh on 20/Dec/2019
 **/
@Getter
@Setter
@NoArgsConstructor
public class Address {

	private String address1;

	private String address2;

	private String locality;

	private String city;

	private String state;

	private String country;

	private String zipCode;
}
