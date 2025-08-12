package sia.tacocloud.data.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.data.model.TacoOrder;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, String> {}
