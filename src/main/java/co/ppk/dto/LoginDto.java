package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDto {
//  @NotBlank
//  @Size(min=3, max = 60)
  private String username;
//  @NotBlank
//  @Size(min=3, max = 60)
  private String password;

  public LoginDto() {
    super();
  }

  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
