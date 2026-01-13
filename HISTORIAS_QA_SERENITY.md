# 🧪 Historias de Usuario QA - FoodTech Kitchen Service (Serenity BDD)

## 🎯 Principios INVEST

Todas las historias de usuario de QA cumplen con los principios INVEST:

- **I**ndependent (Independiente): Cada historia puede validarse por separado
- **N**egotiable (Negociable): Los detalles de verificación pueden refinarse con el equipo
- **V**aluable (Valiosa): Aporta valor al asegurar la calidad del sistema
- **E**stimable (Estimable): Se puede estimar el esfuerzo de automatización
- **S**mall (Pequeña): Se puede completar en una iteración
- **T**estable (Testeable): Es inherentemente testeable (diseñada para testing)

---

## 📝 Propósito de este Documento

Este documento contiene **historias de usuario desde la perspectiva de QA** para validar el correcto funcionamiento del sistema FoodTech mediante pruebas automatizadas con Serenity BDD.

A diferencia de las historias de desarrollo (backend/frontend), estas historias se enfocan en la **verificación y validación** del comportamiento del sistema desde el punto de vista del usuario final.

---

## HU-QA-001: Verificar flujo completo de creación de pedido

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el flujo completo de creación de pedido funciona correctamente  
**Para** asegurar que el mesero puede crear y enviar pedidos sin errores

### Contexto de Negocio

El flujo de creación de pedido es crítico para el negocio. Si falla cualquier paso (selección de mesa, agregado de productos, envío), el restaurante no puede operar. La verificación debe cubrir:
- Selección de mesa disponible
- Agregado de productos al pedido
- Visualización del resumen correcto
- Envío exitoso a cocina
- Confirmación de recepción

### Valor para el Negocio

- Garantiza que el flujo principal del negocio funciona
- Previene pérdidas de ventas por errores en pedidos
- Asegura comunicación correcta entre meseros y cocina
- Detecta regresiones en funcionalidad crítica

---

### Criterios de Aceptación

#### Escenario 1: Creación exitosa de pedido con un solo producto

```gherkin
Scenario: Mesero crea pedido con una bebida para una mesa disponible
  Given que el mesero accede al sistema de gestión de pedidos
  And la mesa "A1" está disponible
  When el mesero selecciona la mesa "A1"
  And el mesero agrega el producto "Gin Tonic Premium" al pedido
  Then el sistema debe mostrar el producto en el resumen de orden
  And el total de items debe ser 1
  When el mesero envía el pedido a cocina
  Then el sistema debe confirmar que el pedido fue recibido
  And la mesa "A1" debe cambiar a estado ocupada
```

#### Escenario 2: Creación de pedido con múltiples productos de diferentes categorías

```gherkin
Scenario: Mesero crea pedido mixto con bebida, plato caliente y postre
  Given que el mesero accede al sistema de gestión de pedidos
  And la mesa "B2" está disponible
  When el mesero selecciona la mesa "B2"
  And el mesero agrega el producto "Cerveza Artesanal" al pedido
  And el mesero agrega el producto "Filete con Papas" al pedido
  And el mesero agrega el producto "Tiramisú" al pedido
  Then el resumen debe mostrar 3 productos diferentes
  And el total de items debe ser 3
  When el mesero envía el pedido a cocina
  Then el sistema debe confirmar que se crearon 3 tareas
  And cada tarea debe estar asignada a la estación correcta
```

#### Escenario 3: Agregado de múltiples unidades del mismo producto

```gherkin
Scenario: Mesero agrega el mismo producto varias veces
  Given que el mesero ha seleccionado la mesa "C3"
  And el mesero ha agregado "Vino Tinto Reserva" al pedido
  When el mesero agrega nuevamente "Vino Tinto Reserva" al pedido
  Then el resumen debe mostrar "Vino Tinto Reserva" con cantidad 2
  And el producto no debe aparecer duplicado en la lista
  And el total de items debe ser 2
```

#### Escenario 4: Sistema impide enviar pedido sin productos

```gherkin
Scenario: Sistema valida que el pedido tenga al menos un producto
  Given que el mesero ha seleccionado la mesa "D4"
  And el pedido está vacío
  When el mesero intenta enviar el pedido a cocina
  Then el sistema debe indicar que el pedido necesita al menos un producto
  And el pedido no debe ser enviado a cocina
```

#### Escenario 5: Sistema impide enviar pedido sin mesa seleccionada

```gherkin
Scenario: Sistema valida que se haya seleccionado una mesa
  Given que el mesero accede al sistema de gestión de pedidos
  And el mesero ha agregado "Ensalada César" al pedido
  And no hay ninguna mesa seleccionada
  When el mesero intenta enviar el pedido a cocina
  Then el sistema debe indicar que debe seleccionar una mesa primero
  And el pedido no debe ser enviado a cocina
```

---

## HU-QA-002: Verificar modificación de pedidos antes de enviar

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el mesero puede modificar pedidos antes de enviarlos  
**Para** asegurar flexibilidad en la toma de pedidos y prevenir errores

### Contexto de Negocio

Los clientes frecuentemente cambian de opinión antes de confirmar su pedido. El sistema debe permitir:
- Eliminar productos agregados por error
- Reducir cantidades de productos
- Modificar el pedido sin perder otros productos

### Valor para el Negocio

- Evita desperdicio de productos no solicitados
- Mejora satisfacción del cliente
- Reduce costos operativos por cancelaciones
- Aumenta confianza del mesero en el sistema

---

### Criterios de Aceptación

#### Escenario 1: Eliminación de un producto del pedido

```gherkin
Scenario: Mesero elimina un producto que el cliente ya no desea
  Given que el mesero ha seleccionado la mesa "A5"
  And el pedido contiene "Ensalada César" y "Sopa del Día"
  And el total de items es 2
  When el mesero elimina "Ensalada César" del pedido
  Then el pedido debe contener únicamente "Sopa del Día"
  And el total de items debe ser 1
  And "Ensalada César" no debe aparecer en el resumen
```

#### Escenario 2: Reducción de cantidad de un producto

```gherkin
Scenario: Cliente solicita menos unidades de un producto
  Given que el mesero ha seleccionado la mesa "B6"
  And el pedido contiene "Agua Mineral" con cantidad 4
  When el mesero elimina una unidad de "Agua Mineral"
  Then "Agua Mineral" debe tener cantidad 3
  And el total de items debe ser 3
```

#### Escenario 3: Eliminación completa de producto con múltiples unidades

```gherkin
Scenario: Cliente cancela completamente un producto
  Given que el mesero ha seleccionado la mesa "C7"
  And el pedido contiene "Cerveza Artesanal" con cantidad 3
  When el mesero elimina todas las unidades de "Cerveza Artesanal"
  Then "Cerveza Artesanal" no debe aparecer en el pedido
  And el pedido debe estar vacío
  And el total de items debe ser 0
```

#### Escenario 4: Pedido permanece modificable hasta el envío

```gherkin
Scenario: Mesero modifica pedido múltiples veces antes de enviar
  Given que el mesero ha seleccionado la mesa "D8"
  When el mesero agrega "Pasta Carbonara"
  And el mesero agrega "Agua Mineral"
  And el mesero elimina "Pasta Carbonara"
  And el mesero agrega "Pizza Margarita"
  Then el pedido debe contener "Agua Mineral" y "Pizza Margarita"
  And el total de items debe ser 2
  And el mesero puede enviar el pedido sin problemas
```

---

## HU-QA-003: Verificar disponibilidad de mesas en tiempo real

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el sistema actualiza el estado de mesas correctamente  
**Para** asegurar que los meseros siempre ven información precisa de disponibilidad

### Contexto de Negocio

El estado de las mesas debe reflejar la realidad operativa. Una mesa ocupada no debe poder recibir nuevos pedidos. El sistema debe:
- Mostrar mesas disponibles para nuevos clientes
- Marcar mesas ocupadas cuando tienen pedidos activos
- Prevenir selección de mesas ocupadas
- Liberar mesas cuando se completan todos los pedidos

### Valor para el Negocio

- Previene errores de asignación de mesas
- Optimiza rotación de mesas
- Mejora coordinación entre meseros
- Evita conflictos operativos

---

### Criterios de Aceptación

#### Escenario 1: Mesa disponible puede ser seleccionada

```gherkin
Scenario: Mesero selecciona mesa disponible para nuevo cliente
  Given que el mesero accede al sistema de gestión de pedidos
  And la mesa "A1" está marcada como disponible
  When el mesero selecciona la mesa "A1"
  Then el sistema debe permitir agregar productos para esa mesa
  And la mesa "A1" debe mostrar indicación visual de seleccionada
```

#### Escenario 2: Mesa se marca ocupada al enviar pedido

```gherkin
Scenario: Mesa cambia a ocupada después de enviar un pedido
  Given que la mesa "B2" está disponible
  And el mesero ha creado un pedido para la mesa "B2"
  When el mesero envía el pedido a cocina
  Then la mesa "B2" debe cambiar a estado ocupada
  And la mesa "B2" debe tener indicación visual diferente
```

#### Escenario 3: Mesa ocupada no puede ser seleccionada

```gherkin
Scenario: Sistema impide seleccionar mesas con pedidos activos
  Given que la mesa "C3" tiene un pedido en preparación en cocina
  And la mesa "C3" está marcada como ocupada
  When el mesero intenta seleccionar la mesa "C3"
  Then el sistema no debe permitir la selección
  And la mesa "C3" debe mantener su estado ocupada
```

#### Escenario 4: Mesa se libera cuando se completa el pedido

```gherkin
Scenario: Mesa vuelve a disponible al completarse todas las tareas
  Given que la mesa "D4" tiene un pedido en cocina
  And todas las estaciones han completado sus tareas para ese pedido
  When el sistema actualiza el estado de las mesas
  Then la mesa "D4" debe cambiar a estado disponible
  And el mesero debe poder seleccionar la mesa "D4" nuevamente
```

#### Escenario 5: Múltiples mesas con diferentes estados

```gherkin
Scenario: Sistema mantiene estado correcto de múltiples mesas simultáneamente
  Given que existen 8 mesas en el sistema
  And las mesas "A1", "A2", "B1" están disponibles
  And las mesas "B2", "C1", "C2" están ocupadas
  When el mesero consulta el estado de todas las mesas
  Then el sistema debe mostrar correctamente 3 mesas disponibles
  And el sistema debe mostrar correctamente 3 mesas ocupadas
  And cada mesa debe tener la indicación visual correspondiente
```

---

## HU-QA-004: Verificar visualización de estado de cocina para meseros

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el mesero puede monitorear el estado de sus pedidos en cocina  
**Para** asegurar que puede informar al cliente con precisión sobre el progreso

### Contexto de Negocio

Los meseros deben poder:
- Ver todas las órdenes activas en cocina
- Identificar rápidamente el estado de cada orden
- Conocer el progreso de preparación
- Detectar cuándo una orden está lista para servir

### Valor para el Negocio

- Mejora comunicación con el cliente
- Reduce interrupciones al personal de cocina
- Permite servicio proactivo
- Aumenta satisfacción del cliente

---

### Criterios de Aceptación

#### Escenario 1: Visualización de orden recién enviada

```gherkin
Scenario: Mesero ve su orden en el estado de cocina después de enviarla
  Given que el mesero envió un pedido para la mesa "A1"
  And el pedido generó la orden número 45
  When el mesero consulta el estado de cocina
  Then la orden 45 debe aparecer en la lista de órdenes activas
  And la orden debe mostrar el número de mesa "A1"
  And la orden debe mostrar los productos enviados
```

#### Escenario 2: Identificación de estado de orden en cola

```gherkin
Scenario: Orden aparece en cola cuando aún no inicia preparación
  Given que existe una orden número 46 para la mesa "B2"
  And ninguna estación ha iniciado la preparación
  When el mesero consulta el estado de cocina
  Then la orden 46 debe aparecer con estado "En Cola"
  And el sistema debe indicar que está siguiente para preparación
```

#### Escenario 3: Identificación de orden en preparación

```gherkin
Scenario: Orden muestra estado en preparación cuando cocina está trabajando
  Given que existe una orden número 47 para la mesa "C3"
  And al menos una estación está preparando tareas de esa orden
  When el mesero consulta el estado de cocina
  Then la orden 47 debe aparecer con estado "Preparando"
  And el sistema debe mostrar un indicador de actividad
```

#### Escenario 4: Visualización de progreso por estaciones

```gherkin
Scenario: Mesero ve el progreso de preparación basado en estaciones
  Given que existe una orden con tareas en barra, cocina caliente y cocina fría
  And la estación de barra ha completado su tarea
  And las otras dos estaciones están en preparación
  When el mesero consulta el progreso de esa orden
  Then el sistema debe mostrar que 1 de 3 estaciones completó
  And el progreso debe indicar aproximadamente 33%
  And el mesero debe ver cuántas tareas faltan por completar
```

#### Escenario 5: Notificación de orden completamente lista

```gherkin
Scenario: Orden aparece como lista cuando todas las estaciones completaron
  Given que existe una orden número 48 para la mesa "D4"
  And todas las estaciones completaron sus tareas
  When el mesero consulta el estado de cocina
  Then la orden 48 debe aparecer con estado "Lista"
  And el progreso debe mostrar 100%
  And el sistema debe indicar que puede recoger y servir la orden
```

#### Escenario 6: Lista de productos agrupados por orden

```gherkin
Scenario: Mesero ve todos los productos de una orden agrupados
  Given que existe una orden con tareas en múltiples estaciones
  And la orden incluye 2 bebidas, 1 plato caliente y 1 postre
  When el mesero consulta los detalles de esa orden
  Then el sistema debe mostrar los 4 productos juntos
  And los productos deben estar agrupados independientemente de la estación
```

---

## HU-QA-005: Verificar consulta de tareas por estación de cocina

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que cada estación de cocina ve únicamente sus tareas asignadas  
**Para** asegurar claridad operativa y evitar confusiones entre estaciones

### Contexto de Negocio

Cada estación (barra, cocina caliente, cocina fría) debe:
- Ver solo las tareas de su estación
- Identificar la mesa asociada a cada tarea
- Conocer los productos a preparar
- No ver tareas de otras estaciones

### Valor para el Negocio

- Elimina confusión operativa
- Mejora eficiencia de cada estación
- Reduce errores de preparación
- Optimiza flujo de trabajo

---

### Criterios de Aceptación

#### Escenario 1: Estación de barra ve solo tareas de bebidas

```gherkin
Scenario: Personal de barra consulta sus tareas pendientes
  Given que existen 5 tareas pendientes en el sistema
  And 2 tareas están asignadas a la estación de barra
  And 2 tareas están asignadas a cocina caliente
  And 1 tarea está asignada a cocina fría
  When el personal de barra accede a su estación
  Then el sistema debe mostrar únicamente las 2 tareas de barra
  And no deben mostrarse tareas de cocina caliente ni cocina fría
```

#### Escenario 2: Estación sin tareas pendientes

```gherkin
Scenario: Estación consulta cuando no tiene tareas asignadas
  Given que existen tareas en el sistema
  And todas las tareas están asignadas a cocina caliente
  And no hay tareas para la estación de barra
  When el personal de barra accede a su estación
  Then el sistema debe indicar que no hay tareas pendientes
  And debe confirmarse que la consulta fue exitosa
```

#### Escenario 3: Información completa de cada tarea

```gherkin
Scenario: Cada tarea muestra información necesaria para preparación
  Given que existe 1 tarea pendiente en la estación de barra
  And la tarea corresponde a la mesa "A1"
  And la tarea contiene "Gin Tonic Premium" y "Cerveza Artesanal"
  When el personal de barra consulta la tarea
  Then el sistema debe mostrar el número de mesa "A1"
  And el sistema debe mostrar el número de orden asociado
  And el sistema debe listar los 2 productos a preparar
  And el sistema debe mostrar cuándo se creó la tarea
```

#### Escenario 4: Múltiples tareas visibles simultáneamente

```gherkin
Scenario: Estación con varias tareas pendientes al mismo tiempo
  Given que la estación de cocina caliente tiene 4 tareas pendientes
  And cada tarea es de una mesa diferente
  When el personal de cocina caliente accede a su estación
  Then el sistema debe mostrar las 4 tareas en la lista
  And cada tarea debe mostrar su mesa asociada
  And el personal puede identificar cuál preparar primero
```

#### Escenario 5: Segregación entre estaciones

```gherkin
Scenario: Verificar que tareas no se mezclan entre estaciones
  Given que existe un pedido mixto que generó 3 tareas
  And 1 tarea está en barra con "Mojito"
  And 1 tarea está en cocina caliente con "Pasta"
  And 1 tarea está en cocina fría con "Ensalada"
  When el personal de barra consulta sus tareas
  Then solo debe ver la tarea con "Mojito"
  When el personal de cocina caliente consulta sus tareas
  Then solo debe ver la tarea con "Pasta"
  When el personal de cocina fría consulta sus tareas
  Then solo debe ver la tarea con "Ensalada"
```

---

## HU-QA-006: Verificar ejecución de tareas en estaciones

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el personal de cocina puede iniciar y completar tareas  
**Para** asegurar que el flujo de preparación funciona correctamente

### Contexto de Negocio

El personal de cocina debe poder:
- Iniciar preparación de tareas pendientes
- Ver cambio de estado al iniciar
- Completar tareas automáticamente
- Reflejar progreso en tiempo real

### Valor para el Negocio

- Asegura trazabilidad de preparaciones
- Permite monitoreo de progreso
- Facilita coordinación entre estaciones
- Genera métricas de desempeño

---

### Criterios de Aceptación

#### Escenario 1: Inicio exitoso de preparación de tarea

```gherkin
Scenario: Cocinero inicia preparación de tarea pendiente
  Given que existe una tarea pendiente en la estación de barra
  And la tarea está en estado "Pendiente"
  When el cocinero indica que inicia la preparación
  Then el estado de la tarea debe cambiar a "En Preparación"
  And el sistema debe registrar el momento de inicio
```

#### Escenario 2: Tarea se completa automáticamente

```gherkin
Scenario: Sistema completa tarea automáticamente después de tiempo estimado
  Given que existe una tarea en estado "En Preparación"
  And el cocinero está ejecutando la preparación física
  When transcurre el tiempo estimado de preparación
  Then el estado de la tarea debe cambiar a "Completada" automáticamente
  And el sistema debe registrar el momento de finalización
```

#### Escenario 3: Validación de estado antes de iniciar

```gherkin
Scenario: Sistema valida que tarea no esté ya iniciada
  Given que existe una tarea en estado "En Preparación"
  When el cocinero intenta iniciar nuevamente la misma tarea
  Then el sistema debe rechazar la operación
  And debe informar que la tarea ya está en preparación
  And el estado debe permanecer en "En Preparación"
```

#### Escenario 4: Orden se marca como completada cuando todas sus tareas finalizan

```gherkin
Scenario: Estado de orden refleja completitud de todas sus tareas
  Given que una orden generó 3 tareas en diferentes estaciones
  And 2 tareas ya están completadas
  And 1 tarea está en preparación
  When la última tarea se completa automáticamente
  Then el estado de la orden debe cambiar a "Completado"
  And el mesero debe poder ver que la orden está lista
```

#### Escenario 5: Progreso de orden se actualiza con cada tarea completada

```gherkin
Scenario: Progreso de orden aumenta al completarse tareas
  Given que una orden tiene tareas en barra, cocina caliente y cocina fría
  And inicialmente ninguna tarea está completada
  When la estación de barra completa su tarea
  Then el progreso de la orden debe aumentar proporcionalmente
  When la estación de cocina caliente completa su tarea
  Then el progreso de la orden debe aumentar nuevamente
  When la estación de cocina fría completa su tarea
  Then el progreso de la orden debe llegar a 100%
```

---

## HU-QA-007: Verificar filtrado de tareas por estado en estaciones

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el personal de cocina puede filtrar tareas por estado  
**Para** asegurar que pueden enfocarse en las tareas que requieren atención

### Contexto de Negocio

Durante el servicio, las estaciones acumulan tareas en diferentes estados. El personal debe poder:
- Ver todas las tareas sin filtro
- Filtrar solo tareas pendientes
- Filtrar solo tareas en preparación
- Filtrar solo tareas completadas

### Valor para el Negocio

- Mejora enfoque en tareas prioritarias
- Reduce saturación visual
- Aumenta productividad
- Facilita gestión de flujo de trabajo

---

### Criterios de Aceptación

#### Escenario 1: Filtro muestra todas las tareas

```gherkin
Scenario: Personal revisa todas las tareas sin aplicar filtro
  Given que la estación de barra tiene 2 tareas pendientes
  And la estación de barra tiene 1 tarea en preparación
  And la estación de barra tiene 2 tareas completadas
  When el personal selecciona el filtro "Todas"
  Then el sistema debe mostrar las 5 tareas
  And deben mostrarse tareas en todos los estados
```

#### Escenario 2: Filtro muestra solo tareas pendientes

```gherkin
Scenario: Personal filtra para ver solo lo que falta por iniciar
  Given que la estación tiene tareas en diferentes estados
  And hay 3 tareas en estado "Pendiente"
  When el personal selecciona el filtro "Pendiente"
  Then el sistema debe mostrar únicamente las 3 tareas pendientes
  And no deben mostrarse tareas en preparación ni completadas
```

#### Escenario 3: Filtro muestra solo tareas en preparación

```gherkin
Scenario: Personal filtra para ver qué está actualmente en proceso
  Given que la estación tiene tareas en diferentes estados
  And hay 2 tareas en estado "En Preparación"
  When el personal selecciona el filtro "En Preparación"
  Then el sistema debe mostrar únicamente las 2 tareas en preparación
  And el personal puede identificar qué está ocupando al equipo
```

#### Escenario 4: Filtro muestra solo tareas completadas

```gherkin
Scenario: Personal filtra para verificar qué se ha completado
  Given que la estación tiene tareas en diferentes estados
  And hay 4 tareas en estado "Completada"
  When el personal selecciona el filtro "Completada"
  Then el sistema debe mostrar únicamente las 4 tareas completadas
  And el personal puede confirmar qué preparaciones están listas
```

#### Escenario 5: Cambio dinámico entre filtros

```gherkin
Scenario: Personal cambia de filtro según necesidad operativa
  Given que el personal está visualizando tareas pendientes
  When el personal cambia al filtro de tareas completadas
  Then la vista debe actualizarse mostrando solo completadas
  And el cambio debe ser inmediato
  And no debe perderse el contexto de la estación
```

#### Escenario 6: Filtro sin resultados

```gherkin
Scenario: Sistema maneja correctamente filtros sin tareas
  Given que la estación no tiene tareas pendientes
  And la estación tiene tareas en otros estados
  When el personal selecciona el filtro "Pendiente"
  Then el sistema debe indicar claramente que no hay tareas pendientes
  And debe confirmarse que el filtro fue aplicado correctamente
```

---

## HU-QA-008: Verificar navegación por categorías de menú

### Descripción

**Como** QA del proyecto FoodTech  
**Quiero** verificar que el mesero puede filtrar productos por categoría  
**Para** asegurar que puede encontrar rápidamente lo que el cliente solicita

### Contexto de Negocio

El menú del restaurante tiene múltiples productos organizados por categorías. El mesero debe poder:
- Ver todos los productos sin filtro
- Filtrar por bebidas
- Filtrar por platos calientes
- Filtrar por platos fríos
- Cambiar entre categorías fluidamente

### Valor para el Negocio

- Reduce tiempo de toma de pedidos
- Mejora experiencia del mesero
- Disminuye errores de búsqueda
- Aumenta eficiencia operativa

---

### Criterios de Aceptación

#### Escenario 1: Visualización de todo el menú sin filtro

```gherkin
Scenario: Mesero visualiza todos los productos disponibles
  Given que el mesero ha seleccionado una mesa
  And el menú contiene productos de todas las categorías
  When el mesero selecciona la opción "Todo el Menú"
  Then el sistema debe mostrar todos los productos disponibles
  And deben mostrarse bebidas, platos calientes, platos fríos y postres
```

#### Escenario 2: Filtrado por categoría de bebidas

```gherkin
Scenario: Mesero filtra solo productos de bebidas
  Given que el mesero está construyendo un pedido
  And el cliente solicita solo bebidas
  When el mesero selecciona la categoría "Bebidas"
  Then el sistema debe mostrar únicamente productos de bebidas
  And no deben mostrarse productos de otras categorías
```

#### Escenario 3: Cambio entre categorías sin perder productos agregados

```gherkin
Scenario: Mesero navega entre categorías mientras construye pedido
  Given que el mesero ha agregado "Cerveza Artesanal" al pedido
  And el mesero está visualizando la categoría de bebidas
  When el mesero cambia a la categoría de platos calientes
  Then el sistema debe mostrar productos de platos calientes
  And "Cerveza Artesanal" debe permanecer en el pedido
```

#### Escenario 4: Vuelta a vista completa desde categoría específica

```gherkin
Scenario: Mesero regresa a ver todo el menú después de filtrar
  Given que el mesero está visualizando solo la categoría de postres
  When el mesero selecciona "Todo el Menú"
  Then el sistema debe mostrar productos de todas las categorías nuevamente
  And los productos agregados previamente deben permanecer en el pedido
```

#### Escenario 5: Indicación visual de productos agregados persiste entre categorías

```gherkin
Scenario: Productos agregados mantienen indicación visual al cambiar categoría
  Given que el mesero ha agregado "Ensalada César" al pedido
  And el mesero cambia de categoría de platos fríos a todo el menú
  When el mesero visualiza el catálogo completo
  Then "Ensalada César" debe tener indicación visual de agregado
  And el mesero puede distinguir productos agregados de los no agregados
```

---

## 📊 Matriz de Trazabilidad QA

| Historia QA | Componente | Prioridad | Complejidad | HU Relacionadas |
|-------------|------------|-----------|-------------|-----------------|
| HU-QA-001   | Mesero     | Alta      | Media       | HU-FRONT-001, HU-FRONT-003, HU-FRONT-005 |
| HU-QA-002   | Mesero     | Alta      | Baja        | HU-FRONT-004 |
| HU-QA-003   | Mesero     | Alta      | Media       | HU-FRONT-001 |
| HU-QA-004   | Mesero     | Alta      | Alta        | HU-FRONT-006 |
| HU-QA-005   | Cocina     | Alta      | Media       | HU-FRONT-007, HU-002 |
| HU-QA-006   | Cocina     | Alta      | Alta        | HU-FRONT-007, HU-003 |
| HU-QA-007   | Cocina     | Media     | Baja        | HU-FRONT-008 |
| HU-QA-008   | Mesero     | Media     | Baja        | HU-FRONT-002 |

---

## 🎯 Orden de Automatización Sugerido

### Sprint 1: Flujos Críticos
1. **HU-QA-001** - Flujo completo de creación de pedido
2. **HU-QA-003** - Disponibilidad de mesas

### Sprint 2: Validaciones y Modificaciones
3. **HU-QA-002** - Modificación de pedidos
4. **HU-QA-004** - Estado de cocina para meseros

### Sprint 3: Operaciones de Cocina
5. **HU-QA-005** - Consulta de tareas por estación
6. **HU-QA-006** - Ejecución de tareas

### Sprint 4: Filtros y Navegación
7. **HU-QA-007** - Filtrado de tareas
8. **HU-QA-008** - Navegación por categorías

---

## 📝 Notas Importantes para QA

### Lenguaje de Negocio

Todos los escenarios están escritos en **lenguaje de negocio**, no técnico:
- ✅ "el mesero selecciona la mesa" (no "hace click en el elemento")
- ✅ "el sistema debe mostrar" (no "el DOM debe contener")
- ✅ "la orden debe aparecer con estado" (no "verificar atributo data-testid")

### Implementación con Serenity

Estos escenarios deben implementarse usando:
- **Screenplay Pattern** para interacciones de usuario
- **Page Objects** para encapsular selectores técnicos
- **Tareas** para acciones de alto nivel
- **Preguntas** para verificaciones

### Independencia de Implementación

Los escenarios son **independientes de la tecnología**:
- ✅ Válidos si cambias de React a Vue
- ✅ Válidos si modificas los componentes
- ✅ Válidos si cambias el backend
- ✅ Enfocados en el comportamiento observable

### Datos de Prueba

Cada escenario puede requerir:
- Mesas con estados específicos
- Productos en el catálogo
- Pedidos/órdenes pre-existentes
- Tareas en diferentes estados

Se recomienda implementar **builders de datos** o usar **datos sintéticos** para cada prueba.

---

## 🔗 Referencias

- [HISTORIAS_DE_USUARIO.md](./HISTORIAS_DE_USUARIO.md) - Historias de backend
- [HISTORIAS_DE_USUARIO_FRONT.md](./HISTORIAS_DE_USUARIO_FRONT.md) - Historias de frontend
- [TESTING-SERENITY-GUIDE.md](./TESTING-SERENITY-GUIDE.md) - Guía técnica de implementación

---

**Versión:** 1.0  
**Fecha:** Enero 2026  
**Autor:** FoodTech QA Team  
**Framework:** Serenity BDD + Cucumber
