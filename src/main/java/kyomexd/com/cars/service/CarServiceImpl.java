package kyomexd.com.cars.service;


import kyomexd.com.cars.model.Car;
import kyomexd.com.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Value("${cars.maxCars}")
    private int maxCount;

    @Override
    public List<Car> listCars(Integer count) {
        if (count == null || count > maxCount) {
            return carRepository.findAll();
        }
        return carRepository.findAll().stream()
                .limit(count)
                .toList();
    }
}
