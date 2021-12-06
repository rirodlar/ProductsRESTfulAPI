package com.falabella.test.products.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDto implements Serializable {


  private static final long serialVersionUID = 1L;

  private String field;
  private String text;
  private int type;

  public MessageDto() {

  }

  public MessageDto(String field, String text, int type) {
    super();
    this.field = field;
    this.text = text;
    this.type = type;
  }
}
