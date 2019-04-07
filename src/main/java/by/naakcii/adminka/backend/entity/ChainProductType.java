package by.naakcii.adminka.backend.entity;

import by.naakcii.adminka.backend.DTO.ChainProductTypeDTO;
import by.naakcii.adminka.backend.utils.annotations.PureSize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id", "tooltip"})
@Entity
@Table(name = "CHAIN_PRODUCT_TYPE")
public class ChainProductType implements Serializable {
	
	private static final long serialVersionUID = 5111366809131618230L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAIN_PRODUCT_TYPE_ID")
    private Long id;
	
	@Column(name = "CHAIN_PRODUCT_TYPE_NAME")
    @NotNull(message = "ChainProductType's name mustn't be null.")
    @PureSize(
    	min = 3, 
    	max = 25,
    	message = "ChainProductType's name '${validatedValue}' must be between '{min}' and '{max}' characters long."
    )
	private String name;
	
	@Column(name = "CHAIN_PRODUCT_TYPE_SYNONYM", length = 50)
    @NotNull(message = "ChainProductType's synonym mustn't be null.")
    @PureSize(
    	min = 3, 
    	max = 25,
    	message = "ChainProductType's synonym '${validatedValue}' must be between '{min}' and '{max}' characters long."
    )
	private String synonym;
	
	@Column(name = "CHAIN_PRODUCT_TYPE_TOOLTIP")
	@Size(
		max = 255,
		message = "ChainProductType's tooltip '${validatedValue}' mustn't be more than '{max}' characters long."
	)
	private String tooltip;
	
	public ChainProductType(ChainProductTypeDTO chainProductTypeDTO) {
		this.id = chainProductTypeDTO.getId();
		this.name = chainProductTypeDTO.getName();
		this.synonym = chainProductTypeDTO.getSynonym();
		this.tooltip = chainProductTypeDTO.getTooltip();
	}
}