package org.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApplicationController { // Use a more descriptive class name

	@GetMapping("/about")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Success"); // Consistent spelling
	}
	@PostMapping("/ab")
	public ResponseEntity<String> wel() {
		return ResponseEntity.ok("Success"); // Consistent spelling
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationController.class, args);
	}
}
