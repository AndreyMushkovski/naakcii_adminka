package by.naakcii.adminka.backend.entity;

import by.naakcii.adminka.backend.DTO.UnitOfMeasureDTO;
import by.naakcii.adminka.backend.utils.annotations.PureSize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Optional;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "UNIT_OF_MEASURE")
public class UnitOfMeasure {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UOM_ID")
    private Long id;
	
	@Column(name = "UOM_NAME", unique = true, length = 50)
    @NotNull(message = "UoM's name mustn't be null.")
    @PureSize(
    	min = 2, 
    	max = 10,
    	message = "UOM's name '${validatedValue}' must be between '{min}' and '{max}' characters long."
    )
	private String name;

	@Column(name = "UOM_STEP")
	@NotNull(message = "UoM's step mustn't be null.")
	@Digits(
			integer = 3,
			fraction = 1,
			message = "UOM's step '${validatedValue}' must have up to '{integer}' integer digits and '{fraction}' fraction digits."
	)
	@Positive(message = "UoM's step '${validatedValue}' must be positive.")
    private BigDecimal step;
	
	public UnitOfMeasure(UnitCode unitCode) {
		this.name = unitCode.getRepresentation();
		this.step = unitCode.getDefaultStep();
	}

	public UnitOfMeasure(UnitOfMeasureDTO unitOfMeasureDTO) {
		this.id = unitOfMeasureDTO.getId();
		this.name = unitOfMeasureDTO.getName();
		this.step = unitOfMeasureDTO.getStep();
	}
}


enum UnitCode {

	KG("кг", new BigDecimal("0.1")),
	PC("шт", new BigDecimal("1.0"));

	private String representation;
	private BigDecimal defaultStep;

	UnitCode(String representation, BigDecimal defaultStep) {
		this.representation = representation;
		this.defaultStep = defaultStep;
	}

	public String getRepresentation() {
		return this.representation;
	}

	public BigDecimal getDefaultStep() {
		return this.defaultStep;
	}

	public static Optional<UnitCode> getByRepresentation(String representation) {
		for (UnitCode unitCode : UnitCode.values()) {
			if (unitCode.representation.equalsIgnoreCase(representation)) {
				return Optional.of(unitCode);
			}
		}

		return Optional.empty();
	}
}

