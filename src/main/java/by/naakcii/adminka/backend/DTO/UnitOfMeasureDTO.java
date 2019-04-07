package by.naakcii.adminka.backend.DTO;

import by.naakcii.adminka.backend.entity.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class UnitOfMeasureDTO extends AbstractDTOEntity {

    private Long id;
    private String name;
    private BigDecimal step;

    public UnitOfMeasureDTO(UnitOfMeasure unitOfMeasure) {
        this.id = unitOfMeasure.getId();
        this.name = unitOfMeasure.getName();
        this.step = unitOfMeasure.getStep();
    }
}