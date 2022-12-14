package com.project.mypfinance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestApiException {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private int httpStatus;
    private String error;
    private String message;
    private String path;

   public static RestResponseBuilder builder(){
       return new RestResponseBuilder();
   }

}
