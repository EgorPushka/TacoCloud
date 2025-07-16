package sia.tacocloud.data.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.data.model.TacoOrder;

import java.util.List;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryZip(String deliveryZip);

}
