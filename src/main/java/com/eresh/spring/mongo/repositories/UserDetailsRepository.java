package com.eresh.spring.mongo.repositories;

import com.eresh.spring.mongo.UserDetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Gorantla, Eresh on 23/Dec/2019
 **/
@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {

}
