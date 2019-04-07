package by.naakcii.adminka.backend.DTO;

import by.naakcii.adminka.backend.entity.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SubcategoryDTO extends AbstractDTOEntity {

    private Long id;

    private String name;

    private String categoryName;

    private Integer priority;

    private Boolean isActive;
    
    public SubcategoryDTO(Subcategory subcategory) {
    	this.id = subcategory.getId();
    	this.name = subcategory.getName();
    	this.categoryName = subcategory.getCategory().getName();
    	this.priority = subcategory.getPriority();
    	this.isActive = subcategory.getIsActive();
    }
}
