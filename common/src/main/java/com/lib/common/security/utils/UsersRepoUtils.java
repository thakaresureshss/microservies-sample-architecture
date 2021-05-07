package com.lib.common.security.utils;

import com.lib.common.security.dto.AuthUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersRepoUtils {

  @Autowired
  private PasswordEncoder passwordEncoder;

  private List<AuthUser> users = new ArrayList<AuthUser>();

  public UsersRepoUtils(List<AuthUser> users) {
    super();
  }

  /**
   * @return the users
   */
  public List<AuthUser> getUsers() {
    this.users = Arrays.asList(
        new AuthUser(1, "suresh", passwordEncoder.encode("1234"),
            Arrays.asList("ROLE_ADMIN", "ROLE_USER")),
        new AuthUser(2, "ramesh", passwordEncoder.encode("1234"), Arrays.asList("ROLE_USER")));
    return users;
  }

  /**
   * @param users the users to set
   */
  public void setUsers(List<AuthUser> users) {
    this.users = users;
  }

}
