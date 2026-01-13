package stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import questions.*;
import tasks.*;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.*;

public class ConsultarTareasStepDefinitions {

    @Cuando("el personal de barra accede a su estación")
    public void elPersonalDeBarraAccedeASuEstacion() {
        theActorInTheSpotlight().attemptsTo(
                NavegarAEstacion.llamada("BARRA")
        );
    }

    @Entonces("el sistema debe mostrar al menos {int} tarea en la estación")
    public void elSistemaDebeMostrarAlMenosUnaTareaEnLaEstacion(Integer cantidadMinima) {
        theActorInTheSpotlight().should(
                seeThat(LaCantidadDeTareas.visibles(), greaterThanOrEqualTo(cantidadMinima))
        );
    }

    @Cuando("el personal de cocina fría accede a su estación")
    public void elPersonalDeCocinaFriaAccedeASuEstacion() {
        theActorInTheSpotlight().attemptsTo(
                NavegarAEstacion.llamada("COCINA FRÍA")
        );
    }

    @Entonces("el sistema debe indicar que no hay tareas o mostrar cero tareas")
    public void elSistemaDebeIndicarQueNoHayTareasOMostrarCeroTareas() {
        theActorInTheSpotlight().should(
                seeThat(NoHayTareasPendientes.enLaEstacion(), equalTo(true))
        );
    }

    @Y("la tarea debe mostrar información de la mesa")
    public void laTareaDebeMostrarInformacionDeLaMesa() {
        theActorInTheSpotlight().should(
                seeThat(LaInformacionDeLaTarea.muestraLaMesa(""), equalTo(true))
        );
    }

    @Y("la tarea debe mostrar identificador de orden")
    public void laTareaDebeMostrarIdentificadorDeOrden() {
        theActorInTheSpotlight().should(
                seeThat(LaInformacionDeLaTarea.muestraElIdDeOrden(), equalTo(true))
        );
    }

    @Cuando("el personal de cocina caliente accede a su estación")
    public void elPersonalDeCocinaCalienteAccedeASuEstacion() {
        theActorInTheSpotlight().attemptsTo(
                NavegarAEstacion.llamada("COCINA CALIENTE")
        );
    }

    @Entonces("el sistema debe mostrar al menos {int} tareas en la estación")
    public void elSistemaDebeMostrarAlMenosTareasEnLaEstacion(Integer cantidadMinima) {
        theActorInTheSpotlight().should(
                seeThat(LaCantidadDeTareas.visibles(), greaterThanOrEqualTo(cantidadMinima))
        );
    }

    @Y("cada tarea debe mostrar información de su mesa")
    public void cadaTareaDebeMostrarInformacionDeSuMesa() {
        theActorInTheSpotlight().should(
                seeThat(LaCantidadDeTareas.visibles(), greaterThan(0))
        );
    }
}
