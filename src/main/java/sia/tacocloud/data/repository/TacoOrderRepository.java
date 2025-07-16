package sia.tacocloud.data.repository;

import sia.tacocloud.data.model.TacoOrder;

public interface TacoOrderRepository {

    TacoOrder save(TacoOrder order);

}
