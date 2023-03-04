package kyomexd.com.cars.service;



import kyomexd.com.cars.model.Car;

import java.util.List;

public interface CarService {
    List<Car> listCars(Integer count);
}
