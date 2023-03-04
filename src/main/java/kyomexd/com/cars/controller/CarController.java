package kyomexd.com.cars.controller;

import kyomexd.com.cars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public String getCars(@RequestParam(value = "count",   required = false) Integer count, Model model) {
        model.addAttribute("cars", carService.listCars(count));
        return "cars";
    }
}
