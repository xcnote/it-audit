package com.it.audit.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDelete {
	private List<Long> ids;
}
