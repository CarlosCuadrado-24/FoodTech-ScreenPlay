package stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.ensure.Ensure;
import questions.*;
import tasks.EliminarProducto;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions para HU-QA-002: Verificar modificación de pedidos antes de enviar.
 * Los steps son delgados y solo orquestan Tasks y Questions.
 */
public class ModificacionDePedidoStepDefinitions {

    private static final String EL_MESERO = "El Mesero";

    @Cuando("el mesero elimina {string} del pedido")
    public void elMeseroEliminaDelPedido(String nombreProducto) {
        theActorCalled(EL_MESERO).attemptsTo(
                EliminarProducto.llamado(nombreProducto)
        );
    }

    @Cuando("el mesero elimina una unidad de {string}")
    public void elMeseroEliminaUnaUnidadDe(String nombreProducto) {
        theActorCalled(EL_MESERO).attemptsTo(
                EliminarProducto.llamado(nombreProducto)
        );
    }

    @Cuando("el mesero elimina todas las unidades de {string}")
    public void elMeseroEliminaTodasLasUnidadesDe(String nombreProducto) {
        // Eliminar mientras el producto esté visible en el pedido
        // Máximo 10 intentos para evitar loops infinitos
        int maxIntentos = 10;
        int intentos = 0;
        
        while (intentos < maxIntentos && 
               ElProductoEnElResumen.conNombre(nombreProducto).answeredBy(theActorCalled(EL_MESERO))) {
            theActorCalled(EL_MESERO).attemptsTo(
                    EliminarProducto.llamado(nombreProducto)
            );
            intentos++;
        }
    }

    @Entonces("el pedido debe contener únicamente {string}")
    public void elPedidoDebeContenerUnicamente(String nombreProducto) {
        theActorCalled(EL_MESERO).should(
                seeThat(ElProductoEnElResumen.conNombre(nombreProducto), is(true))
        );
    }

    @Y("{string} no debe aparecer en el resumen")
    public void noDebeAparecerEnElResumen(String nombreProducto) {
        theActorCalled(EL_MESERO).should(
                seeThat(ElProductoNoEstaEnElResumen.llamado(nombreProducto), is(true))
        );
    }

    @Entonces("{string} debe tener cantidad {string}")
    public void debeTenerCantidad(String nombreProducto, String cantidadEsperada) {
        theActorCalled(EL_MESERO).should(
                seeThat(LaCantidadDelProducto.conNombre(nombreProducto), equalTo(cantidadEsperada))
        );
    }

    @Entonces("{string} no debe aparecer en el pedido")
    public void noDebeAparecerEnElPedido(String nombreProducto) {
        theActorCalled(EL_MESERO).should(
                seeThat(ElProductoNoEstaEnElResumen.llamado(nombreProducto), is(true))
        );
    }

    @Y("el pedido debe estar vacío")
    public void elPedidoDebeEstarVacio() {
        theActorCalled(EL_MESERO).should(
                seeThat(ElPedidoEstaVacio.delMesero(), is(true))
        );
    }

    @Entonces("el pedido debe contener {string} y {string}")
    public void elPedidoDebeContenerProductos(String producto1, String producto2) {
        theActorCalled(EL_MESERO).should(
                seeThat(ElPedidoContiene.losProductos(producto1, producto2), is(true))
        );
    }
}
