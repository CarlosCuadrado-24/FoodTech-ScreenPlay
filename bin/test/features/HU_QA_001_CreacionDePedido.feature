# language: es
Característica: HU-QA-001 - Verificar flujo completo de creación de pedido
  Como QA del proyecto FoodTech
  Quiero verificar que el flujo completo de creación de pedido funciona correctamente
  Para asegurar que el mesero puede crear y enviar pedidos sin errores

  @escenario1 @smoke
  Escenario: Mesero crea pedido con una bebida para una mesa disponible
    Dado que el mesero accede al sistema de gestión de pedidos
    Y la mesa "A1" está disponible
    Cuando el mesero selecciona la mesa "A1"
    Y el mesero agrega el producto "Gin Tonic Premium" al pedido
    Entonces el sistema debe mostrar el producto en el resumen de orden
    Y el total de items debe ser "1"
    Cuando el mesero envía el pedido a cocina
    Entonces el sistema debe confirmar que el pedido fue recibido
    Y la mesa "A1" debe cambiar a estado ocupada

  @escenario2
  Escenario: Mesero crea pedido mixto con bebida, plato caliente y postre
    Dado que el mesero accede al sistema de gestión de pedidos
    Y la mesa "B2" está disponible
    Cuando el mesero selecciona la mesa "B2"
    Y el mesero agrega el producto "Cerveza Artesanal" al pedido
    Y el mesero agrega el producto "Filete con Papas" al pedido
    Y el mesero agrega el producto "Tiramisú" al pedido
    Entonces el resumen debe mostrar 3 productos diferentes
    Y el total de items debe ser "3"
    Cuando el mesero envía el pedido a cocina
    Entonces el sistema debe confirmar que el pedido fue recibido

  @escenario3
  Escenario: Mesero agrega el mismo producto varias veces
    Dado que el mesero accede al sistema de gestión de pedidos
    Cuando el mesero selecciona la mesa "C3"
    Y el mesero agrega el producto "Vino Tinto Reserva" al pedido
    Y el mesero agrega el producto "Vino Tinto Reserva" al pedido
    Entonces el sistema debe mostrar el producto "Vino Tinto Reserva" con cantidad "x2"
    Y el total de items debe ser "2"

  @escenario4 @wip
  Escenario: Sistema valida que el pedido tenga al menos un producto
    Dado que el mesero accede al sistema de gestión de pedidos
    Cuando el mesero selecciona la mesa "D4"
    Y el pedido está vacío
    Entonces el botón enviar debe estar deshabilitado

  @escenario5 @wip
  Escenario: Sistema valida que se haya seleccionado una mesa
    Dado que el mesero accede al sistema de gestión de pedidos
    Y el mesero agrega el producto "Ensalada César" al pedido
    Y no hay ninguna mesa seleccionada
    Entonces el botón enviar debe estar deshabilitado
