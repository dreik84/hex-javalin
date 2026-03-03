package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainPage {
    private Boolean visited;
    private String currentUser;
    private String flash;

    public Boolean isVisited() {
        return visited;
    }
}
