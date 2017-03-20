package br.com.marcosgorll.busroute;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.marcosgorll.busroute.service.RouteRegister;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Now starting the server
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RouteRegister register) {
		return args -> {// There is some file?
			if (args.length < 1) {
				System.err.println("[WARN] No route file received");
				return;
			}
			File routeFile = new File(args[0]);
			// Is really a file? Maybe here we will have some throuble with links
			if (!routeFile.exists() || !routeFile.isFile()) {
				System.err.println("[WARN] Invalid route file, or not exists or is not file");
				return;
			}
			register.mountRoutes(routeFile);
		};
	}

	@Bean
	public RouteRegister routeRegister() {
		return new RouteRegister();
	}
}