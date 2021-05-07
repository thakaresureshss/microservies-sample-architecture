package com.lib.common.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationError {
  private String field;
  private Object rejectedValue;
  private String message;


}
