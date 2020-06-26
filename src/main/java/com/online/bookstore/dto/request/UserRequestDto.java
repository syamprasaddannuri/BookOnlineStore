package com.online.bookstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @JsonProperty(value = "postId")
    private String id;

    @JsonProperty(value = "name")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @JsonProperty(value = "age")
    private int age;

    @JsonProperty(value = "mobileNumber")
    @NotBlank(message = "mobile number cannot be blank")
    private String mobileNumber;

    @JsonProperty(value = "emailId")
    @NotBlank(message = "email cannot be blank")
    private String emailId;
}
