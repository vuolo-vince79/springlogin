package com.login.repository;

import com.login.model.SetupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetupUserRepository extends JpaRepository<SetupUser, Long> {

    Optional<SetupUser> findByIdUser(long idUser);
}
