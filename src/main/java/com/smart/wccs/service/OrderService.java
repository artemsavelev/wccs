package com.smart.wccs.service;

import com.smart.wccs.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 */
public interface OrderService {

    /**
     *
     * @param pageable
     * @return
     */
    Page<Order> getAllOrders(Pageable pageable);

    /**
     *
     * @param id
     * @return
     */
    Order getById(Long id);

    /**
     *
     * @param order
     */
    void create(Order order);

    /**
     *
     * @param id
     * @param order
     */
    void update(Long id, Order order);

    /**
     *
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @param value
     * @return
     */
    List<Order> search(String value);

}
