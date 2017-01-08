package com.it.audit.web.dto;

import com.it.audit.enums.ObjectStatus;

import lombok.Data;

@Data
public class ReviewFeedback {
	private Long objectId;
	private ObjectStatus status;
}
