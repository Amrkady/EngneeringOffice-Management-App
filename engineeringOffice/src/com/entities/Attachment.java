package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="attachments")
public class Attachment {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name="id")
	private Integer id;
	
	@Column(name="att_path")
	private Integer attPath;
	
	@Column(name="att_name")
	private Integer attName;
	

}
