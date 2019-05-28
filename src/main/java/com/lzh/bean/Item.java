package com.lzh.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  private Long id;
  @NotBlank(message = "商品的名称必填!!!")
  private String name;
  @NotNull(message = "商品的价格必填!!!")
  private double price;

  //springMVC 将请求参数
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date productionDate;

  @NotBlank(message = "商品的描述必填!!!")
  private String description;
  private String pic;
  private Date created;

  @NotNull(message = "商品的图片必填!!!")
  private MultipartFile picFile;


}
