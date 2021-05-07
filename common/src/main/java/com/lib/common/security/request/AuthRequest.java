package com.lib.common.security.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * This is request class which will take request values.
 * 
 * @author Suresh Thakare
 *
 */
@Setter
@Getter
public class AuthRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "Username must not be blank")
  private String username;

  @NotBlank(message = "Password must not be blank")
  private String password;
}
