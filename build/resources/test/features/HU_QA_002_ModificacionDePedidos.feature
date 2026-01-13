# language: es
Característica: HU-QA-002 - Verificar modificación de pedidos antes de enviar
  Como QA del proyecto FoodTech
  Quiero verificar que el mesero puede modificar pedidos antes de enviarlos
  Para asegurar flexibilidad en la toma de pedidos y prevenir errores

  @escenario1 @modificacion
  Escenario: Mesero elimina un producto que el cliente ya no desea
    Dado que el mesero accede al sistema de gestión de pedidos
    Cuando el mesero selecciona la primera mesa disponible
    Y el mesero agrega el producto "Ensalada de Burrata" al pedido
    Y el mesero agrega el producto "Risotto de Trufa Negra" al pedido
    Y el total de items debe ser "2"
    Cuando el mesero elimina "Ensalada de Burrata" del pedido
    Entonces el pedido debe contener únicamente "Risotto de Trufa Negra"
    Y el total de items debe ser "1"
    Y "Ensalada de Burrata" no debe aparecer en el resumen

  @escenario2 @modificacion
  Escenario: Cliente solicita menos unidades de un producto
    Dado que el mesero accede al sistema de gestión de pedidos
    Cuando el mesero selecciona la primera mesa disponible
    Y el mesero agrega el producto "Agua San Pellegrino" al pedido
    Y el mesero agrega el producto "Agua San Pellegrino" al pedido
    Y el mesero agrega el producto "Agua San Pellegrino" al pedido
    Y el mesero agrega el producto "Agua San Pellegrino" al pedido
    Y el total de items debe ser "4"
    Cuando el mesero elimina una unidad de "Agua San Pellegrino"
    Entonces "Agua San Pellegrino" debe tener cantidad "x3"
    Y el total de items debe ser "3"

  @escenario3 @modificacion
  Escenario: Cliente cancela completamente un producto
    Dado que el mesero accede al sistema de gestión de pedidos
    Cuando el mesero selecciona la primera mesa disponible
    Y el mesero agrega el producto "Vino Tinto Reserva" al pedido
    Y el mesero agrega el producto "Vino Tinto Reserva" al pedido
    Y el mesero agrega el producto "Vino Tinto Reserva" al pedido
    Y el total de items debe ser "3"
    Cuando el mesero elimina todas las unidades de "Vino Tinto Reserva"
    Entonces "Vino Tinto Reserva" no debe aparecer en el pedido
    Y el pedido debe estar vacío

  @escenario4 @modificacion
  Escenario: Mesero modifica pedido múltiples veces antes de enviar
    Dado que el mesero accede al sistema de gestión de pedidos
    Cuando el mesero selecciona la primera mesa disponible
    Y el mesero agrega el producto "Ravioli de Langosta" al pedido
    Y el mesero agrega el producto "Agua San Pellegrino" al pedido
    Y el mesero elimina "Ravioli de Langosta" del pedido
    Y el mesero agrega el producto "Solomillo Wellington" al pedido
    Entonces el pedido debe contener "Agua San Pellegrino" y "Solomillo Wellington"
    Y el total de items debe ser "2"
    Cuando el mesero envía el pedido a cocina
    Entonces el sistema debe confirmar que el pedido fue recibido
