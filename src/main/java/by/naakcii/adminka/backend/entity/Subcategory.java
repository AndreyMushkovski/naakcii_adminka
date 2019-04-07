package by.naakcii.adminka.backend.entity;

import by.naakcii.adminka.backend.DTO.SubcategoryDTO;
import by.naakcii.adminka.backend.utils.annotations.PureSize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id", "priority", "products"})
@Entity
@Table(name = "SUBCATEGORY")
public class Subcategory implements Serializable {

	private static final long serialVersionUID = 4720680821468502372L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBCATEGORY_ID")
	private Long id;
	
	@Column(name = "SUBCATEGORY_NAME")
	@NotNull(message = "Subcategory's name mustn't be null.")
    @PureSize(
    	min = 3, 
    	max = 50,
    	message = "Subcategory's name '${validatedValue}' must be between '{min}' and '{max}' characters long."
    )
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	@NotNull(message = "Subcategory must have category.")
	@Valid
	private Category category;
	
	@OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
	private Set<
			@Valid
			@NotNull(message = "Subcategory must have list of products without null elements.")
			Product> products = new HashSet<Product>();
	
	@Column(name = "SUBCATEGORY_PRIORITY")
	@Positive(message = "Subcategory's priority '${validatedValue}' must be positive.")
	private Integer priority;	
	
	@Column(name = "SUBCATEGORY_IS_ACTIVE")
	@NotNull(message = "Subcategory must have field 'isActive' defined.")
	private Boolean isActive;
	
	public Subcategory(SubcategoryDTO subcategoryDTO) {
		this.id = subcategoryDTO.getId();
		this.name = subcategoryDTO.getName();
		this.isActive = subcategoryDTO.getIsActive();
		this.priority = subcategoryDTO.getPriority();
	}

	public Subcategory(String name, Boolean isActive, Category category) {
		this.name = name;
		this.isActive = isActive;
		this.category = category;
		category.getSubcategories().add(this);
	}
}