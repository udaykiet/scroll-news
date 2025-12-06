package com.scroll.app.models;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password; // Will hash this later

	private String fullName;

	@Column(unique = true)
	private String phone;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime lastLoginAt;

	// NEW: One-to-One relationship with UserPreference
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private UserPreference preference;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}
}
