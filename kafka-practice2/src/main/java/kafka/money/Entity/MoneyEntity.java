package kafka.money.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "money")
public class MoneyEntity {
	
	@Id
	private String id;
	private int money;
}
