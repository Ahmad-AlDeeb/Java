package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Service;

@RestController
@RequestMapping("/api/v1/provision")
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }


}
