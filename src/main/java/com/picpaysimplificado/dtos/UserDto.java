package com.picpaysimplificado.dtos;

import com.picpaysimplificado.user.UserType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record UserDto(String firstName,
                      String lastName,
                      String document,
                      String email,
                      String password,
                      BigDecimal balance,
                      UserType userType) {
}
