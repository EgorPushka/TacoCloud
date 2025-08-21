package sia.tacocloud.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DesignTacoFrom {

  @NotBlank(message = "Name is required")
  private String name;

  @Size(min = 1, message = "At least one ingredient must be selected")
  private List<String> ingredientIds = new ArrayList<>();
}
