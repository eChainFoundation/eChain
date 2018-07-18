package com.echain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

public class BaseEntity {

	@Id
	@ApiModelProperty(value = "ID", dataType = "Long")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
