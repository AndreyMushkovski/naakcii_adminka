package by.naakcii.adminka.backend.DTO;

import by.naakcii.adminka.backend.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDTO extends AbstractDTOEntity {

    private Long id;

    private String name;

    private Integer priority;

    private String icon;

    private Boolean isActive;

    public CategoryDTO(Category category){
    	this.id = category.getId();
    	this.name = category.getName();
    	this.priority = category.getPriority();
    	this.icon = category.getIcon();
    	this.isActive = category.getIsActive();
    }
}
