package com.meetingplanner.repository;

import com.meetingplanner.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*Classe du repository de l'entité User*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(  value = "select * from users as u where u.email = :email",
    nativeQuery = true)
    Optional<User> findUserByEmail(String email);

}
