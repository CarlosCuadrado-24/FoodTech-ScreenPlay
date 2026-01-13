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

    @Cuando("el personal de cocina caliente accede a su estación")
    public void elPersonalDeCocinaCalienteAccedeASuEstacion() {
        theActorInTheSpotlight().attemptsTo(
                NavegarAEstacion.llamada("COCINA CALIENTE")
        );
    }

    @Cuando("el personal de cocina fría accede a su estación")
    public void elPersonalDeCocinaFriaAccedeASuEstacion() {
        theActorInTheSpotlight().attemptsTo(
                NavegarAEstacion.llamada("COCINA FRÍA")
        );
    }

    @Entonces("el sistema debe mostrar al menos {int} tarea en la estación")
    public void elSistemaDebeMostrarAlMenosUnaTareaEnLaEstacion(Integer cantidadMinima) {
        theActorInTheSpotlight().should(
                seeThat(LaCantidadDeTareas.visibles(), greaterThanOrEqualTo(cantidadMinima))
        );
    }

    @Y("cada tarea debe mostrar el número de mesa")
    public void cadaTareaDebeMostrarElNumeroDeMesa() {
        theActorInTheSpotlight().should(
                seeThat(LaInformacionDeLaTarea.muestraLaMesa(""), equalTo(true))
        );
    }

    @Y("cada tarea debe mostrar el identificador de orden")
    public void cadaTareaDebeMostrarElIdentificadorDeOrden() {
        theActorInTheSpotlight().should(
                seeThat(LaInformacionDeLaTarea.muestraElIdDeOrden(), equalTo(true))
        );
    }
}
