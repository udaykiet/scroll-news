package com.scroll.app.dtos;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String email;
	private String fullName;
	private String phone;
}