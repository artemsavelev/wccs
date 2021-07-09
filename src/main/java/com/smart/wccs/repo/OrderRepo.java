package com.smart.wccs.repo;

import com.smart.wccs.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    /**
     * Поисковый запрос в БД на совпадение по полям extId, customer, address
     * @param value на вход подается строка поиска отправленная с фронта
     * @return возвращаем List Order найденное совпадение по extId, customer, address
     */
    @Query("select o from Order o where lower(o.extId) like %?1% or lower(o.customer) like %?1% or lower(o.address) like %?1%")
    List<Order> findOrders(String value);

}
