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