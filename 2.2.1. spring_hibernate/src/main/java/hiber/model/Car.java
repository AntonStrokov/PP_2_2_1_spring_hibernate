package hiber.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "model")
	private String model;

	@Column(name = "series")
	private int series;

	// Конструктор без id для удобства создания
	public Car(String model, int series) {
		this.model = model;
		this.series = series;
	}
}