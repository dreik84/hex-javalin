package org.example.dto.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.dto.BasePage;
import org.example.model.Car;

@Getter
@AllArgsConstructor
public class CarPage extends BasePage {
    private Car car;
}
