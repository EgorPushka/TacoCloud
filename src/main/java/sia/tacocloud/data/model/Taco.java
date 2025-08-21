package sia.tacocloud.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Taco {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date createdAt = new Date();

  @NotNull
  @Size(min = 3, message = "Name must be at least 5 char")
  private String name;

  @NotNull
  @ManyToMany
  @Size(min = 1, message = "You must choose at least 1 ingredient")
  @JoinTable(
          name = "Ingredient_Ref",
          joinColumns = @JoinColumn(name = "taco", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "ingredient", referencedColumnName = "id")
  )
  @OrderColumn(name = "taco_key")
  private List<Ingredient> ingredients = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "taco_order", nullable = false)
  @ToString.Exclude
  private TacoOrder order;

  @Column(name = "taco_order_key", nullable = false)
  private Integer orderKey;

  public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
  }
}
