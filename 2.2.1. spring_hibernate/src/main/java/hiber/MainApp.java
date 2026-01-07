package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(AppConfig.class);

		UserService userService = context.getBean(UserService.class);

		Car car1 = new Car("Toyota Camry", 50);
		Car car2 = new Car("BMW X5", 30);
		Car car3 = new Car("Audi A6", 40);
		Car car4 = new Car("Lada Vesta", 10);
		Car car5 = new Car("Toyota Camry", 50); // Дубликат для теста

		userService.add(new User("Иван", "Иванов", "ivan@mail.ru", car1));
		userService.add(new User("Петр", "Петров", "petr@mail.ru", car2));
		userService.add(new User("Сергей", "Сергеев", "sergey@mail.ru", car3));
		userService.add(new User("Анна", "Сидорова", "anna@mail.ru", car4));
		userService.add(new User("Мария", "Петрова", "maria@mail.ru", car5)); // Второй пользователь с той же машиной

		System.out.println("=== Все пользователи ===");
		userService.listUsers().forEach(user -> {
			System.out.println(user.getFirstName() + " " + user.getLastName() +
					" - " + (user.getCar() != null ?
					user.getCar().getModel() + " серии " + user.getCar().getSeries() : "нет машины"));
		});

		// Тестируем HQL-запрос
		System.out.println("\n=== Поиск по машине (HQL) ===");

		// Находим первого пользователя с Toyota Camry серии 50
		User userWithToyota = userService.getUserByCarModelAndSeries("Toyota Camry", 50);
		if (userWithToyota != null) {
			System.out.println("Найден: " + userWithToyota.getFirstName() + " " +
					userWithToyota.getLastName() + " с машиной Toyota Camry серии 50");
		}

		// Находим пользователя с BMW X5
		User userWithBmw = userService.getUserByCarModelAndSeries("BMW X5", 30);
		if (userWithBmw != null) {
			System.out.println("Найден: " + userWithBmw.getFirstName() + " " +
					userWithBmw.getLastName() + " с машиной BMW X5 серии 30");
		}

		// Пробуем найти несуществующую машину
		User userNotFound = userService.getUserByCarModelAndSeries("Ferrari", 100);
		if (userNotFound == null) {
			System.out.println("Пользователь с Ferrari серии 100 не найден");
		}

		System.out.println("\n=== Проверка работы create-drop ===");
		System.out.println("При завершении программы все таблицы будут автоматически удалены");
		System.out.println("При следующем запуске - созданы заново");

		context.close();
	}
}