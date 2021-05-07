package com.lib.common.dto;

import java.util.List;
import lombok.Data;

@Data
public class MovieCatalogDto extends MovieDto {
  private List<String> casts;
}
