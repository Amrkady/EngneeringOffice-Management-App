package com.entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class Contracts {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer userId;
	@Column(name = "LOGIN_NAME", nullable = false)
	private String loginName;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "role_id")
	private Integer roleId;
	
}
