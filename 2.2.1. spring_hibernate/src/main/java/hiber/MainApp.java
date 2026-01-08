package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(AppConfig.class);

		UserService userService = context.getBean(UserService.class);

		Car car1 = new Car("Toyota Camry", 50);
		Car car2 = new Car("BMW X5", 30);
		Car car3 = new Car("Audi A6", 40);
		Car car4 = new Car("Toyota Camry", 50); // Дубликат car1
		Car car5 = new Car("Toyota Camry", 50); // Еще один дубликат

		userService.add(new User("Иван", "Иванов", "ivan@mail.ru", car1));
		userService.add(new User("Петр", "Петров", "petr@mail.ru", car2));
		userService.add(new User("Сергей", "Сергеев", "sergey@mail.ru", car3));
		userService.add(new User("Анна", "Сидорова", "anna@mail.ru", car4));  // Такая же машина как у Ивана
		userService.add(new User("Мария", "Кузнецова", "maria@mail.ru", car5)); // И у Марии такая же машина

		System.out.println("=== Все пользователи в базе ===");
		userService.listUsers().forEach(user -> {
			System.out.println(user.getFirstName() + " " + user.getLastName() +
					" - " + user.getCar().getModel() + " серии " + user.getCar().getSeries());
		});

		System.out.println("\n=== Тест 1: Поиск ОДНОГО пользователя по машине (getUserByCarModelAndSeries) ===");
		User singleUser = userService.getUserByCarModelAndSeries("Toyota Camry", 50);
		if (singleUser != null) {
			System.out.println("Найден первый пользователь с Toyota Camry серии 50: " +
					singleUser.getFirstName() + " " + singleUser.getLastName());
		} else {
			System.out.println("Пользователь с Toyota Camry серии 50 не найден");
		}

		System.out.println("\n=== Тест 2: Поиск ВСЕХ пользователей с одинаковой машиной (getAllUsersByCarModelAndSeries) ===");
		List<User> allToyotaUsers = userService.getAllUsersByCarModelAndSeries("Toyota Camry", 50);
		if (!allToyotaUsers.isEmpty()) {
			System.out.println("Найдено " + allToyotaUsers.size() + " пользователей с Toyota Camry серии 50:");
			for (User user : allToyotaUsers) {
				System.out.println("  - " + user.getFirstName() + " " + user.getLastName() +
						" (email: " + user.getEmail() + ")");
			}
		} else {
			System.out.println("Пользователи с Toyota Camry серии 50 не найдены");
		}

		System.out.println("\n=== Тест 3: Поиск пользователей с уникальной машиной ===");
		List<User> bmwUsers = userService.getAllUsersByCarModelAndSeries("BMW X5", 30);
		System.out.println("Найдено " + bmwUsers.size() + " пользователей с BMW X5 серии 30");

		System.out.println("\n=== Тест 4: Поиск пользователей с несуществующей машиной ===");
		List<User> ferrariUsers = userService.getAllUsersByCarModelAndSeries("Ferrari", 100);
		System.out.println("Найдено " + ferrariUsers.size() + " пользователей с Ferrari серии 100");

		context.close();
	}
}