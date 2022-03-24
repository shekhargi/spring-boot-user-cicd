package neowise.user.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import neowise.user.service.config.Env;

@RestController
public class Controller {

    @GetMapping("/test")
    public String test() {
        final String ENV_TEST ="TEST_ENV_VAR";
        final String ENV_DATA = Env.get(ENV_TEST);
        System.out.println(ENV_DATA);
        String data = "Hello World ! --- Env Data: " + ENV_DATA + "\n--- From System Env: "
                + System.getenv("TEST_ENV_VAR") + "\nUsername: " + System.getenv("username")
                + "\nFrom build.gradle: " + System.getenv("SERVICE_ENV");
        return data;
    }

}
