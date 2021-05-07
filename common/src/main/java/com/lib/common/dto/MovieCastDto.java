package com.lib.common.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class MovieCastDto {
  private UUID id;
  private UUID movieId;
  private String actorName;
}
