package sideseven_spring;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sideseven_spring.service.ClienteService;
import sideseven_spring.service.ProductoService;
import sideseven_spring.service.VentaService;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MainApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setLogStartupInfo(false);
		app.run(args);
	}

	@Bean
	CommandLineRunner run(ClienteService clienteService, ProductoService productoService, VentaService ventaService) {
		return args -> {
			MenuConsola menu = new MenuConsola(clienteService, productoService, ventaService);
			menu.mostrarMenuPrincipal();
		};
	}

}
