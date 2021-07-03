package com.smart.wccs.service;


/**
 * Интерфейс для управления наборами компонентов сметы для каждого отдела *
 */
public interface ActionToSetService {

    /**
     * Метод для добавления устройства в набор к отделу
     * @param id передаваемый параметр в URL
     */
    void toSetDevice(Long id);

    /**
     * Метод для добавления материала в набор к отделу
     * @param id передаваемый параметр в URL
     */
    void toSetMaterial(Long id);

    /**
     * Метод для добавления работ в набор к отделу
     * @param id передаваемый параметр в URL
     */
    void toSetWork(Long id);

    /**
     * Метод для удаления устройств из набора отдела
     * @param id передаваемый параметр в URL
     */
    void delSetDevice(Long id);

    /**
     * Метод для удаления материалов из набора отдела
     * @param id передаваемый параметр в URL
     */
    void delSetMaterial(Long id);

    /**
     * Метод для удаления работ из набора отдела
     * @param id передаваемый параметр в URL
     */
    void delSetWork(Long id);

}
