package kyomexd.com.cars.repository;

import kyomexd.com.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
