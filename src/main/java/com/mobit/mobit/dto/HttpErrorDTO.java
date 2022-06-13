package com.mobit.mobit.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HttpErrorDTO {
	private LocalDateTime timestamp;
	private int status;
	private String message;
	private String developerMessage;
	private String uriPatch;
}
