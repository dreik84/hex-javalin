package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainPage {
    private Boolean visited;

    public Boolean isVisited() {
        return visited;
    }
}
