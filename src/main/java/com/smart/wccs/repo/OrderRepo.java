package com.smart.wccs.repo;

import com.smart.wccs.model.Department;
import com.smart.wccs.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    /**
     * Поисковый запрос в БД на совпадение по полям extId, customer, address
     * @param department отдел авторизированного пользователя (для изоляции по отделу)
     * @param value на вход подается строка поиска отправленная с клиента
     * @return возвращаем List Order найденное совпадение по extId, customer, address
     */
    @Query("select o from Order o where o.department = ?1 and (lower(o.extId) like %?2% or lower(o.customer) like %?2% or lower(o.address) like %?2%)")
    List<Order> findOrdersByDepartment(Department department, String value);

    /**
     * Вывод всех задач с выборкой по отделу
     * @param department отдел авторизированного пользователя (для изоляции по отделу)
     * @param pageable принимаем количество выводимых задач
     * @return возвращаем List Order с выборкой по отделу
     */
    List<Order> findAllByDepartment(Department department, Pageable pageable);

}
