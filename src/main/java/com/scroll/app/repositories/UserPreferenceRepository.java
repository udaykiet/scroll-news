package com.scroll.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scroll.app.models.User;
import com.scroll.app.models.UserPreference;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference , Long> {

	Optional<UserPreference> findByUser(User user);

	Optional<UserPreference> findByUserId(Long userId);

	boolean existsByUserId(Long userId);
}
