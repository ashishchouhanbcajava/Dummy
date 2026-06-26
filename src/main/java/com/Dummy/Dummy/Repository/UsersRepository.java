package com.Dummy.Dummy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dummy.Dummy.beans.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String username);

	boolean existsByUsername(String username);

}
