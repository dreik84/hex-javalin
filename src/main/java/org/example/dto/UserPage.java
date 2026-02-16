package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.model.User;

@Getter
@AllArgsConstructor
public class UserPage {
    private User user;
}
