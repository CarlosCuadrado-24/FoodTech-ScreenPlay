# language: es
Característica: HU-QA-003 - Verificar consulta de tareas por estación de cocina

  Como QA del proyecto FoodTech
  Quiero verificar que cada estación de cocina muestra correctamente sus tareas
  Para asegurar claridad operativa y evitar confusiones entre estaciones

  Antecedentes:
    Dado que el mesero accede al sistema de gestión de pedidos

  Escenario: Estación de barra muestra tareas cuando hay pedidos de bebidas
    Dado que el mesero ha seleccionado la mesa "A1"
    Y el mesero ha agregado "Gin Tonic Premium" al pedido
    Y el mesero envía el pedido a cocina
    Cuando el personal de barra accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación

  Escenario: Estación sin tareas muestra mensaje correcto
    Cuando el personal de cocina fría accede a su estación
    Entonces el sistema debe indicar que no hay tareas o mostrar cero tareas

  Escenario: Cada tarea muestra información completa de preparación
    Dado que el mesero ha seleccionado la mesa "C3"
    Y el mesero ha agregado "Cerveza Artesanal" al pedido
    Y el mesero envía el pedido a cocina
    Cuando el personal de barra accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
    Y la tarea debe mostrar información de la mesa
    Y la tarea debe mostrar identificador de orden

  Escenario: Múltiples pedidos generan múltiples tareas visibles
    Dado que el mesero ha seleccionado la mesa "D4"
    Y el mesero ha agregado "Ravioli de Ricota" al pedido
    Y el mesero envía el pedido a cocina
    Y el mesero ha seleccionado la mesa "E5"
    Y el mesero ha agregado "Solomillo de Cerdo" al pedido
    Y el mesero envía el pedido a cocina
    Cuando el personal de cocina caliente accede a su estación
    Entonces el sistema debe mostrar al menos 2 tareas en la estación
    Y cada tarea debe mostrar información de su mesa

  Escenario: Pedido mixto genera tareas en diferentes estaciones
    Dado que el mesero ha seleccionado la mesa "G7"
    Y el mesero ha agregado "Agua Mineral" al pedido
    Y el mesero ha agregado "Pasta Carbonara" al pedido
    Y el mesero ha agregado "Ensalada César" al pedido
    Y el mesero envía el pedido a cocina
    Cuando el personal de barra accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
    Cuando el personal de cocina caliente accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
    Cuando el personal de cocina fría accede a su estación
    Entonces el sistema debe mostrar al menos 1 tarea en la estación
