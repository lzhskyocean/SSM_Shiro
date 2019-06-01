package com.lzh.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private long id;

  @NotBlank(message = "用户名不能为空")
  private String username;
  @NotBlank(message = "密码名不能为空")
  private String password;

  //密码加盐
  private String salt;

  @NotBlank(message = "手机号名不能为空")
  private String phone;
  private Date created;

}
