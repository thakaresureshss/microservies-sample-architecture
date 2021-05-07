package com.lib.common.dto;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class MovieDto {
  private UUID id;
  private String name;
  private List<String> langugage;
  private List<String> directors;
  private String discription;
  private int durationInMin;

}
