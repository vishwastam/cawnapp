package com.cawnfig.cawnapp.repository;

import com.cawnfig.cawnapp.domain.C_user;
import com.cawnfig.cawnapp.domain.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the C_user entity.
 */
@SuppressWarnings("unused")
@Repository
public interface C_userRepository extends JpaRepository<C_user,Long> {
	
	C_user findOneByUser(User user);

}
