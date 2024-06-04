package com.t3h.topcv.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CompanyUser {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String username;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password can only contain letters and numbers")
    private String password;

    @NotEmpty(message = "Name is required")
    private String name;

    private String address;

    @NotEmpty(message = "Phone is required")
    private String phone;

}