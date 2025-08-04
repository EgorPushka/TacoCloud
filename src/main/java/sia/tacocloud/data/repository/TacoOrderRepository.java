package sia.tacocloud.data.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.data.model.TacoOrder;

import java.util.List;
import java.util.UUID;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, UUID> {

    List<TacoOrder> findByDeliveryZip(String deliveryZip);

}
