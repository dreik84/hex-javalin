package org.example.dto.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.dto.BasePage;
import org.example.model.Car;

import java.util.List;

@AllArgsConstructor
@Getter
public class CarsPage extends BasePage {
    private List<Car> cars;
}
