# 🧪 Guía de Testing con Serenity BDD - FoodTech Kitchen Service

## 📋 Tabla de Contenidos
- [Introducción](#introducción)
- [Selectores por Historia de Usuario](#selectores-por-historia-de-usuario)
- [Mapa Completo de Selectores](#mapa-completo-de-selectores)
- [Ejemplos de Page Objects](#ejemplos-de-page-objects)
- [Ejemplos de Steps](#ejemplos-de-steps)
- [Estructura del Proyecto Sugerida](#estructura-del-proyecto-sugerida)

---

## 🎯 Introducción

Este documento proporciona todos los selectores (data-testid, ids y atributos) necesarios para implementar pruebas automatizadas con Serenity BDD sobre las Historias de Usuario del proyecto FoodTech.

### Tecnologías Recomendadas
- **Serenity BDD** con Screenplay Pattern
- **Selenium WebDriver**
- **Java 11+**
- **Cucumber** para BDD (Gherkin)
- **Maven** o **Gradle**

---

## 🎭 Selectores por Historia de Usuario

### HU-001: Procesar pedido de cocina

#### Flujo: Seleccionar mesa y crear pedido

| Elemento | Selector | XPath Sugerido | Descripción |
|----------|----------|----------------|-------------|
| **Tarjeta de Mesa** | `[data-testid="table-card-A1"]` | `//div[@data-table-number='A1']` | Tarjeta completa de mesa |
| Número de mesa | `[data-testid="table-number-A1"]` | `//span[@data-testid='table-number-A1']` | Muestra el número de la mesa |
| Estado de mesa | `[data-testid="table-status-A1"]` | `//span[@data-testid='table-status-A1']` | Muestra "Disponible" u "Ocupada" |
| **Tarjeta de Producto** | `[data-testid="product-card-gin-tonic-premium"]` | `//div[@data-product-name='Gin Tonic Premium']` | Tarjeta completa del producto |
| Nombre del producto | `[data-testid="product-name"]` | `//h3[@data-testid='product-name']` | Nombre del producto |
| Badge "Agregado" | `[data-testid="product-added-badge"]` | `//span[@data-testid='product-added-badge']` | Indicador de producto agregado |
| Botón agregar producto | `[data-testid="add-product-btn-gin-tonic-premium"]` | `//button[@data-testid='add-product-btn-gin-tonic-premium']` | Botón para agregar producto |

#### Flujo: Resumen de orden

| Elemento | Selector | XPath Sugerido | Descripción |
|----------|----------|----------------|-------------|
| **Panel resumen** | `[data-testid="order-summary"]` | `//div[@data-testid='order-summary']` | Panel completo del resumen |
| Badge "Orden Activa" | `[data-testid="order-active-badge"]` | `//span[@data-testid='order-active-badge']` | Badge que indica orden activa |
| **Lista de productos** | `[data-testid="order-products-list"]` | `//div[@data-testid='order-products-list']` | Contenedor de productos |
| Item de orden | `[data-testid="order-item-gin-tonic-premium"]` | `//div[@data-testid='order-item-gin-tonic-premium']` | Item individual en la orden |
| Nombre en orden | `[data-testid="order-item-name"]` | `.//span[@data-testid='order-item-name']` | Nombre del producto en orden |
| Cantidad en orden | `[data-testid="order-item-quantity"]` | `.//span[@data-testid='order-item-quantity']` | Cantidad (ej: "x2") |
| Botón eliminar producto | `[data-testid="remove-product-btn-gin-tonic-premium"]` | `//button[@data-testid='remove-product-btn-gin-tonic-premium']` | Botón para eliminar |
| **Total de items** | `[data-testid="order-total"]` | `//div[@data-testid='order-total']` | Contenedor del total |
| Contador total | `[data-testid="total-items-count"]` | `//span[@data-testid='total-items-count']` | Número total de items |
| **Botón enviar** | `[data-testid="submit-order-btn"]` | `//button[@data-testid='submit-order-btn']` | Botón "Enviar a Cocina" |

#### Validaciones Importantes

```gherkin
# Verificar que mesa está disponible
Then la mesa "A1" debe tener estado "Disponible"
# XPath: //div[@data-testid='table-card-A1']//span[@data-testid='table-status-A1'][text()='Disponible']

# Verificar que producto fue agregado
And el producto "Gin Tonic Premium" debe mostrar badge "Agregado"
# XPath: //div[@data-testid='product-card-gin-tonic-premium'][@data-is-in-order='true']//span[@data-testid='product-added-badge']

# Verificar total de items
And el total de items debe ser "3"
# XPath: //span[@data-testid='total-items-count'][text()='3']
```

---

### HU-002: Consultar tareas por estación

#### Flujo: Visualizar tareas en estación de cocina

| Elemento | Selector | XPath Sugerido | Descripción |
|----------|----------|----------------|-------------|
| **Tarjeta de Tarea** | `[data-testid="task-card-123"]` | `//div[@data-task-id='123']` | Tarjeta completa de tarea |
| ID de tarea | `[data-task-id="123"]` | `//div[@data-task-id='123']` | Atributo con ID de tarea |
| ID de orden | `[data-order-id="45"]` | `//div[@data-order-id='45']` | Atributo con ID de orden |
| Número de mesa | `[data-testid="task-table-number"]` | `.//h3[@data-testid='task-table-number']` | Muestra "Mesa A1" |
| Estado de tarea | `[data-testid="task-status-badge"]` | `.//span[@data-testid='task-status-badge']` | Badge del estado |
| ID de orden texto | `[data-testid="task-order-id"]` | `.//p[@data-testid='task-order-id']` | Texto "Pedido #45" |
| **Productos de tarea** | `[data-testid="task-products"]` | `.//div[@data-testid='task-products']` | Contenedor de productos |
| Producto individual | `[data-testid="task-product-0"]` | `.//div[@data-testid='task-product-0']` | Producto en posición 0 |
| **Botón iniciar** | `[data-testid="start-task-btn-123"]` | `//button[@data-testid='start-task-btn-123']` | Botón "Iniciar Preparación" |

#### Filtros por Estación

```gherkin
# Las tareas se filtran por URL
Given el cocinero accede a "/bar"          # Estación BAR
Given el cocinero accede a "/hot-kitchen"  # Estación HOT_KITCHEN
Given el cocinero accede a "/cold-kitchen" # Estación COLD_KITCHEN
```

#### Validaciones por Estado

```gherkin
# Verificar estado de tarea
Then la tarea "123" debe tener estado "Pendiente"
# XPath: //div[@data-task-id='123']//span[@data-testid='task-status-badge'][text()='Pendiente']

# Verificar que tarea pertenece a estación correcta
And la tarea "123" debe estar asignada a "BAR"
# XPath: //div[@data-task-id='123'][@data-station='BAR']

# Verificar productos en tarea
And la tarea "123" debe contener el producto "Gin Tonic Premium"
# XPath: //div[@data-task-id='123']//div[@data-testid='task-products']//div[@data-product-name='Gin Tonic Premium']
```

---

### HU-003: Ejecutar tarea de preparación

#### Flujo: Iniciar y completar preparación

| Elemento | Selector | XPath Sugerido | Descripción |
|----------|----------|----------------|-------------|
| Tarea pendiente | `[data-task-status="PENDING"]` | `//div[@data-task-status='PENDING']` | Filtra tareas pendientes |
| Tarea en preparación | `[data-task-status="IN_PREPARATION"]` | `//div[@data-task-status='IN_PREPARATION']` | Filtra en preparación |
| Tarea completada | `[data-task-status="COMPLETED"]` | `//div[@data-task-status='COMPLETED']` | Filtra completadas |
| Botón iniciar | `[data-testid="start-task-btn-123"]` | `//button[@data-testid='start-task-btn-123']` | Inicia preparación |

#### Estado de Orden en Vista Mesero

| Elemento | Selector | XPath Sugerido | Descripción |
|----------|----------|----------------|-------------|
| **Panel estado cocina** | `[data-testid="kitchen-status"]` | `//div[@data-testid='kitchen-status']` | Panel completo |
| Botón refrescar | `[data-testid="refresh-kitchen-btn"]` | `//button[@data-testid='refresh-kitchen-btn']` | Botón sync |
| **Lista de órdenes** | `[data-testid="kitchen-orders-list"]` | `//div[@data-testid='kitchen-orders-list']` | Lista de órdenes |
| Estado vacío | `[data-testid="kitchen-empty-state"]` | `//div[@data-testid='kitchen-empty-state']` | Mensaje sin tareas |
| **Orden en cocina** | `[data-testid="kitchen-order-45"]` | `//div[@data-order-id='45']` | Orden específica |
| Header de orden | `[data-testid="kitchen-order-header"]` | `.//span[@data-testid='kitchen-order-header']` | "#45 • A1" |
| Estado de orden | `[data-testid="kitchen-order-status"]` | `.//span[@data-testid='kitchen-order-status']` | "Preparando", "Lista" |
| Productos de orden | `[data-testid="kitchen-order-products"]` | `.//div[@data-testid='kitchen-order-products']` | Chips de productos |
| Producto individual | `[data-testid="kitchen-product-0"]` | `.//span[@data-testid='kitchen-product-0']` | Producto en posición 0 |
| **Barra de progreso** | `[data-testid="kitchen-progress-bar"]` | `.//div[@data-testid='kitchen-progress-bar']` | Contenedor barra |
| Progreso fill | `[data-testid="kitchen-progress-fill"]` | `.//div[@data-testid='kitchen-progress-fill']` | Barra de color |
| Texto progreso | `[data-testid="kitchen-progress-text"]` | `.//p[@data-testid='kitchen-progress-text']` | "2 de 3 tareas..." |
| Mensaje completada | `[data-testid="kitchen-order-completed-msg"]` | `.//p[@data-testid='kitchen-order-completed-msg']` | "Recoger en..." |
| Mensaje pendiente | `[data-testid="kitchen-order-pending-msg"]` | `.//p[@data-testid='kitchen-order-pending-msg']` | "Siguiente para..." |

#### Validación de Progreso

```gherkin
# Verificar progreso de orden
Then la orden "45" debe tener progreso "67%"
# XPath: //div[@data-order-id='45']//div[@data-testid='kitchen-progress-fill'][@data-progress='67']

# Verificar estado agregado de orden
And la orden "45" debe estar en estado "IN_PREPARATION"
# XPath: //div[@data-testid='kitchen-order-45'][@data-order-status='IN_PREPARATION']
```

---

## 🗺️ Mapa Completo de Selectores

### Convención de Nomenclatura

```
[componente]-[elemento]-[identificador]

Ejemplos:
- table-card-A1          (tarjeta de mesa A1)
- product-card-gin-tonic (tarjeta de producto)
- task-card-123          (tarjeta de tarea ID 123)
- order-item-gin-tonic   (item en resumen de orden)
- kitchen-order-45       (orden en estado de cocina)
```

### Atributos de Datos Adicionales

Además de `data-testid`, los elementos tienen atributos semánticos:

```html
<!-- Mesa -->
<div data-testid="table-card-A1" 
     data-table-id="1" 
     data-table-number="A1" 
     data-table-status="DISPONIBLE">

<!-- Producto en orden -->
<div data-testid="order-item-gin-tonic-premium" 
     data-product-name="Gin Tonic Premium">
  <span data-testid="order-item-name">Gin Tonic Premium</span>
  <span data-testid="order-item-quantity">x2</span>
  <button data-testid="remove-product-btn-gin-tonic-premium">...</button>
</div>

<!-- Tarea -->
<div data-testid="task-card-123" 
     data-task-id="123" 
     data-order-id="45" 
     data-table-number="A1" 
     data-task-status="PENDING" 
     data-station="BAR">

<!-- Orden en cocina -->
<div data-testid="kitchen-order-45" 
     data-order-id="45" 
     data-table-number="A1" 
     data-order-status="IN_PREPARATION">
```

Estos atributos permiten XPath más robustos y expresivos.

---

## 💻 Ejemplos de Page Objects

### WaiterPage.java

```java
package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class WaiterPage extends PageObject {
    
    // Selección de mesa
    public WebElementFacade getTableCard(String tableNumber) {
        return find("[data-testid='table-card-" + tableNumber + "']");
    }
    
    public WebElementFacade getTableStatus(String tableNumber) {
        return find("[data-testid='table-status-" + tableNumber + "']");
    }
    
    // Productos
    public WebElementFacade getProductCard(String productName) {
        String selector = productName.toLowerCase().replaceAll("\\s+", "-");
        return find("[data-testid='product-card-" + selector + "']");
    }
    
    public WebElementFacade getAddProductButton(String productName) {
        String selector = productName.toLowerCase().replaceAll("\\s+", "-");
        return find("[data-testid='add-product-btn-" + selector + "']");
    }
    
    public boolean isProductInOrder(String productName) {
        String selector = productName.toLowerCase().replaceAll("\\s+", "-");
        return find("[data-testid='product-card-" + selector + "'][data-is-in-order='true']")
               .isCurrentlyVisible();
    }
    
    // Resumen de orden
    @FindBy(css = "[data-testid='order-summary']")
    private WebElementFacade orderSummary;
    
    @FindBy(css = "[data-testid='total-items-count']")
    private WebElementFacade totalItemsCount;
    
    @FindBy(css = "[data-testid='submit-order-btn']")
    private WebElementFacade submitOrderButton;
    
    public int getTotalItemsCount() {
        return Integer.parseInt(totalItemsCount.getText());
    }
    
    public WebElementFacade getOrderItem(String productName) {
        String selector = productName.toLowerCase().replaceAll("\\s+", "-");
        return find("[data-testid='order-item-" + selector + "']");
    }
    
    public String getOrderItemQuantity(String productName) {
        return getOrderItem(productName)
               .find("[data-testid='order-item-quantity']")
               .getText();
    }
    
    public void removeProductFromOrder(String productName) {
        String selector = productName.toLowerCase().replaceAll("\\s+", "-");
        find("[data-testid='remove-product-btn-" + selector + "']").click();
    }
    
    // Estado de cocina
    public WebElementFacade getKitchenOrder(String orderId) {
        return find("[data-testid='kitchen-order-" + orderId + "']");
    }
    
    public String getKitchenOrderStatus(String orderId) {
        return getKitchenOrder(orderId)
               .find("[data-testid='kitchen-order-status']")
               .getText();
    }
    
    public int getKitchenOrderProgress(String orderId) {
        WebElementFacade progressBar = getKitchenOrder(orderId)
                .find("[data-testid='kitchen-progress-fill']");
        return Integer.parseInt(progressBar.getAttribute("data-progress"));
    }
}
```

### KitchenStationPage.java

```java
package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.List;

public class KitchenStationPage extends PageObject {
    
    public WebElementFacade getTaskCard(String taskId) {
        return find("[data-testid='task-card-" + taskId + "']");
    }
    
    public List<WebElementFacade> getPendingTasks() {
        return findAll("[data-task-status='PENDING']");
    }
    
    public List<WebElementFacade> getTasksInPreparation() {
        return findAll("[data-task-status='IN_PREPARATION']");
    }
    
    public List<WebElementFacade> getCompletedTasks() {
        return findAll("[data-task-status='COMPLETED']");
    }
    
    public String getTaskStatus(String taskId) {
        return getTaskCard(taskId)
               .find("[data-testid='task-status-badge']")
               .getText();
    }
    
    public String getTaskTableNumber(String taskId) {
        return getTaskCard(taskId).getAttribute("data-table-number");
    }
    
    public String getTaskOrderId(String taskId) {
        return getTaskCard(taskId).getAttribute("data-order-id");
    }
    
    public List<WebElementFacade> getTaskProducts(String taskId) {
        return getTaskCard(taskId).thenFindAll("[data-testid^='task-product-']");
    }
    
    public void startTaskPreparation(String taskId) {
        find("[data-testid='start-task-btn-" + taskId + "']").click();
    }
    
    public boolean canStartTask(String taskId) {
        return find("[data-testid='start-task-btn-" + taskId + "']")
               .isCurrentlyVisible();
    }
    
    // Verificación por estación
    public List<WebElementFacade> getTasksByStation(String station) {
        return findAll("[data-station='" + station + "']");
    }
}
```

---

## 🎬 Ejemplos de Steps

### WaiterSteps.java

```java
package steps;

import net.thucydides.core.annotations.Step;
import pages.WaiterPage;

public class WaiterSteps {
    
    WaiterPage waiterPage;
    
    @Step("Seleccionar la mesa {0}")
    public void selectTable(String tableNumber) {
        waiterPage.getTableCard(tableNumber).click();
    }
    
    @Step("Verificar que la mesa {0} está disponible")
    public void verifyTableIsAvailable(String tableNumber) {
        String status = waiterPage.getTableStatus(tableNumber).getText();
        assert status.equals("Disponible") : 
            "Mesa " + tableNumber + " no está disponible. Estado actual: " + status;
    }
    
    @Step("Verificar que la mesa {0} está ocupada")
    public void verifyTableIsOccupied(String tableNumber) {
        String status = waiterPage.getTableStatus(tableNumber).getText();
        assert status.equals("Ocupada") : 
            "Mesa " + tableNumber + " no está ocupada. Estado actual: " + status;
    }
    
    @Step("Agregar producto {0} a la orden")
    public void addProductToOrder(String productName) {
        waiterPage.getAddProductButton(productName).click();
    }
    
    @Step("Verificar que el producto {0} está en la orden")
    public void verifyProductIsInOrder(String productName) {
        assert waiterPage.isProductInOrder(productName) :
            "Producto " + productName + " no está en la orden";
    }
    
    @Step("Verificar que el total de items es {0}")
    public void verifyTotalItems(int expectedTotal) {
        int actualTotal = waiterPage.getTotalItemsCount();
        assert actualTotal == expectedTotal :
            "Total incorrecto. Esperado: " + expectedTotal + ", Actual: " + actualTotal;
    }
    
    @Step("Verificar cantidad del producto {0} es {1}")
    public void verifyProductQuantity(String productName, String expectedQuantity) {
        String actualQuantity = waiterPage.getOrderItemQuantity(productName);
        assert actualQuantity.equals(expectedQuantity) :
            "Cantidad incorrecta para " + productName + 
            ". Esperada: " + expectedQuantity + ", Actual: " + actualQuantity;
    }
    
    @Step("Eliminar producto {0} de la orden")
    public void removeProductFromOrder(String productName) {
        waiterPage.removeProductFromOrder(productName);
    }
    
    @Step("Enviar orden a cocina")
    public void submitOrder() {
        waiterPage.find("[data-testid='submit-order-btn']").click();
    }
    
    @Step("Verificar estado de orden {0} es {1}")
    public void verifyOrderStatus(String orderId, String expectedStatus) {
        String actualStatus = waiterPage.getKitchenOrderStatus(orderId);
        assert actualStatus.equalsIgnoreCase(expectedStatus) :
            "Estado incorrecto. Esperado: " + expectedStatus + ", Actual: " + actualStatus;
    }
    
    @Step("Verificar progreso de orden {0} es {1}%")
    public void verifyOrderProgress(String orderId, int expectedProgress) {
        int actualProgress = waiterPage.getKitchenOrderProgress(orderId);
        assert actualProgress == expectedProgress :
            "Progreso incorrecto. Esperado: " + expectedProgress + "%, Actual: " + actualProgress + "%";
    }
}
```

### KitchenSteps.java

```java
package steps;

import net.thucydides.core.annotations.Step;
import pages.KitchenStationPage;
import java.util.List;

public class KitchenSteps {
    
    KitchenStationPage kitchenPage;
    
    @Step("Navegar a estación {0}")
    public void navigateToStation(String station) {
        String url = "";
        switch(station) {
            case "BAR":
                url = "/bar";
                break;
            case "HOT_KITCHEN":
                url = "/hot-kitchen";
                break;
            case "COLD_KITCHEN":
                url = "/cold-kitchen";
                break;
        }
        kitchenPage.openUrl(kitchenPage.getDriver().getCurrentUrl() + url);
    }
    
    @Step("Verificar que existen {0} tareas pendientes")
    public void verifyPendingTasksCount(int expectedCount) {
        int actualCount = kitchenPage.getPendingTasks().size();
        assert actualCount == expectedCount :
            "Cantidad incorrecta de tareas. Esperadas: " + expectedCount + ", Actuales: " + actualCount;
    }
    
    @Step("Verificar que la tarea {0} está pendiente")
    public void verifyTaskIsPending(String taskId) {
        String status = kitchenPage.getTaskStatus(taskId);
        assert status.equalsIgnoreCase("Pendiente") :
            "Tarea " + taskId + " no está pendiente. Estado: " + status;
    }
    
    @Step("Verificar que la tarea {0} pertenece a la mesa {1}")
    public void verifyTaskBelongsToTable(String taskId, String tableNumber) {
        String actualTable = kitchenPage.getTaskTableNumber(taskId);
        assert actualTable.equals(tableNumber) :
            "Mesa incorrecta. Esperada: " + tableNumber + ", Actual: " + actualTable;
    }
    
    @Step("Verificar que la tarea {0} contiene el producto {1}")
    public void verifyTaskContainsProduct(String taskId, String productName) {
        boolean found = kitchenPage.getTaskProducts(taskId).stream()
            .anyMatch(product -> product.getAttribute("data-product-name").equals(productName));
        
        assert found : "Producto " + productName + " no encontrado en tarea " + taskId;
    }
    
    @Step("Iniciar preparación de tarea {0}")
    public void startTaskPreparation(String taskId) {
        kitchenPage.startTaskPreparation(taskId);
    }
    
    @Step("Verificar que la tarea {0} está en preparación")
    public void verifyTaskIsInPreparation(String taskId) {
        String status = kitchenPage.getTaskStatus(taskId);
        assert status.equalsIgnoreCase("En Preparación") :
            "Tarea " + taskId + " no está en preparación. Estado: " + status;
    }
    
    @Step("Verificar que la tarea {0} está completada")
    public void verifyTaskIsCompleted(String taskId) {
        String status = kitchenPage.getTaskStatus(taskId);
        assert status.equalsIgnoreCase("Completada") :
            "Tarea " + taskId + " no está completada. Estado: " + status;
    }
    
    @Step("Verificar que todas las tareas son de la estación {0}")
    public void verifyAllTasksBelongToStation(String station) {
        List<net.serenitybdd.core.pages.WebElementFacade> tasks = 
            kitchenPage.getTasksByStation(station);
        
        long totalTasks = kitchenPage.findAll("[data-testid^='task-card-']").size();
        
        assert tasks.size() == totalTasks :
            "Algunas tareas no pertenecen a " + station + 
            ". Esperadas: " + totalTasks + ", De esta estación: " + tasks.size();
    }
}
```

---

## 📁 Estructura del Proyecto Sugerida

```
foodtech-serenity-tests/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── pages/
│   │   │   │   ├── WaiterPage.java
│   │   │   │   ├── KitchenStationPage.java
│   │   │   │   └── BasePage.java
│   │   │   ├── steps/
│   │   │   │   ├── WaiterSteps.java
│   │   │   │   ├── KitchenSteps.java
│   │   │   │   └── CommonSteps.java
│   │   │   ├── stepdefinitions/
│   │   │   │   ├── WaiterStepDefinitions.java
│   │   │   │   └── KitchenStepDefinitions.java
│   │   │   ├── runners/
│   │   │   │   └── TestRunner.java
│   │   │   └── utils/
│   │   │       ├── DataGenerator.java
│   │   │       └── TestConfig.java
│   │   └── resources/
│   │       └── features/
│   │           ├── HU001_ProcesarPedido.feature
│   │           ├── HU002_ConsultarTareas.feature
│   │           └── HU003_EjecutarTarea.feature
├── pom.xml (o build.gradle)
├── serenity.properties
└── README.md
```

---

## 📝 Ejemplos de Features (Gherkin)

### HU001_ProcesarPedido.feature

```gherkin
# language: es
Característica: HU-001 - Procesar pedido de cocina
  Como responsable de cocina
  Quiero que el sistema reciba un pedido y lo descomponga automáticamente
  Para que cada área pueda trabajar de forma independiente

  Antecedentes:
    Dado que el mesero está en la vista principal
    Y existen mesas disponibles

  Escenario: Pedido únicamente con bebidas
    Dado que el mesero selecciona la mesa "A1"
    Cuando el mesero agrega el producto "Gin Tonic Premium"
    Y el mesero agrega el producto "Vino Tinto Reserva"
    Entonces el total de items debe ser 2
    Y los productos deben estar en la orden
    Cuando el mesero envía la orden
    Entonces el sistema confirma que la orden fue creada
    Y se generaron tareas para la estación "BAR"

  Escenario: Pedido mixto con múltiples tipos
    Dado que el mesero selecciona la mesa "B5"
    Cuando el mesero agrega el producto "Gin Tonic Premium"
    Y el mesero agrega el producto "Risotto de Trufa Negra"
    Y el mesero agrega el producto "Carpaccio de Res"
    Entonces el total de items debe ser 3
    Cuando el mesero envía la orden
    Entonces se generaron tareas para las estaciones:
      | BAR          |
      | HOT_KITCHEN  |
      | COLD_KITCHEN |
```

### HU002_ConsultarTareas.feature

```gherkin
# language: es
Característica: HU-002 - Consultar tareas por estación
  Como encargado de una estación
  Quiero visualizar únicamente las tareas de mi estación
  Para prepararlas sin confusión

  Antecedentes:
    Dado que existen las siguientes órdenes:
      | mesa | productos                                    | estaciones                          |
      | A1   | Gin Tonic Premium, Vino Tinto Reserva       | BAR                                 |
      | B2   | Risotto de Trufa Negra, Carpaccio de Res   | HOT_KITCHEN, COLD_KITCHEN          |
      | C3   | Gin Tonic Premium, Risotto de Trufa Negra  | BAR, HOT_KITCHEN                   |

  Escenario: Consultar tareas de barra
    Cuando el cocinero accede a la estación "BAR"
    Entonces debe ver 2 tareas pendientes
    Y todas las tareas deben pertenecer a la estación "BAR"
    Y debe ver las mesas "A1, C3"

  Escenario: Consultar tareas de cocina caliente
    Cuando el cocinero accede a la estación "HOT_KITCHEN"
    Entonces debe ver 2 tareas pendientes
    Y todas las tareas deben pertenecer a la estación "HOT_KITCHEN"
    Y debe ver las mesas "B2, C3"
```

### HU003_EjecutarTarea.feature

```gherkin
# language: es
Característica: HU-003 - Ejecutar tarea de preparación
  Como cocinero de una estación
  Quiero iniciar la preparación de una tarea
  Para que el sistema registre el progreso automáticamente

  Antecedentes:
    Dado que existe una orden para la mesa "A1" con:
      | Gin Tonic Premium      | BAR          |
      | Risotto de Trufa Negra | HOT_KITCHEN  |
      | Carpaccio de Res       | COLD_KITCHEN |

  Escenario: Iniciar preparación de tarea
    Dado que el cocinero está en la estación "BAR"
    Y existe una tarea pendiente para la mesa "A1"
    Cuando el cocinero inicia la preparación de la tarea
    Entonces la tarea debe cambiar a estado "En Preparación"
    Y el mesero debe ver la orden en estado "Preparando"
    Y el progreso de la orden debe ser "33%"

  Escenario: Orden se completa cuando todas las tareas terminan
    Dado que la orden de la mesa "A1" tiene 3 tareas
    Y 2 tareas están completadas
    Y 1 tarea está en preparación
    Cuando la última tarea se completa
    Entonces la orden debe cambiar a estado "Lista"
    Y el progreso debe ser "100%"
    Y el mesero debe ver el mensaje "Recoger en estación de entrega"
```

---

## 🔧 Configuración de Serenity

### serenity.properties

```properties
# Configuración general
serenity.project.name=FoodTech Kitchen Service - Automated Tests
serenity.test.root=features

# WebDriver
webdriver.driver=chrome
webdriver.chrome.driver=src/test/resources/drivers/chromedriver.exe

# Timeouts
webdriver.timeouts.implicitlywait=3000
serenity.timeout=10000

# Base URL
webdriver.base.url=http://localhost:5173

# Reports
serenity.report.encoding=UTF-8
serenity.reports.show.step.details=true
serenity.take.screenshots=FOR_FAILURES

# Cucumber
cucumber.options=--tags "not @wip"
```

### pom.xml (dependencias clave)

```xml
<dependencies>
    <!-- Serenity BDD -->
    <dependency>
        <groupId>net.serenity-bdd</groupId>
        <artifactId>serenity-core</artifactId>
        <version>3.9.0</version>
    </dependency>
    <dependency>
        <groupId>net.serenity-bdd</groupId>
        <artifactId>serenity-cucumber</artifactId>
        <version>3.9.0</version>
    </dependency>
    <dependency>
        <groupId>net.serenity-bdd</groupId>
        <artifactId>serenity-screenplay</artifactId>
        <version>3.9.0</version>
    </dependency>
    <dependency>
        <groupId>net.serenity-bdd</groupId>
        <artifactId>serenity-screenplay-webdriver</artifactId>
        <version>3.9.0</version>
    </dependency>
    
    <!-- Selenium -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.14.1</version>
    </dependency>
    
    <!-- JUnit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🎯 Comandos para Ejecutar Tests

```bash
# Ejecutar todos los tests
mvn clean verify

# Ejecutar un feature específico
mvn clean verify -Dcucumber.options="src/test/resources/features/HU001_ProcesarPedido.feature"

# Ejecutar tests con un tag específico
mvn clean verify -Dcucumber.options="--tags @smoke"

# Generar reporte sin ejecutar tests
mvn serenity:aggregate

# Ver reporte
# Abrir: target/site/serenity/index.html
```

---

## � Recomendaciones para Mejorar Testabilidad

### Items de Orden (OrderSummary.tsx)

**Problema Actual:** Los items individuales no tienen `data-testid` únicos.

**Solución Recomendada:** Agregar atributos al componente:
```tsx
// En OrderSummary.tsx, línea ~42
<div 
  key={product.name}
  data-testid={`order-item-${product.name.replace(/\s+/g, '-').toLowerCase()}`}
  data-product-name={product.name}
  className="flex justify-between items-start group"
>
  <div className="flex-1">
    <div className="flex items-center gap-1 sm:gap-2">
      <span 
        data-testid="order-item-name"
        className="text-white-text text-sm sm:text-base font-bold"
      >
        {product.name}
      </span>
      <span 
        data-testid="order-item-quantity"
        className="text-primary text-xs sm:text-sm font-bold"
      >
        x{product.quantity}
      </span>
    </div>
  </div>
  <button
    data-testid={`remove-product-btn-${product.name.replace(/\s+/g, '-').toLowerCase()}`}
    onClick={() => onRemoveProduct(product.name)}
    className="opacity-0 group-hover:opacity-100 transition-opacity text-silver-text hover:text-primary"
  >
    <span className="material-symbols-outlined text-lg">
      remove_circle
    </span>
  </button>
</div>
```

Esto facilitará enormemente la automatización con Serenity.

---

## �📊 Tips de Testing

### 1. Esperas Inteligentes

```java
// Esperar a que un elemento sea clickeable
waiterPage.waitFor(ExpectedConditions.elementToBeClickable(
    By.cssSelector("[data-testid='submit-order-btn']")
));

// Esperar a que cambie un atributo
waiterPage.waitForCondition()
    .withTimeout(5, TimeUnit.SECONDS)
    .until(driver -> {
        String status = driver.findElement(
            By.cssSelector("[data-testid='task-status-badge']")
        ).getText();
        return status.equals("En Preparación");
    });
```

### 2. Manejo de Estados Dinámicos

```java
// Polling para verificar cambio de estado
public void waitForOrderStatus(String orderId, String expectedStatus) {
    waiterPage.waitFor(10, TimeUnit.SECONDS)
        .until(driver -> 
            getKitchenOrderStatus(orderId).equals(expectedStatus)
        );
}
```

### 3. Data Builders para Tests

```java
public class OrderBuilder {
    private String table;
    private List<String> products = new ArrayList<>();
    
    public static OrderBuilder forTable(String table) {
        OrderBuilder builder = new OrderBuilder();
        builder.table = table;
        return builder;
    }
    
    public OrderBuilder withProduct(String product) {
        this.products.add(product);
        return this;
    }
    
    public void create(WaiterSteps waiterSteps) {
        waiterSteps.selectTable(table);
        products.forEach(waiterSteps::addProductToOrder);
        waiterSteps.submitOrder();
    }
}

// Uso:
OrderBuilder.forTable("A1")
    .withProduct("Gin Tonic Premium")
    .withProduct("Vino Tinto Reserva")
    .create(waiterSteps);
```

---

## ✅ Checklist de Implementación

- [ ] Configurar proyecto Maven/Gradle
- [ ] Instalar dependencias de Serenity
- [ ] Crear estructura de carpetas
- [ ] Implementar Page Objects (WaiterPage, KitchenStationPage)
- [ ] Implementar Steps (WaiterSteps, KitchenSteps)
- [ ] Crear Features (HU001, HU002, HU003)
- [ ] Implementar Step Definitions
- [ ] Configurar serenity.properties
- [ ] Ejecutar primer test
- [ ] Configurar CI/CD (opcional)
- [ ] Generar reportes

---

## 📚 Referencias

- [Serenity BDD Oficial](https://serenity-bdd.info/)
- [Cucumber](https://cucumber.io/)
- [Selenium Documentation](https://www.selenium.dev/)
- [XPath Cheatsheet](https://devhints.io/xpath)

---

**Versión:** 1.0  
**Fecha:** Enero 2026  
**Autor:** FoodTech Team
