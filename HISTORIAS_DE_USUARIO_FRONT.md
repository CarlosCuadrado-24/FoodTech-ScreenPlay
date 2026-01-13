# 📋 Historias de Usuario - FoodTech Frontend (Interfaz de Mesero)

## 🎯 Principios INVEST

Todas las historias de usuario de este proyecto cumplen con los principios INVEST:

- **I**ndependent (Independiente): Cada historia puede desarrollarse y entregarse por separado
- **N**egotiable (Negociable): Los detalles pueden refinarse con el equipo
- **V**aluable (Valiosa): Aporta valor observable al negocio
- **E**stimable (Estimable): Se puede estimar el esfuerzo necesario
- **S**mall (Pequeña): Se puede completar en una iteración
- **T**estable (Testeable): Se puede verificar su cumplimiento

---

## HU-FRONT-001: Visualizar disponibilidad de mesas en tiempo real

### Descripción

**Como** mesero del restaurante  
**Quiero** ver el estado actual de todas las mesas (disponibles u ocupadas)  
**Para** saber qué mesas puedo asignar a nuevos clientes sin consultar manualmente con cocina

### Contexto de Negocio

Actualmente, los meseros deben preguntar al personal de cocina o revisar anotaciones manuales para saber qué mesas están ocupadas. Esto genera:
- Pérdida de tiempo en consultas innecesarias
- Riesgo de asignar mesas que ya tienen pedidos en preparación
- Confusión cuando múltiples meseros atienden el mismo comedor
- Experiencia negativa cuando se intenta tomar un pedido de una mesa que aún tiene orden activa

La solución muestra automáticamente el estado real de cada mesa basándose en las órdenes en cocina.

### Valor de Negocio

- Reducción del tiempo de consultas entre meseros y cocina
- Eliminación de errores de asignación de mesas
- Mejor coordinación del equipo de servicio
- Mayor rotación de mesas por asignación eficiente

---

### Criterios de Aceptación

#### Escenario 1: Identificación visual de mesas disponibles

```gherkin
Scenario: Mesero visualiza mesas disponibles para nuevos clientes
  Given que el mesero ingresa al sistema de gestión de pedidos
  When el sistema carga la zona de mesas
  Then el mesero puede identificar visualmente las mesas disponibles
  And el mesero puede identificar visualmente las mesas ocupadas
  And cada mesa muestra claramente su identificador
```

#### Escenario 2: Mesa se marca como ocupada al enviar pedido

```gherkin
Scenario: Mesa cambia a ocupada después de enviar una orden
  Given que la mesa "A2" está disponible
  And el mesero selecciona la mesa "A2"
  When el mesero agrega productos al pedido
  And el mesero envía la orden a cocina
  Then la mesa "A2" debe mostrar estado ocupada
  And otros meseros no pueden asignar la mesa "A2" a nuevos clientes
```

#### Escenario 3: Mesa permanece ocupada mientras hay preparación pendiente

```gherkin
Scenario: Mesa ocupada mientras cocina tiene tareas activas
  Given que la mesa "B1" tiene una orden enviada a cocina
  And la orden tiene tareas en cualquier estación que no están completadas
  When el mesero consulta el estado de las mesas
  Then la mesa "B1" debe aparecer como ocupada
```

#### Escenario 4: Mesa se marca disponible cuando todo está completado

```gherkin
Scenario: Mesa vuelve a disponible al completarse toda la preparación
  Given que la mesa "C3" tenía una orden en cocina
  And todas las estaciones han completado sus tareas para esa orden
  When el sistema actualiza el estado de las mesas
  Then la mesa "C3" debe aparecer como disponible
  And el mesero puede asignar la mesa "C3" a nuevos clientes
```

#### Escenario 5: Prevención de selección de mesas ocupadas

```gherkin
Scenario: Sistema impide tomar pedidos en mesas ocupadas
  Given que la mesa "A4" está ocupada con una orden en preparación
  When el mesero intenta seleccionar la mesa "A4"
  Then el sistema no permite seleccionar la mesa ocupada
  And el mesero recibe indicación de que la mesa no está disponible
```

---

## HU-FRONT-002: Construir pedido por categorías de productos

### Descripción

**Como** mesero del restaurante  
**Quiero** explorar el menú organizado por categorías de productos  
**Para** encontrar rápidamente lo que el cliente solicita sin revisar todo el menú

### Contexto de Negocio

Cuando un cliente ordena, el mesero debe buscar entre todos los productos del menú. Con menús extensos, esto genera:
- Tiempo excesivo en la toma de pedidos
- Riesgo de no encontrar productos solicitados
- Frustración del mesero al navegar información desordenada
- Experiencia de servicio lenta que afecta la satisfacción del cliente

La solución organiza el menú por categorías para acceso rápido a cada tipo de producto.

### Valor de Negocio

- Reducción del tiempo de toma de pedidos
- Mejora en la experiencia del cliente por servicio más ágil
- Menor margen de error al encontrar productos específicos
- Incremento en la productividad del personal de servicio

---

### Criterios de Aceptación

#### Escenario 1: Visualización de todas las categorías disponibles

```gherkin
Scenario: Mesero accede al menú completo sin filtros
  Given que el mesero ha seleccionado una mesa disponible
  When el mesero accede al catálogo de productos
  Then el sistema muestra todos los productos del menú
  And el mesero puede ver categorías como bebidas, platos calientes, platos fríos y postres
```

#### Escenario 2: Filtrado por categoría específica

```gherkin
Scenario: Mesero filtra solo bebidas para encontrarlas rápidamente
  Given que el mesero está construyendo un pedido
  And el cliente solicita solo bebidas
  When el mesero selecciona la categoría de bebidas
  Then el sistema muestra únicamente productos de bebidas
  And no se muestran productos de otras categorías
```

#### Escenario 3: Cambio entre categorías durante el pedido

```gherkin
Scenario: Mesero navega entre categorías mientras toma el pedido
  Given que el mesero está visualizando productos de una categoría
  When el cliente solicita productos de otra categoría
  Then el mesero puede cambiar fácilmente a otra categoría
  And los productos agregados previamente se mantienen en el pedido
```

#### Escenario 4: Vuelta a vista completa del menú

```gherkin
Scenario: Mesero regresa a ver todos los productos sin filtro
  Given que el mesero está visualizando una categoría específica
  When el mesero necesita ver todos los productos disponibles
  Then el mesero puede volver a la vista completa del menú
  And se muestran productos de todas las categorías nuevamente
```

---

## HU-FRONT-003: Construir pedido con múltiples productos

### Descripción

**Como** mesero del restaurante  
**Quiero** agregar múltiples productos al pedido antes de enviarlo  
**Para** tomar todo lo que el cliente solicita en una sola interacción con el sistema

### Contexto de Negocio

Los clientes suelen ordenar varios productos (bebidas, entradas, platos fuertes, postres) en la misma visita. Si el sistema solo permite enviar productos uno por uno:
- El mesero debe hacer múltiples envíos a cocina
- Se generan múltiples órdenes para la misma mesa
- Cocina recibe información fragmentada
- El cliente experimenta entregas desordenadas

La solución permite construir un pedido completo antes de enviarlo a cocina.

### Valor de Negocio

- Una sola orden por mesa genera claridad operativa
- Cocina recibe información completa de lo que el cliente ordenó
- Reducción de errores por órdenes fragmentadas
- Mejor experiencia del cliente por servicio organizado

---

### Criterios de Aceptación

#### Escenario 1: Agregar un producto al pedido

```gherkin
Scenario: Mesero agrega el primer producto solicitado
  Given que el mesero ha seleccionado una mesa disponible
  And el pedido está vacío
  When el mesero selecciona un producto del menú
  Then el producto se agrega al resumen del pedido
  And el mesero puede ver el producto agregado en la lista de pedido
```

#### Escenario 2: Agregar múltiples productos diferentes

```gherkin
Scenario: Cliente solicita varios productos diferentes
  Given que el mesero está construyendo un pedido
  When el mesero agrega el producto "Gin Tonic Premium"
  And el mesero agrega el producto "Ensalada César"
  And el mesero agrega el producto "Tiramisú"
  Then el pedido debe contener los 3 productos
  And cada producto aparece una vez en el resumen
```

#### Escenario 3: Agregar múltiples unidades del mismo producto

```gherkin
Scenario: Cliente solicita dos bebidas iguales
  Given que el mesero está construyendo un pedido
  When el mesero agrega el producto "Vino Tinto Reserva"
  And el mesero agrega nuevamente el producto "Vino Tinto Reserva"
  Then el pedido debe mostrar "Vino Tinto Reserva" con cantidad 2
  And no se duplica el producto en la lista
```

#### Escenario 4: Indicación visual de productos ya agregados

```gherkin
Scenario: Mesero identifica qué productos ya agregó
  Given que el mesero ha agregado varios productos al pedido
  When el mesero revisa el catálogo de productos
  Then el sistema indica visualmente cuáles productos ya están en el pedido
  And el mesero puede distinguir productos agregados de los no agregados
```

#### Escenario 5: Visualización del total de items

```gherkin
Scenario: Mesero verifica cuántos items lleva el pedido
  Given que el mesero ha agregado varios productos al pedido
  When el mesero revisa el resumen de pedido
  Then el sistema muestra el total de items agregados
  And el total refleja la suma de todas las cantidades
```

---

## HU-FRONT-004: Modificar pedido antes de enviarlo

### Descripción

**Como** mesero del restaurante  
**Quiero** corregir o eliminar productos del pedido antes de enviarlo  
**Para** ajustar el pedido si el cliente cambia de opinión o si cometí un error al agregar

### Contexto de Negocio

Es común que los clientes cambien de opinión antes de confirmar el pedido, o que el mesero agregue productos por error. Si no se puede modificar el pedido:
- Se envían productos no solicitados a cocina
- Se genera desperdicio de preparación
- El cliente recibe lo que no pidió
- Se debe cancelar toda la orden para corregir un solo producto

La solución permite modificar el pedido antes de enviarlo.

### Valor de Negocio

- Reducción de desperdicios por productos no solicitados
- Mayor satisfacción del cliente al recibir exactamente lo ordenado
- Ahorro operativo al no preparar productos cancelados
- Flexibilidad en la toma de pedidos

---

### Criterios de Aceptación

#### Escenario 1: Eliminar un producto del pedido

```gherkin
Scenario: Cliente decide no ordenar un producto ya agregado
  Given que el mesero ha agregado varios productos al pedido
  And el producto "Ensalada César" está en el pedido
  When el cliente indica que ya no desea la "Ensalada César"
  And el mesero elimina ese producto del pedido
  Then el producto "Ensalada César" ya no aparece en el resumen
  And el total de items disminuye correctamente
```

#### Escenario 2: Reducir cantidad de un producto

```gherkin
Scenario: Cliente ordena menos cantidad de lo inicialmente indicado
  Given que el pedido contiene "Cerveza Artesanal" con cantidad 3
  When el cliente indica que solo quiere 2 cervezas
  And el mesero elimina una unidad de "Cerveza Artesanal"
  Then el pedido debe mostrar "Cerveza Artesanal" con cantidad 2
  And el total de items se ajusta correctamente
```

#### Escenario 3: Eliminar todas las unidades de un producto

```gherkin
Scenario: Cliente cancela completamente un producto con múltiples unidades
  Given que el pedido contiene "Agua Mineral" con cantidad 4
  When el mesero elimina todas las unidades de "Agua Mineral"
  Then el producto "Agua Mineral" desaparece completamente del pedido
```

#### Escenario 4: Pedido vacío después de eliminar todos los productos

```gherkin
Scenario: Mesero elimina todos los productos agregados
  Given que el pedido tiene 3 productos diferentes
  When el mesero elimina todos los productos uno por uno
  Then el pedido queda vacío
  And el sistema indica que no hay productos en el pedido
```

---

## HU-FRONT-005: Enviar pedido completo a cocina

### Descripción

**Como** mesero del restaurante  
**Quiero** enviar el pedido completo a cocina cuando el cliente confirma su orden  
**Para** que todas las estaciones reciban sus tareas y comiencen la preparación

### Contexto de Negocio

Una vez que el cliente confirma su orden, el mesero debe transmitir esa información a cocina. Si este proceso no es claro:
- El mesero no sabe si la orden fue recibida
- Pueden perderse pedidos
- No hay confirmación de que cocina inició la preparación
- El mesero debe verificar manualmente con cocina

La solución permite enviar el pedido con confirmación clara de recepción.

### Valor de Negocio

- Comunicación confiable entre servicio y cocina
- Eliminación de pedidos perdidos
- Inicio inmediato de preparación
- Trazabilidad de cada orden

---

### Criterios de Aceptación

#### Escenario 1: Envío exitoso con pedido completo

```gherkin
Scenario: Mesero envía orden con productos válidos
  Given que el mesero ha seleccionado la mesa "A1"
  And el pedido contiene al menos un producto
  When el mesero confirma el envío del pedido
  Then el sistema transmite el pedido a cocina
  And el mesero recibe confirmación de que el pedido fue recibido
```

#### Escenario 2: Confirmación muestra información de la orden

```gherkin
Scenario: Sistema confirma los detalles de la orden enviada
  Given que el mesero envía un pedido para la mesa "B3"
  When el sistema procesa exitosamente la orden
  Then la confirmación muestra el identificador de la orden
  And la confirmación muestra el número de mesa
  And la confirmación indica cuántas tareas se crearon en cocina
```

#### Escenario 3: Prevención de envío sin productos

```gherkin
Scenario: Sistema no permite enviar pedidos vacíos
  Given que el mesero ha seleccionado una mesa
  And el pedido está vacío sin ningún producto
  When el mesero intenta enviar el pedido
  Then el sistema solicita agregar al menos un producto
  And el pedido no se transmite a cocina
```

#### Escenario 4: Prevención de envío sin mesa seleccionada

```gherkin
Scenario: Sistema no permite enviar sin mesa asignada
  Given que el mesero ha agregado productos al pedido
  And no hay ninguna mesa seleccionada
  When el mesero intenta enviar el pedido
  Then el sistema solicita seleccionar una mesa primero
  And el pedido no se transmite a cocina
```

#### Escenario 5: Pedido se limpia después del envío exitoso

```gherkin
Scenario: Sistema queda listo para el siguiente pedido
  Given que el mesero envía exitosamente un pedido
  When el sistema confirma la recepción
  Then el resumen de pedido se vacía
  And el sistema queda listo para tomar un nuevo pedido
```

---

## HU-FRONT-006: Monitoreo y visualización del estado y progreso de órdenes

### Descripción

**Como** mesero del restaurante  
**Quiero** ver en un solo lugar el estado y el progreso de las órdenes que envié a cocina  
**Para** informar al cliente con precisión sobre cuándo estará su pedido y actuar oportunamente

### Contexto de Negocio

Cada orden se divide en tareas por estación (barra, cocina caliente, cocina fría). Los meseros necesitan una visión clara y unificada para:
- Identificar si una orden está en cola, en preparación o lista
- Ver qué productos componen cada orden
- Conocer el avance global de la orden para informar tiempos estimados

Sin esta visibilidad, los meseros interrumpen a cocina, dan información imprecisa al cliente y no pueden anticipar entregas.

### Valor de Negocio

- Información precisa y única para comunicar tiempos al cliente
- Menos interrupciones al personal de cocina
- Mayor eficiencia en la entrega y servicio al cliente
- Mejora en la experiencia al reducir incertidumbre

---

### Criterios de Aceptación

#### Escenario 1: Listado unificado de órdenes activas

```gherkin
Scenario: Mesero ve todas las órdenes activas en un único listado
  Given que el mesero ha enviado varias órdenes a cocina
  When el mesero consulta la sección de órdenes en cocina
  Then el mesero ve una lista con todas las órdenes activas
  And cada entrada muestra el número de orden y la mesa asociada
```

#### Escenario 2: Identificación clara de estado por orden

```gherkin
Scenario: Mesero identifica rápidamente el estado de una orden
  Given que existe una orden para la mesa "A3"
  When la orden está en diferentes fases de progreso
  Then el mesero puede ver si la orden está "En Cola", "Preparando" o "Lista"
  And el mesero entiende qué significa cada estado para la entrega
```

#### Escenario 3: Visualización de productos por orden

```gherkin
Scenario: Mesero revisa los productos incluidos en una orden
  Given que una orden está en preparación
  When el mesero consulta los detalles de esa orden
  Then el mesero ve la lista completa de productos que componen la orden
  And puede confirmarlo con el cliente
```

#### Escenario 4: Indicador de progreso basado en estaciones

```gherkin
Scenario: Mesero ve el progreso de una orden según estaciones
  Given que una orden tiene tareas en barra, cocina caliente y cocina fría
  And inicialmente ninguna tarea está completada
  When una estación completa sus tareas
  Then el progreso de la orden aumenta proporcionalmente
  And el mesero ve el porcentaje de avance y cuántas estaciones faltan
```

#### Escenario 5: Orden lista y notificación de recogida

```gherkin
Scenario: Mesero es notificado cuando la orden está completamente lista
  Given que todas las estaciones han completado sus tareas para una orden
  When la orden alcanza 100% de progreso
  Then la orden aparece como "Lista"
  And el mesero recibe indicación clara de que puede recoger y servir la orden
```


## HU-FRONT-007: Visualizar tareas asignadas a estación de trabajo

### Descripción

**Como** personal de cocina (barra, cocina caliente o cocina fría)  
**Quiero** ver las tareas que están asignadas a mi estación de trabajo  
**Para** saber qué productos debo preparar, para qué mesa y en qué orden

### Contexto de Negocio

El personal de cada estación (barra, cocina caliente, cocina fría) debe conocer:
- Qué productos le corresponde preparar
- Para qué mesa es cada preparación
- De qué orden forma parte cada tarea
- Qué tareas tiene pendientes vs. en proceso

Sin esta visibilidad clara:
- El personal no sabe qué preparar primero
- Se pierde el contexto de la mesa asociada
- No hay claridad sobre qué tareas están esperando atención
- El servicio al cliente se retrasa por falta de organización

La solución muestra de forma clara las tareas de cada estación.

### Valor de Negocio

- Claridad operativa para el personal de cocina
- Priorización efectiva de tareas pendientes
- Reducción de errores por confusión de tareas
- Mejora en tiempos de preparación

---

### Criterios de Aceptación

#### Escenario 1: Visualización de tareas de la estación

```gherkin
Scenario: Personal de barra ve sus tareas asignadas
  Given que el personal accede a la vista de estación de barra
  When el sistema carga las tareas
  Then el personal ve únicamente las tareas asignadas a la estación de barra
  And no se muestran tareas de otras estaciones
```

#### Escenario 2: Información completa por tarea

```gherkin
Scenario: Personal identifica los detalles de una tarea
  Given que hay una tarea asignada a la estación
  When el personal revisa esa tarea
  Then el personal ve qué productos debe preparar
  And el personal ve para qué mesa es la preparación
  And el personal ve el número de orden asociada
```

#### Escenario 3: Inicio de preparación de tarea

```gherkin
Scenario: Personal comienza a preparar una tarea pendiente
  Given que hay una tarea con estado pendiente
  When el personal inicia la preparación de esa tarea
  Then la tarea cambia a estado en preparación
  And otros miembros del equipo pueden ver que esa tarea ya está siendo trabajada
```

#### Escenario 4: Múltiples tareas visibles simultáneamente

```gherkin
Scenario: Estación con varias tareas pendientes
  Given que hay 5 tareas asignadas a la estación de cocina caliente
  When el personal consulta su estación
  Then el personal ve las 5 tareas en la lista
  And puede identificar cuáles son prioritarias
```

#### Escenario 5: Vista cuando no hay tareas asignadas

```gherkin
Scenario: Estación sin tareas pendientes o en proceso
  Given que no hay tareas asignadas a la estación de cocina fría
  When el personal consulta su estación
  Then el sistema indica claramente que no hay tareas activas
  And el personal sabe que puede esperar nuevas órdenes
```

---

## HU-FRONT-008: Filtrar tareas por estado en estación

### Descripción

**Como** personal de cocina en mi estación de trabajo  
**Quiero** filtrar las tareas por su estado (todas, pendientes, en preparación, completadas)  
**Para** enfocarme en las tareas que requieren mi atención inmediata

### Contexto de Negocio

Durante el servicio, cada estación acumula tareas en diferentes estados:
- Pendientes: esperando ser iniciadas
- En preparación: actualmente siendo trabajadas
- Completadas: ya finalizadas

Sin capacidad de filtrado:
- El personal ve todas las tareas mezcladas
- Pierde tiempo revisando tareas ya completadas
- No puede enfocarse solo en lo pendiente
- La pantalla se satura con información irrelevante

La solución permite filtrar tareas según su estado para mejor organización.

### Valor de Negocio

- Mayor enfoque en tareas prioritarias
- Reducción de distracciones visuales
- Agilidad en identificación de trabajo pendiente
- Mejor gestión del flujo de trabajo

---

### Criterios de Aceptación

#### Escenario 1: Filtro para ver todas las tareas

```gherkin
Scenario: Personal revisa todas las tareas sin filtro
  Given que la estación tiene tareas en diferentes estados
  When el personal selecciona el filtro "Todas"
  Then el personal ve las tareas pendientes, en preparación y completadas
  And puede tener una vista completa del trabajo del turno
```

#### Escenario 2: Filtro para ver solo tareas pendientes

```gherkin
Scenario: Personal quiere ver solo lo que falta por iniciar
  Given que hay tareas en diferentes estados en la estación
  When el personal selecciona el filtro "Pendiente"
  Then el personal ve únicamente las tareas que aún no se han iniciado
  And no se muestran tareas en preparación ni completadas
```

#### Escenario 3: Filtro para ver tareas en preparación

```gherkin
Scenario: Personal revisa qué está actualmente en proceso
  Given que hay tareas siendo trabajadas por el equipo
  When el personal selecciona el filtro "En Preparación"
  Then el personal ve únicamente las tareas que están siendo preparadas
  And puede identificar qué está ocupando al equipo actualmente
```

#### Escenario 4: Filtro para ver tareas completadas

```gherkin
Scenario: Personal verifica qué se ha completado
  Given que varias tareas ya fueron finalizadas
  When el personal selecciona el filtro "Completada"
  Then el personal ve únicamente las tareas ya finalizadas
  And puede confirmar qué preparaciones están listas
```

#### Escenario 5: Cambio dinámico de filtros

```gherkin
Scenario: Personal cambia de filtro según necesidad
  Given que el personal está visualizando tareas pendientes
  When el personal cambia al filtro de completadas
  Then la vista se actualiza mostrando solo tareas completadas
  And el cambio es inmediato sin perder contexto
```

#### Escenario 6: Filtro sin resultados

```gherkin
Scenario: Filtro seleccionado no tiene tareas
  Given que no hay tareas en estado pendiente en la estación
  When el personal selecciona el filtro "Pendiente"
  Then el sistema indica claramente que no hay tareas pendientes
  And el personal entiende que no hay trabajo nuevo por iniciar
```

---

## HU-FRONT-009: Actualización automática de estados

### Descripción

**Como** mesero del restaurante  
**Quiero** que los estados de mesas y órdenes se actualicen automáticamente  
**Para** ver información siempre actualizada sin tener que recargar manualmente

### Contexto de Negocio

En un restaurante, los estados cambian constantemente:
- Cocina completa tareas continuamente
- Mesas se ocupan y liberan
- Órdenes avanzan en su preparación

Si el mesero ve información desactualizada:
- Puede intentar asignar mesas ya ocupadas
- No se entera cuando las órdenes están listas
- Debe recargar manualmente constantemente
- Pierde tiempo en verificación manual

La solución actualiza automáticamente los estados en tiempo real.

### Valor de Negocio

- Información siempre actualizada sin esfuerzo del mesero
- Respuesta inmediata a cambios en cocina
- Reducción de errores por información desactualizada
- Mayor agilidad en el servicio

---

### Criterios de Aceptación

#### Escenario 1: Actualización periódica automática

```gherkin
Scenario: Sistema actualiza estados sin intervención del mesero
  Given que el mesero está consultando el estado de cocina
  When transcurre un período de tiempo
  Then el sistema actualiza automáticamente los estados
  And el mesero ve la información más reciente sin recargar
```

#### Escenario 2: Reflejo de cambios en tiempo de preparación

```gherkin
Scenario: Mesero ve cambios conforme cocina trabaja
  Given que el mesero está observando una orden en preparación
  When una estación completa su tarea
  Then el progreso de la orden se actualiza automáticamente
  And el mesero ve el nuevo porcentaje sin actualizar manualmente
```

#### Escenario 3: Actualización de disponibilidad de mesas

```gherkin
Scenario: Mesa se muestra ocupada cuando se envía orden
  Given que el mesero está viendo las mesas disponibles
  When otro mesero envía una orden para una mesa disponible
  Then esa mesa se actualiza automáticamente a ocupada
  And el primer mesero ve el cambio sin recargar
```

#### Escenario 4: Actualización manual disponible

```gherkin
Scenario: Mesero puede forzar actualización inmediata
  Given que el mesero quiere verificar el estado más reciente
  When el mesero solicita actualizar manualmente
  Then el sistema consulta inmediatamente el estado actual
  And muestra la información más reciente
```

---

## 📊 Matriz de Trazabilidad Frontend

| Historia | Actor | Prioridad | Complejidad | Depende de Backend |
|----------|-------|-----------|-------------|-------------------|
| HU-FRONT-001 | Mesero | Alta | Media | HU-001, HU-002 |
| HU-FRONT-002 | Mesero | Media | Baja | - |
| HU-FRONT-003 | Mesero | Alta | Media | - |
| HU-FRONT-004 | Mesero | Media | Baja | - |
| HU-FRONT-005 | Mesero | Alta | Media | HU-001 |
| HU-FRONT-006 | Mesero | Alta | Media | HU-002, HU-003 |
| HU-FRONT-007 | Cocina | Alta | Media | HU-002 |
| HU-FRONT-008 | Cocina | Media | Baja | HU-002 |
| HU-FRONT-009 | Mesero/Cocina | Media | Media | HU-002, HU-003 |

---

## 🎯 Orden de Implementación Sugerido

1. **Sprint 1:** HU-FRONT-002, HU-FRONT-003, HU-FRONT-004 (Gestión básica de pedidos)
2. **Sprint 2:** HU-FRONT-005, HU-FRONT-001 (Envío y gestión de mesas)
3. **Sprint 3:** HU-FRONT-006, HU-FRONT-007 (Monitoreo de órdenes y vista de estaciones)
4. **Sprint 4:** HU-FRONT-008, HU-FRONT-009 (Filtros y actualización automática)

---

## 📝 Notas Importantes

### Lenguaje de Negocio

Todos los criterios de aceptación están escritos en **lenguaje de negocio**, evitando términos técnicos:
- ✅ "el mesero selecciona un producto" (no "el mesero hace click en la tarjeta")
- ✅ "el sistema indica visualmente" (no "se muestra un badge en el div")
- ✅ "el pedido se vacía" (no "se ejecuta el hook reset")
- ✅ "el mesero ve un indicador" (no "se renderiza la barra de progreso")

### Validez Tecnológica

Los criterios son **independientes de la implementación**:
- ✅ Válidos si usas React, Vue o Angular
- ✅ Válidos si cambias de Tailwind a otro framework CSS
- ✅ Válidos si modificas los componentes internos
- ✅ Válidos si cambias la estructura de estado

### Orientación a QA

Los escenarios están escritos para que **QA pueda entenderlos y probarlos** sin conocimiento del código frontend.

### Complementariedad con Backend

Estas historias **complementan las del backend**:
- Backend: "el sistema genera tareas por estación"
- Frontend: "el mesero ve el progreso basado en las estaciones"

- Backend: "el sistema cambia el estado a EN_PREPARACION"
- Frontend: "el mesero ve que la orden está en estado Preparando"

---

## 🔗 Relación con Historias de Usuario del Backend

| Historia Backend | Historias Frontend Relacionadas | Relación |
|-----------------|--------------------------------|----------|
| HU-001 (Procesar pedido) | HU-FRONT-003, HU-FRONT-004, HU-FRONT-005 | El frontend construye y envía el pedido que el backend procesa |
| HU-002 (Consultar tareas) | HU-FRONT-001, HU-FRONT-006, HU-FRONT-007, HU-FRONT-008 | El frontend muestra tareas consultadas por estación y permite filtrado |
| HU-003 (Ejecutar tarea) | HU-FRONT-006, HU-FRONT-007, HU-FRONT-009 | El frontend refleja el progreso y permite iniciar tareas |

---

**Versión:** 1.0  
**Fecha:** Enero 2026  
**Autor:** FoodTech Frontend Team
