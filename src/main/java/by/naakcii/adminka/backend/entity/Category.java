package by.naakcii.adminka.backend.entity;

import by.naakcii.adminka.backend.DTO.CategoryDTO;
import by.naakcii.adminka.backend.utils.annotations.PureSize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id", "icon", "priority", "subcategories"})
@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {
    
    private static final long serialVersionUID = -782539646608262755L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_NAME")
    @NotNull(message = "Category's name mustn't be null.")
    @PureSize(
    	min = 3, 
    	max = 50,
    	message = "Category's name '${validatedValue}' must be between '{min}' and '{max}' characters long."
    )
    private String name;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<
            @Valid
            @NotNull(message = "Category must have list of subcategories without null elements.")
            Subcategory> subcategories = new HashSet<Subcategory>();

    @Column(name = "CATEGORY_PRIORITY")
    @Positive(message = "Category's priority '${validatedValue}' must be positive.")
    private Integer priority;

    @Column(name = "CATEGORY_ICON")
    @Size(
    	max = 255, 
    	message = "Path to the icon of the category '${validatedValue}' mustn't be more than '{max}' characters long."
    )
    private String icon;
    
    @Column(name = "CATEGORY_IS_ACTIVE")
    @NotNull(message = "Category must have field 'isActive' defined.")
    private Boolean isActive;
    
    public Category(String name, Boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    public Category(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.icon = categoryDTO.getIcon();
        this.name = categoryDTO.getName();
        this.isActive = categoryDTO.getIsActive();
        this.priority = categoryDTO.getPriority();
    }
}
