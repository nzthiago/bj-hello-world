package com.disney.play;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @RequestMapping("/")
  public String index() {
      return "hello world";
  }

  public static void main(String[] args) throws FileNotFoundException {
    SpringApplication.run(Application.class, args);
  }

}