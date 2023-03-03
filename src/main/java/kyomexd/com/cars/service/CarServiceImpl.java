package kyomexd.com.cars.service;


import kyomexd.com.cars.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class CarServiceImpl implements CarService {

    @Autowired
    private List<Car> carsList;

    @Value("${cars.maxCars}")
    private int maxCount;

    @Override
    public List<Car> listCars(int count) {

        if (count > maxCount) {
            return carsList;
        }
        return carsList.stream()
                .limit(count)
                .toList();
    }
}
