/**
 * 
 */
package nz.hmp.tither.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Entity//(name = "\"income\"")
@Data
public class Income {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private Long id;
	@NotNull(message = "Amount is mandatory")
	@Getter @Setter private Double amount;
}
