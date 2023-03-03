package kyomexd.com.cars;

import kyomexd.com.cars.model.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public List<Car> getCarsList () {
		List <Car> cars = new ArrayList<>();
		cars.add(new Car("Toyota",1,"White"));
		cars.add(new Car("Nissan",2,"White"));
		cars.add(new Car("Subaru",3,"Blue"));
		cars.add(new Car("Honda",4,"Black"));
		cars.add(new Car("Volga",5,"Black"));
		cars.add(new Car("Toyota",6,"White"));
		cars.add(new Car("Nissan",7,"White"));
		cars.add(new Car("Subaru",8,"Blue"));
		cars.add(new Car("Honda",9,"Black"));
		cars.add(new Car("Volga",10,"Black"));
		return cars;
	}

}
