package com.it.audit.web.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.it.audit.enums.ObjectTaskType;

import lombok.Data;

@Data
public class TaskAllot {

	@NotNull
	private ObjectTaskType type;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private List<Long> taskIds;
}
