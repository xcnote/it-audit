package com.it.audit.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class DeleteDTO {
	private List<Long> ids;
	
	private Long objectId;
}
