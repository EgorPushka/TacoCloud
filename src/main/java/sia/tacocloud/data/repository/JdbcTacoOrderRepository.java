package sia.tacocloud.data.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sia.tacocloud.data.model.Ingredient;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTacoOrderRepository implements TacoOrderRepository {

    private final JdbcOperations jdbcOperations;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        var pscf = new PreparedStatementCreatorFactory(
                """
                        INSERT INTO Taco_Order
                        (delivery_Name, delivery_Street, delivery_City, delivery_State, delivery_Zip, cc_number, cc_expiration, cc_cvv, placed_at)
                        VALUES ( ?,?,?,?,?,?,?,?,? )
                        """,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());

        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()
                )
        );

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        var orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        var tacos = order.getTacos();
        int i = 0;
        for (var taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }


    private long saveTaco(Long orederId, int orederKey, Taco taco) {
        taco.setCreatedAt(new Date());
        var pscf = new PreparedStatementCreatorFactory(
                """
                        INSERT INTO Taco (name, created_at, taco_order, taco_order_key)
                        VALUES ( ?,?,?,? )
                        """,
                Types.VARCHAR, Types.TIMESTAMP, Types.LONGVARCHAR, Types.LONGVARCHAR
        );
        pscf.setReturnGeneratedKeys(true);
        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orederId,
                        orederKey
                )
        );
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        var tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
        int key = 0;
        for (var ing : ingredients) {
            jdbcOperations.update("""
                            INSERT INTO Ingredient_Ref (ingredient, taco, taco_key)
                            VALUES ( ?,?,? )
                            """,
                    ing.id(), tacoId, key++);
        }
    }


}

