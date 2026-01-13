# language: es
Característica: HU-QA-003 - Verificar consulta de tareas por estación de cocina

  Como QA del proyecto FoodTech
  Quiero verificar que cada estación de cocina muestra correctamente sus tareas
  Para asegurar claridad operativa y evitar confusiones entre estaciones

  Antecedentes:
    Dado que el mesero accede al sistema de gestión de pedidos
    Y existe al menos una mesa disponible
    Cuando el mesero selecciona la primera mesa disponible
    Y el mesero ha agregado "Gin Tonic Premium" al pedido
    Y el mesero ha agregado "Risotto de Trufa Negra" al pedido
    Y el mesero ha agregado "Ensalada de Burrata" al pedido
    Y el mesero envía el pedido a cocina

  Escenario: Estación de barra muestra tareas de bebidas
    Cuando el personal de barra accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación

  Escenario: Estación de cocina caliente muestra tareas de platos calientes
    Cuando el personal de cocina caliente accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación

  Escenario: Estación de cocina fría muestra tareas de ensaladas
    Cuando el personal de cocina fría accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación

  Escenario: Cada tarea muestra información completa
    Cuando el personal de barra accede a su estación
    Entonces cada tarea debe mostrar el número de mesa
    Y cada tarea debe mostrar el identificador de orden

  Escenario: Navegación entre estaciones funciona correctamente
    Cuando el personal de barra accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
    Cuando el personal de cocina caliente accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
    Cuando el personal de cocina fría accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
