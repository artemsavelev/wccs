package com.smart.wccs.service.filecreator.builder.builders;

import com.smart.wccs.service.filecreator.builder.components.Sheet;

/**
 * Интерфейс Строителя объявляет все возможные этапы и шаги конфигурации
 * продукта.
 */

public interface Builder {

    void setSheet(Sheet sheet);

}
