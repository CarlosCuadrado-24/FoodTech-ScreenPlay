# 📖 README - Demostración Completa de Esperas Implícitas con Serenity BDD

## 📚 Índice
1. [¿Qué son las Esperas Implícitas?](#-qué-son-las-esperas-implícitas)
2. [Propósito de este Proyecto](#-propósito-de-este-proyecto)
3. [Estructura del Proyecto](#-estructura-del-proyecto)
4. [Explicación Detallada de Cada Archivo](#-explicación-detallada-de-cada-archivo)
   - [Configuración y Runner](#1%EF%B8%8F⃣-configuración-y-runner)
   - [Escenario de Prueba](#2%EF%B8%8F⃣-escenario-de-prueba-gherkin)
   - [Hooks](#3%EF%B8%8F⃣-hooks)
   - [Step Definitions](#4%EF%B8%8F⃣-step-definitions)
   - [Tasks (Acciones)](#5%EF%B8%8F⃣-tasks-acciones)
   - [Questions (Validaciones)](#6%EF%B8%8F⃣-questions-validaciones)
   - [User Interface (Page Objects)](#7%EF%B8%8F⃣-user-interface-page-objects)
   - [Utilidades](#8%EF%B8%8F⃣-utilidades)
5. [Flujo de Ejecución Completo](#-flujo-de-ejecución-completo)
6. [Cómo Funcionan las Esperas Implícitas](#-cómo-funcionan-las-esperas-implícitas-en-este-ejemplo)
7. [Puntos Clave para Explicar a tu Profesor](#-puntos-clave-para-explicar-a-tu-profesor)
8. [Diagrama de Flujo](#-diagrama-de-flujo)
9. [Glosario de Conceptos](#-glosario-de-conceptos)

---

## 🎯 ¿Qué son las Esperas Implícitas?

### Definición Técnica
Las **esperas implícitas** son una configuración global de Selenium WebDriver que instruye al driver a **esperar un tiempo determinado** cuando intenta localizar un elemento que **NO existe en el DOM** en ese momento.

### ¿Qué SÍ hacen? ✅

| Función | Descripción |
|---------|-------------|
| **Esperan existencia en DOM** | Esperan a que un elemento **aparezca** en el árbol DOM |
| **Polling automático** | Reintentan la búsqueda cada ~500ms |
| **Configuración global** | Se aplican a **TODOS** los `findElement()` y `findElements()` |
| **Una sola configuración** | Se configuran UNA vez y funcionan durante toda la sesión |

### ¿Qué NO hacen? ❌

| Limitación | Explicación |
|------------|-------------|
| **NO esperan visibilidad** | Un elemento puede estar en el DOM pero con `display: none` |
| **NO esperan clicabilidad** | No validan si el elemento está listo para recibir clicks |
| **NO esperan habilitación** | No verifican si un campo está `enabled` o `disabled` |
| **NO esperan cambios de texto** | No detectan cuando el texto de un elemento cambia |
| **NO manejan condiciones complejas** | No pueden esperar "que el loader desaparezca Y el botón aparezca" |

### Analogía del Mundo Real 🌎

**Situación**: Vas a una tienda a comprar un producto específico.

**Sin esperas implícitas**:
```
Tú: "¿Tienen el Producto X?"
Empleado: "No"
Tú: "Ok, me voy" *sales de la tienda inmediatamente*
```

**Con esperas implícitas (10 segundos)**:
```
Tú: "¿Tienen el Producto X?"
Empleado: "Déjame revicar el inventario..."
*Esperas 2 minutos mientras busca*
Empleado: "¡Sí! Acá está, recién llegó al sistema"
Tú: "Perfecto, lo compro"
```

### Código Técnico Equivalente

```java
// SIN esperas implícitas
WebElement element = driver.findElement(By.id("mensaje"));
// Si no existe → NoSuchElementException INMEDIATA

// CON esperas implícitas (10 segundos)
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
WebElement element = driver.findElement(By.id("mensaje"));
// Si no existe → Selenium espera hasta 10 segundos haciendo polling cada 500ms
```

---

## 🎯 Propósito de este Proyecto

Este proyecto tiene un objetivo educativo muy específico: **demostrar ÚNICAMENTE las esperas implícitas** en un escenario realista.

### El Escenario Elegido

Usamos la página: `https://the-internet.herokuapp.com/dynamic_loading/2`

**¿Por qué esta página es perfecta?**

1. **Estado Inicial** (al cargar la página):
   ```html
   <div id="start">
     <button>Start</button>
   </div>
   <!-- El mensaje NO existe aún -->
   ```

2. **Después de hacer clic** en "Start" (0-5 segundos):
   ```html
   <div id="start">
     <button>Start</button>
   </div>
   <div id="loading">
     <img src="loading.gif">  <!-- Spinner visible -->
   </div>
   <!-- El mensaje AÚN NO existe -->
   ```

3. **Después de ~5 segundos**:
   ```html
   <div id="start">
     <button>Start</button>
   </div>
   <!-- El div loading desapareció del DOM -->
   <div id="finish">
     <h4>Hello World!</h4>  <!-- ¡AHORA SÍ existe! -->
   </div>
   ```

### ¿Por qué demuestra esperas implícitas?

- El elemento `<h4>Hello World!</h4>` **NO existe** cuando intentamos localizarlo
- **Sin esperas implícitas**: Falla instantáneamente con `NoSuchElementException`
- **Con esperas implícitas**: Selenium espera automáticamente ~5 segundos hasta que el elemento aparece en el DOM

---

## 📁 Estructura del Proyecto

```
proyecto/
│
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── hook/
│   │   │   │   └── AbrirNavegador.java           [1] Task reutilizable para abrir URLs
│   │   │   │
│   │   │   ├── questions/
│   │   │   │   └── TextoMostrado.java            [2] Question para obtener texto de elementos
│   │   │   │
│   │   │   ├── runners/
│   │   │   │   └── TestRunnerCargaDinamica.java  [3] Ejecutor de pruebas JUnit
│   │   │   │
│   │   │   ├── stepdefinitions/
│   │   │   │   ├── hook/
│   │   │   │   │   └── Hook.java                 [4] Inicialización del escenario
│   │   │   │   │
│   │   │   │   └── CargaDinamicaStepDefinition.java [5] Implementación de pasos Gherkin
│   │   │   │
│   │   │   ├── tasks/
│   │   │   │   └── EsperarCargaDinamica.java     [6] Task para hacer clic en botón
│   │   │   │
│   │   │   ├── userinterface/
│   │   │   │   └── PaginaCargaDinamica.java      [7] Definición de localizadores (Page Object)
│   │   │   │
│   │   │   └── util/
│   │   │       └── Constantes.java               [8] Constantes del proyecto
│   │   │
│   │   └── resources/
│   │       ├── features/
│   │       │   └── carga_dinamica.feature        [9] Escenario en Gherkin
│   │       │
│   │       └── serenity.conf                     [10] Configuración (ESPERAS IMPLÍCITAS)
│   │
│   └── build.gradle                              [11] Gestión de dependencias
```

---

## 📄 Explicación Detallada de Cada Archivo

### 1️⃣ Configuración y Runner

#### 📋 `serenity.conf` - Configuración Global del Proyecto

**Ubicación**: `src/test/resources/serenity.conf`

**Propósito Principal**: 
- ⭐ **CONFIGURAR LAS ESPERAS IMPLÍCITAS** (punto central del proyecto)
- Configurar el navegador (Chrome)
- Configurar opciones de reporte

**Contenido Clave**:
```properties
webdriver {
  capabilities {
    browserName = "chrome"
    acceptInsecureCerts = true
    "goog:chromeOptions" {
      args = ["remote-allow-origins=*","test-type", "no-sandbox", 
              "ignore-certificate-errors", "--start-maximized"]
    }
  }
  
  # ⭐⭐⭐ CONFIGURACIÓN DE ESPERAS IMPLÍCITAS ⭐⭐⭐
  timeouts {
    # 10000 milisegundos = 10 segundos
    implicitlywait = 10000
  }
}

serenity {
  encoding = "UTF-8"
  report.encoding = "UTF-8"
  compress.filenames = true
  take.screenshots = FOR_EACH_ACTION
}
```

**Análisis de la Configuración**:

```properties
implicitlywait = 10000
```

**¿Qué hace esta línea?**
1. Le dice a Selenium: "Cuando busques un elemento, espera HASTA 10 segundos"
2. Se aplica a **TODOS** los métodos que buscan elementos:
   - `driver.findElement()`
   - `driver.findElements()`
   - `element.findElement()`
   - Targets de Serenity que usan localizadores
3. Es una configuración **GLOBAL** que afecta a toda la sesión del WebDriver
4. **No requiere código adicional en Java**

**Flujo interno de Selenium con esta configuración**:
```
Intento 1 (t=0ms):     driver.findElement(locator) → ¿Existe? NO
Espera 500ms
Intento 2 (t=500ms):   driver.findElement(locator) → ¿Existe? NO
Espera 500ms
Intento 3 (t=1000ms):  driver.findElement(locator) → ¿Existe? NO
Espera 500ms
...
Intento 11 (t=5000ms): driver.findElement(locator) → ¿Existe? SÍ → ¡ENCONTRADO!
```

---

#### 🏃 `TestRunnerCargaDinamica.java` - Ejecutor de Pruebas

**Ubicación**: `src/test/java/runners/TestRunnerCargaDinamica.java`

**Propósito**:
- Punto de entrada para ejecutar las pruebas
- Conecta JUnit con Cucumber
- Define dónde buscar features y step definitions

**Código Completo**:
```java
package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "stepdefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, 
    value = "io.cucumber.core.plugin.SerenityReporterParallel, pretty, timeline:build/test-results/timeline")
public class TestRunnerCargaDinamica {
}
```

**Análisis de Anotaciones**:

- `@Suite`: Indica que esta clase es una suite de pruebas JUnit 5
- `@IncludeEngines("cucumber")`: Usa el motor de Cucumber para ejecutar las pruebas
- `@SelectClasspathResource("features")`: Busca archivos `.feature` en `src/test/resources/features/`
- `@ConfigurationParameter(key = GLUE, value = "stepdefinitions")`: Busca step definitions en el paquete `stepdefinitions` y subpaquetes

**¿Cómo se ejecuta?**
```bash
gradle clean test
```

**Flujo de ejecución**:
```
1. Gradle invoca TestRunnerCargaDinamica
2. JUnit 5 detecta @Suite
3. Cucumber busca features en "features/"
4. Encuentra carga_dinamica.feature
5. Lee el escenario
6. Busca step definitions en "stepdefinitions"
7. Ejecuta los pasos uno por uno
8. Genera reporte
```

---

### 2️⃣ Escenario de Prueba (Gherkin)

#### 📝 `carga_dinamica.feature` - Definición del Escenario

**Ubicación**: `src/test/resources/features/carga_dinamica.feature`

**Propósito**:
- Definir el comportamiento esperado en lenguaje natural
- Servir como documentación ejecutable
- Permitir comunicación entre técnicos y no técnicos

**Código Completo**:
```gherkin
#language: es

Característica: Demostración de esperas implícitas con carga dinámica de elementos
  Como estudiante de automatización
  Quiero demostrar cómo funcionan las esperas implícitas
  Para entender que solo esperan a que elementos EXISTAN en el DOM

  Escenario: Espera implícita al buscar elemento que se agrega al DOM después de un delay
    Dado que el usuario abre la pagina de carga dinamica
    Cuando el usuario hace clic en el boton de inicio
    Entonces debe aparecer el mensaje "Hello World!"
```

**Análisis Detallado**:

```gherkin
#language: es
```
- Define que usaremos palabras clave en español (Dado, Cuando, Entonces)

```gherkin
Dado que el usuario abre la pagina de carga dinamica
```
- **Precondición**: Estado inicial del sistema
- Abre el navegador y navega a la URL

```gherkin
Cuando el usuario hace clic en el boton de inicio
```
- **Acción**: Lo que el usuario hace
- Trigger que inicia la carga dinámica

```gherkin
Entonces debe aparecer el mensaje "Hello World!"
```
- **Resultado esperado**: Lo que debería suceder
- ⭐ **AQUÍ ACTÚAN LAS ESPERAS IMPLÍCITAS** ⭐

**¿Por qué este escenario demuestra esperas implícitas?**

| Momento | Estado del DOM | Sin esperas | Con esperas |
|---------|----------------|-------------|-------------|
| t=0s (Dado) | Botón existe | ✅ Encuentra | ✅ Encuentra |
| t=0.1s (Cuando) | Click ejecutado | ✅ Exitoso | ✅ Exitoso |
| t=0.2s (Entonces) | Mensaje NO existe | ❌ Exception | ⏳ Espera... |
| t=5s | ✅ Mensaje aparece | - | ✅ Encuentra |

---

### 3️⃣ Hooks

#### 🎣 `Hook.java` - Inicialización del Escenario

**Ubicación**: `src/test/java/stepdefinitions/hook/Hook.java`

**Propósito**:
- Ejecutar código **ANTES** de cada escenario
- Inicializar el stage de Screenplay
- Preparar los actores

**Código Completo**:
```java
package stepdefinitions.hook;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hook {

    @Before
    public void iniciarEscenario(){
        OnStage.setTheStage(new OnlineCast());
        
        // NOTA: Las esperas implícitas están configuradas en serenity.conf
        // con webdriver.timeouts.implicitlywait = 10000
        // No es necesario configurarlas programáticamente aquí
    }
}
```

**Análisis**:

- `@Before`: Se ejecuta **antes de cada escenario**
- `OnStage.setTheStage(new OnlineCast())`: Prepara el "escenario" para los actores
- Las esperas implícitas se aplican automáticamente desde `serenity.conf`

---

#### 🔧 `AbrirNavegador.java` - Task Reutilizable

**Ubicación**: `src/test/java/hook/AbrirNavegador.java`

**Propósito**:
- Encapsular la acción de abrir una URL
- Reutilizable en cualquier escenario
- Siguiendo el patrón Screenplay (Task)

**Código Completo**:
```java
package hook;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirNavegador implements Task {
    private String url;

    public AbrirNavegador(String url) {
        this.url = url;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(url));
    }

    public static AbrirNavegador abrirUrl(String url){
        return Tasks.instrumented(AbrirNavegador.class, url);
    }
}
```

**Análisis**:

- `implements Task`: Representa una **acción** que un actor puede realizar
- `performAs(actor)`: Define QUÉ hace el actor
- `Open.url(url)`: Acción predefinida de Serenity que abre el navegador
- `Tasks.instrumented()`: Permite que Serenity reporte esta acción

**Uso**:
```java
actor.attemptsTo(AbrirNavegador.abrirUrl("https://..."));
```

---

### 4️⃣ Step Definitions

#### 📌 `CargaDinamicaStepDefinition.java` - Implementación de Pasos Gherkin

**Ubicación**: `src/test/java/stepdefinitions/CargaDinamicaStepDefinition.java`

**Propósito**:
- **CONECTAR** los pasos de Gherkin con código Java
- Implementar la lógica de cada paso (Dado, Cuando, Entonces)
- Orquestar Tasks y Questions

**Código Completo**:
```java
package stepdefinitions;

import hook.AbrirNavegador;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;
import static questions.TextoMostrado.textoMostrado;
import static tasks.EsperarCargaDinamica.hacerClicEnBotonInicio;
import static userinterface.PaginaCargaDinamica.MENSAJE_FINAL;
import static util.Constantes.ACTOR;
import static util.Constantes.URL_CARGA_DINAMICA;

public class CargaDinamicaStepDefinition {

    @Dado("que el usuario abre la pagina de carga dinamica")
    public void queElUsuarioAbreLaPaginaDeCargaDinamica() {
        OnStage.theActorCalled(ACTOR).attemptsTo(
                AbrirNavegador.abrirUrl(URL_CARGA_DINAMICA)
        );
    }

    @Cuando("el usuario hace clic en el boton de inicio")
    public void elUsuarioHaceClicEnElBotonDeInicio() {
        theActorInTheSpotlight().attemptsTo(
                hacerClicEnBotonInicio()
        );
    }

    @Entonces("debe aparecer el mensaje {string}")
    public void debeAparecerElMensaje(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat(textoMostrado(MENSAJE_FINAL), equalTo(mensajeEsperado))
        );
    }
}
```

**Análisis Exhaustivo**:

#### Método 1: `@Dado` - Precondición

```java
@Dado("que el usuario abre la pagina de carga dinamica")
public void queElUsuarioAbreLaPaginaDeCargaDinamica() {
    OnStage.theActorCalled(ACTOR).attemptsTo(
            AbrirNavegador.abrirUrl(URL_CARGA_DINAMICA)
    );
}
```

**Componentes**:
- `@Dado`: Anotación que conecta con el paso Gherkin
- `OnStage.theActorCalled(ACTOR)`: Crea o recupera el actor "Maria"
- `.attemptsTo()`: El actor intenta realizar una tarea
- `AbrirNavegador.abrirUrl()`: Task que navega a la URL

**Estado después**:
- Navegador abierto
- Página cargada
- HTML inicial presente (botón existe, mensaje NO)

---

#### Método 2: `@Cuando` - Acción

```java
@Cuando("el usuario hace clic en el boton de inicio")
public void elUsuarioHaceClicEnElBotonDeInicio() {
    theActorInTheSpotlight().attemptsTo(
            hacerClicEnBotonInicio()
    );
}
```

**Componentes**:
- `theActorInTheSpotlight()`: Recupera el último actor que actuó
- `hacerClicEnBotonInicio()`: Task que hace clic en el botón

**Estado después**:
- Click ejecutado
- Loader aparece
- **Comienza el delay de ~5 segundos**

---

#### Método 3: `@Entonces` - Validación (⭐ ESPERAS IMPLÍCITAS)

```java
@Entonces("debe aparecer el mensaje {string}")
public void debeAparecerElMensaje(String mensajeEsperado) {
    theActorInTheSpotlight().should(
            seeThat(textoMostrado(MENSAJE_FINAL), equalTo(mensajeEsperado))
    );
}
```

**Componentes**:
- `{string}`: Parámetro capturado ("Hello World!")
- `textoMostrado(MENSAJE_FINAL)`: Question que obtiene el texto
- `equalTo()`: Matcher que valida igualdad

**⭐ AQUÍ ACTÚAN LAS ESPERAS IMPLÍCITAS**:

```
t=0ms:    Intenta localizar //div[@id='finish']/h4 → NO existe → espera 500ms
t=500ms:  Reintenta → NO existe → espera 500ms
t=1000ms: Reintenta → NO existe → espera 500ms
...
t=5000ms: Reintenta → ✅ EXISTE → obtiene texto "Hello World!"
```

---

### 5️⃣ Tasks (Acciones)

#### ⚙️ `EsperarCargaDinamica.java` - Task para Hacer Click

**Ubicación**: `src/test/java/tasks/EsperarCargaDinamica.java`

**Propósito**:
- Encapsular la acción de hacer clic en el botón "Start"
- Reutilizable y mantenible

**Código Completo**:
```java
package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import static userinterface.PaginaCargaDinamica.BOTON_INICIO;

public class EsperarCargaDinamica implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BOTON_INICIO)
        );
    }

    public static EsperarCargaDinamica hacerClicEnBotonInicio(){
        return Tasks.instrumented(EsperarCargaDinamica.class);
    }
}
```

**Análisis**:
- `Click.on(BOTON_INICIO)`: Acción predefinida de Serenity
- `BOTON_INICIO`: Target definido en Page Object
- Este Task NO necesita esperas implícitas (el botón existe desde el inicio)

---

### 6️⃣ Questions (Validaciones)

#### ❓ `TextoMostrado.java` - Question para Obtener Texto

**Ubicación**: `src/test/java/questions/TextoMostrado.java`

**Propósito**:
- Encapsular la lógica de obtener texto de un elemento
- ⭐ **AQUÍ ES DONDE LAS ESPERAS IMPLÍCITAS ACTÚAN MÁS EVIDENTEMENTE** ⭐

**Código Completo**:
```java
package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class TextoMostrado implements Question<String> {

    private final Target elementoConTexto;

    public TextoMostrado(Target elementoConTexto) {
        this.elementoConTexto = elementoConTexto;
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(elementoConTexto).answeredBy(actor);
    }

    public static TextoMostrado textoMostrado(Target elementoConTexto){
        return new TextoMostrado(elementoConTexto);
    }
}
```

**Análisis Crítico**:

```java
@Override
public String answeredBy(Actor actor) {
    return Text.of(elementoConTexto).answeredBy(actor);
}
```

**¿Qué pasa internamente?**

1. `Text.of(elementoConTexto)` intenta localizar el elemento
2. Llama a `elementoConTexto.resolveFor(actor)`
3. Ejecuta `driver.findElement(By.xpath("//div[@id='finish']/h4"))`
4. **⭐ AQUÍ ACTÚAN LAS ESPERAS IMPLÍCITAS ⭐**
5. Selenium hace polling cada 500ms durante hasta 10 segundos
6. Cuando encuentra el elemento, obtiene su texto

**Sin esperas implícitas**:
```
t=0ms: findElement() → NO existe → ❌ NoSuchElementException
```

**Con esperas implícitas**:
```
t=0ms:    findElement() → NO existe → espera 500ms
t=500ms:  findElement() → NO existe → espera 500ms
...
t=5000ms: findElement() → ✅ EXISTE → getText() → "Hello World!"
```

---

### 7️⃣ User Interface (Page Objects)

#### 🖥️ `PaginaCargaDinamica.java` - Definición de Localizadores

**Ubicación**: `src/test/java/userinterface/PaginaCargaDinamica.java`

**Propósito**:
- Centralizar todos los localizadores de la página
- Facilitar mantenimiento
- Pattern: Page Object

**Código Completo**:
```java
package userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaCargaDinamica extends PageObject {

    public static final Target BOTON_INICIO = Target.the("Botón para iniciar la carga dinámica")
            .locatedBy("//button[text()='Start']");

    public static final Target MENSAJE_FINAL = Target.the("Mensaje que aparece después de la carga")
            .locatedBy("//div[@id='finish']/h4");
}
```

**Análisis de Targets**:

#### `BOTON_INICIO`
```java
Target.the("Botón para iniciar la carga dinámica")
      .locatedBy("//button[text()='Start']");
```

- **XPath**: `//button[text()='Start']`
- Busca un `<button>` con texto "Start"
- **Relación con esperas implícitas**: Este botón existe desde el inicio, NO necesita esperas

**HTML correspondiente**:
```html
<button>Start</button>
```

---

#### `MENSAJE_FINAL` ⭐

```java
Target.the("Mensaje que aparece después de la carga")
      .locatedBy("//div[@id='finish']/h4");
```

- **XPath**: `//div[@id='finish']/h4`
- Busca un `<h4>` dentro de un `<div id="finish">`
- **⭐ RELACIÓN CON ESPERAS IMPLÍCITAS**: Este elemento NO existe al inicio

**Estado del DOM en diferentes momentos**:

| Momento | HTML | ¿Existe? |
|---------|------|----------|
| Al cargar | `<button>Start</button>` | ❌ NO |
| Después de click (0-5s) | `<button>Start</button><div id="loading">...</div>` | ❌ NO |
| Después de ~5s | `<button>Start</button><div id="finish"><h4>Hello World!</h4></div>` | ✅ SÍ |

**Cuando se busca MENSAJE_FINAL**:
```
1. MENSAJE_FINAL.resolveFor(actor) se llama
2. driver.findElement(By.xpath("//div[@id='finish']/h4"))
3. Selenium busca en el DOM actual
   ├─ t=0ms: NO existe → Esperas implícitas activadas
   ├─ Polling cada 500ms
   └─ t=5000ms: ✅ EXISTE → Retorna el elemento
```

---

### 8️⃣ Utilidades

#### 🔧 `Constantes.java` - Valores Centralizados

**Ubicación**: `src/test/java/util/Constantes.java`

**Propósito**:
- Centralizar valores constantes
- Evitar "magic strings"
- Facilitar mantenimiento

**Código Completo**:
```java
package util;

public class Constantes {

    public Constantes() {
    }

    public static final String ACTOR = "Maria";
    
    public static final String URL_CARGA_DINAMICA = 
        "https://the-internet.herokuapp.com/dynamic_loading/2";
}
```

**Análisis**:

```java
public static final String ACTOR = "Maria";
```
- Nombre del actor principal en Screenplay
- Uso: `OnStage.theActorCalled(ACTOR)`

```java
public static final String URL_CARGA_DINAMICA = 
    "https://the-internet.herokuapp.com/dynamic_loading/2";
```
- URL de la página de prueba
- Uso: `AbrirNavegador.abrirUrl(URL_CARGA_DINAMICA)`

**¿Por qué esta URL?**

Esta página es perfecta para demostrar esperas implícitas porque:
1. Es pública y siempre disponible
2. Tiene carga dinámica real (~5 segundos)
3. El mensaje NO existe al inicio del DOM
4. HTML claro y predecible

---

## 🔄 Flujo de Ejecución Completo

### Paso a Paso Detallado

```
┌─────────────────────────────────────────────────────────────────┐
│ INICIO: Ejecución del Comando                                   │
│ $ gradle clean test                                             │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 1: Gradle invoca TestRunnerCargaDinamica                  │
│ - JUnit 5 detecta @Suite                                       │
│ - Cucumber se prepara para ejecutar                            │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 2: Cucumber busca archivos .feature                       │
│ - Encuentra: carga_dinamica.feature                           │
│ - Parsea el archivo Gherkin                                    │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 3: Cucumber busca Step Definitions                        │
│ - Escanea paquete "stepdefinitions"                           │
│ - Encuentra: CargaDinamicaStepDefinition y Hook               │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 4: Serenity carga configuración                           │
│ - Lee serenity.conf                                            │
│ - ⭐ Configura esperas implícitas: 10 segundos                 │
│ - Configura Chrome                                             │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 5: Hook @Before se ejecuta                                │
│ - OnStage.setTheStage(new OnlineCast())                       │
│ - Prepara el "escenario" para los actores                     │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 6: DADO que el usuario abre la pagina                     │
│                                                                 │
│ - OnStage.theActorCalled("Maria")                             │
│ - AbrirNavegador.abrirUrl(URL)                                │
│ - Serenity crea WebDriver (Chrome)                            │
│ - ⭐ Aplica esperas implícitas de 10s                          │
│ - Navega a la URL                                              │
│                                                                 │
│ Estado del DOM:                                                │
│ <button>Start</button>                                        │
│ <!-- Mensaje NO existe -->                                     │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 7: CUANDO el usuario hace clic en el boton                │
│                                                                 │
│ - hacerClicEnBotonInicio()                                     │
│ - Click.on(BOTON_INICIO)                                       │
│ - Localiza botón con XPath: //button[text()='Start']          │
│ - Hace click                                                   │
│                                                                 │
│ Estado del DOM (después del click):                            │
│ <button>Start</button>                                        │
│ <div id="loading">                                            │
│   <img src="loading.gif">  ← Spinner                          │
│ </div>                                                        │
│ <!-- Mensaje TODAVÍA NO existe -->                            │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 8: ENTONCES debe aparecer el mensaje                      │
│                                                                 │
│ ⭐⭐⭐ AQUÍ ACTÚAN LAS ESPERAS IMPLÍCITAS ⭐⭐⭐                 │
│                                                                 │
│ Desglose de ejecución:                                         │
│                                                                 │
│ t=0ms:    textoMostrado(MENSAJE_FINAL)                        │
│           └─ Intenta localizar: //div[@id='finish']/h4        │
│              └─ NO existe → Espera 500ms                       │
│                                                                 │
│ t=500ms:  Reintenta localizar                                  │
│           └─ NO existe → Espera 500ms                          │
│                                                                 │
│ t=1000ms: Reintenta localizar                                  │
│           └─ NO existe → Espera 500ms                          │
│                                                                 │
│ ... (continúa cada 500ms)                                      │
│                                                                 │
│ t=5000ms: Reintenta localizar                                  │
│           └─ ✅ EXISTE → getText() → "Hello World!"            │
│                                                                 │
│ - Valida: "Hello World!" == "Hello World!"                     │
│ - ✅ ASSERCIÓN EXITOSA                                         │
│                                                                 │
│ Estado final del DOM:                                          │
│ <button>Start</button>                                        │
│ <div id="finish">                                             │
│   <h4>Hello World!</h4>  ← Ahora existe                       │
│ </div>                                                        │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ PASO 9: Generación de Reporte                                  │
│ - Serenity genera reporte HTML                                │
│ - Disponible en: target/site/serenity/index.html             │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│ RESULTADO FINAL                                                 │
│ ✅ Prueba EXITOSA                                              │
│ ✅ Demostración de esperas implícitas completa                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔍 Cómo Funcionan las Esperas Implícitas en Este Ejemplo

### Comparación: Con vs Sin Esperas Implícitas

#### ❌ Sin Esperas Implícitas (implicitlyWait = 0)

```
Tiempo: 0ms
├─ Paso: Entonces debe aparecer el mensaje "Hello World!"
├─ Intenta localizar: //div[@id='finish']/h4
├─ Búsqueda en DOM: NO existe
├─ ❌ NoSuchElementException inmediata
└─ ❌ Prueba FALLA en <1 segundo
```

**Log de error**:
```
org.openqa.selenium.NoSuchElementException: 
  Unable to locate element: //div[@id='finish']/h4
```

---

#### ✅ Con Esperas Implícitas (implicitlyWait = 10000ms)

```
Tiempo: 0ms → 5000ms
├─ Paso: Entonces debe aparecer el mensaje "Hello World!"
├─ Intenta localizar: //div[@id='finish']/h4
│
├─ Polling automático cada 500ms:
│  ├─ 0ms: NO existe → espera 500ms
│  ├─ 500ms: NO existe → espera 500ms
│  ├─ 1000ms: NO existe → espera 500ms
│  ├─ 1500ms: NO existe → espera 500ms
│  ├─ 2000ms: NO existe → espera 500ms
│  ├─ 2500ms: NO existe → espera 500ms
│  ├─ 3000ms: NO existe → espera 500ms
│  ├─ 3500ms: NO existe → espera 500ms
│  ├─ 4000ms: NO existe → espera 500ms
│  ├─ 4500ms: NO existe → espera 500ms
│  └─ 5000ms: ✅ EXISTE → obtiene elemento
│
├─ Obtiene texto: "Hello World!"
├─ Valida: "Hello World!" == "Hello World!"
└─ ✅ Prueba PASA en ~5 segundos
```

**Log exitoso**:
```
✓ Dado que el usuario abre la pagina de carga dinamica
✓ Cuando el usuario hace clic en el boton de inicio
✓ Entonces debe aparecer el mensaje "Hello World!"

Tests run: 1, Failures: 0, Errors: 0
```

---

### Diagrama de Estados del DOM

```
┌──────────────────────────────────────────────────────────────────┐
│ ESTADO 1: Página inicial (t=0)                                   │
├──────────────────────────────────────────────────────────────────┤
│                                                                   │
│  <div id="start">                                                │
│    <button>Start</button>  ← Existe desde el inicio              │
│  </div>                                                          │
│                                                                   │
│  <!-- <div id="finish"> NO existe aún -->                        │
│                                                                   │
│  🟢 BOTON_INICIO: Localizable                                    │
│  🔴 MENSAJE_FINAL: NO localizable                                │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
                          ↓ Click en botón
┌──────────────────────────────────────────────────────────────────┐
│ ESTADO 2: Después del click (t=0.1s → t=5s)                     │
├──────────────────────────────────────────────────────────────────┤
│                                                                   │
│  <div id="start">                                                │
│    <button>Start</button>                                        │
│  </div>                                                          │
│                                                                   │
│  <div id="loading" class="example">  ← Aparece dinámicamente     │
│    <img src="/img/ajax-loader.gif">  ← Spinner visible           │
│  </div>                                                          │
│                                                                   │
│  <!-- <div id="finish"> TODAVÍA NO existe -->                    │
│                                                                   │
│  🟢 BOTON_INICIO: Localizable                                    │
│  🔴 MENSAJE_FINAL: NO localizable                                │
│                                                                   │
│  ⏳ Si buscamos MENSAJE_FINAL aquí:                              │
│     Las esperas implícitas hacen polling cada 500ms              │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
                          ↓ Pasan ~5 segundos
┌──────────────────────────────────────────────────────────────────┐
│ ESTADO 3: Carga completada (t≥5s)                                │
├──────────────────────────────────────────────────────────────────┤
│                                                                   │
│  <div id="start">                                                │
│    <button>Start</button>                                        │
│  </div>                                                          │
│                                                                   │
│  <!-- <div id="loading"> desapareció del DOM -->                 │
│                                                                   │
│  <div id="finish" style="">  ← Aparece en el DOM                 │
│    <h4>Hello World!</h4>  ← ¡Finalmente existe!                  │
│  </div>                                                          │
│                                                                   │
│  🟢 BOTON_INICIO: Localizable                                    │
│  🟢 MENSAJE_FINAL: Localizable ← Ahora SÍ                        │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
```

---

## 📝 Puntos Clave para Explicar a tu Profesor

### 1. Concepto de Esperas Implícitas

**Definición Simple**:
> "Las esperas implícitas le dicen a Selenium: 'Si no encuentras un elemento inmediatamente, espera X segundos antes de rendirte, reintentando cada medio segundo'."

**Analogía**:
> "Es como cuando llamas a un amigo por teléfono. Sin esperas implícitas, si no contesta al primer timbre, cuelgas. Con esperas implícitas, dejas que suene 10 veces antes de colgar."

---

### 2. ¿Qué SÍ hacen?

✅ **Esperan EXISTENCIA en el DOM**
- Solo esperan a que un elemento APAREZCA en el árbol DOM
- Ejemplo: `<h4>Hello World!</h4>` que NO existe y luego SÍ existe

✅ **Polling automático**
- Selenium reintenta cada ~500ms
- No necesitas escribir código de espera explícito

✅ **Configuración global**
- Se configura UNA vez
- Aplica a TODAS las búsquedas de elementos

---

### 3. ¿Qué NO hacen?

❌ **NO esperan visibilidad**
- Un elemento puede existir en el DOM pero tener `display: none`
- Las esperas implícitas NO validan esto

❌ **NO esperan clicabilidad**
- No verifican si un elemento está listo para recibir clicks
- No detectan si está tapado por otro elemento

❌ **NO esperan cambios de texto**
- Si un elemento ya existe pero su texto cambia, NO esperan

❌ **NO manejan condiciones complejas**
- No pueden esperar "que X desaparezca Y que Y aparezca"

---

### 4. ¿Por qué este proyecto lo demuestra correctamente?

**Razón #1: El elemento NO existe**
```html
<!-- Al cargar la página -->
<button>Start</button>
<!-- El mensaje NO está en el DOM -->
```

**Razón #2: Aparece después de un delay**
```html
<!-- Después de ~5 segundos -->
<button>Start</button>
<div id="finish">
  <h4>Hello World!</h4>  ← Ahora SÍ existe
</div>
```

**Razón #3: Demuestra el polling**
- Sin esperas: Falla en <1 segundo
- Con esperas: Espera ~5 segundos haciendo reintentos

---

### 5. Configuración en Serenity

**Forma correcta** (en `serenity.conf`):
```properties
webdriver {
  timeouts {
    implicitlywait = 10000  # 10 segundos
  }
}
```

**Ventajas**:
- Declarativo
- Centralizado
- No requiere código Java adicional
- Se aplica automáticamente

---

### 6. Diferencia con Esperas Explícitas

| Característica | Esperas Implícitas | Esperas Explícitas |
|----------------|-------------------|-------------------|
| **Qué esperan** | EXISTENCIA en DOM | Condiciones complejas |
| **Alcance** | GLOBAL (todos los findElement) | LOCAL (caso específico) |
| **Configuración** | Una vez al inicio | Cada vez que se necesita |
| **Flexibilidad** | Limitada | Alta |
| **Uso típico** | Elementos que tardan en aparecer | Visibilidad, clicabilidad, etc. |

---

### 7. Cuándo usar Esperas Implícitas

✅ **USAR cuando**:
- Elementos que tardan en agregarse al DOM
- Aplicaciones con carga dinámica de contenido
- Como configuración base del proyecto

❌ **NO USAR para**:
- Verificar que elementos NO existan (falsa espera de 10s)
- Condiciones complejas (usar explícitas)
- Cuando necesitas tiempos diferentes por elemento

---

### 8. Flujo técnico de este ejemplo

```
1. Usuario abre página → Botón existe, mensaje NO
2. Usuario hace click → Inicia carga dinámica
3. Prueba valida mensaje → Mensaje NO existe
4. ⭐ Esperas implícitas activadas ⭐
5. Polling cada 500ms durante ~5 segundos
6. Mensaje aparece en el DOM
7. Selenium lo encuentra
8. Obtiene texto "Hello World!"
9. Valida contra valor esperado
10. ✅ Prueba pasa
```

---

## 📊 Diagrama de Flujo

```
                    ┌─────────────────┐
                    │  gradle test    │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ TestRunner      │
                    │ Cucumber+JUnit  │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ Hook @Before    │
                    │ Inicia Stage    │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ DADO: Abre URL  │
                    │ WebDriver inicia│
                    │ ⭐ Esperas=10s  │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ CUANDO: Click   │
                    │ Botón Start     │
                    └────────┬────────┘
                             │
                    ┌────────▼────────────────┐
                    │ ENTONCES: Valida texto  │
                    └────────┬────────────────┘
                             │
                    ┌────────▼────────────────┐
                    │ TextoMostrado.java      │
                    │ answeredBy(actor)       │
                    └────────┬────────────────┘
                             │
                    ┌────────▼────────────────┐
                    │ Text.of(MENSAJE_FINAL)  │
                    └────────┬────────────────┘
                             │
                    ┌────────▼────────────────┐
                    │ MENSAJE_FINAL.resolve() │
                    └────────┬────────────────┘
                             │
        ┌────────────────────▼────────────────────┐
        │ driver.findElement(By.xpath(...))       │
        │ ⭐⭐⭐ ESPERAS IMPLÍCITAS ⭐⭐⭐          │
        └────────────────────┬────────────────────┘
                             │
                ┌────────────▼────────────┐
                │ Polling cada 500ms      │
                │ ┌─────────────────────┐ │
                │ │ t=0ms:   NO existe  │ │
                │ │ t=500ms: NO existe  │ │
                │ │ t=1000ms:NO existe  │ │
                │ │ ...                 │ │
                │ │ t=5000ms:✅ EXISTE  │ │
                │ └─────────────────────┘ │
                └────────────┬────────────┘
                             │
                    ┌────────▼────────┐
                    │ getText()       │
                    │ "Hello World!"  │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ equalTo()       │
                    │ ✅ PASA         │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ Reporte Serenity│
                    └─────────────────┘
```

---

## 📚 Glosario de Conceptos

### Esperas Implícitas
Configuración global de Selenium que instruye al driver a esperar un tiempo determinado cuando busca un elemento que NO existe en el DOM.

### DOM (Document Object Model)
Estructura en árbol que representa el HTML de una página web. Los elementos pueden estar o no estar en el DOM.

### Polling
Proceso de reintentar una operación repetidamente con intervalos de tiempo regulares (en este caso, cada 500ms).

### Target (Serenity)
Representación de un elemento de la página que incluye un localizador (XPath, CSS) y una descripción legible.

### Task (Screenplay)
Clase que representa una acción que un actor puede realizar (ej: hacer clic, navegar).

### Question (Screenplay)
Clase que representa una consulta sobre el estado del sistema (ej: obtener texto, verificar visibilidad).

### Step Definition
Método Java anotado que conecta un paso de Gherkin con código ejecutable.

### Page Object
Patrón de diseño que centraliza los localizadores de elementos de una página en una sola clase.

### XPath
Lenguaje de consulta para seleccionar nodos en un documento XML/HTML. Ejemplo: `//div[@id='finish']/h4`

### Gherkin
Lenguaje de dominio específico para escribir especificaciones ejecutables en formato legible (Given-When-Then).

---

## 🎓 Conclusión

Este proyecto demuestra de forma clara y práctica cómo funcionan las **esperas implícitas** en Selenium con Serenity BDD. El ejemplo elegido es perfecto porque:

1. ✅ El elemento **NO existe** en el DOM inicialmente
2. ✅ **Aparece dinámicamente** después de ~5 segundos
3. ✅ Demuestra el **polling automático** de Selenium
4. ✅ Muestra la **diferencia** entre tener y no tener esperas configuradas
5. ✅ Es **simple** de entender y explicar

**Punto clave para recordar**: Las esperas implícitas SOLO esperan a que un elemento **EXISTA** en el DOM, no esperan visibilidad, clicabilidad ni otros estados.

---

## 🚀 Cómo Ejecutar

```bash
# Ejecutar pruebas
gradle clean test

# Ver reporte
# Abrir: target/site/serenity/index.html
```

---

## 📞 Contacto

Si tienes preguntas sobre este proyecto, revisa los comentarios en el código o consulta la documentación de Serenity BDD.

---

**¡Buena suerte con tu explicación! 🎉**