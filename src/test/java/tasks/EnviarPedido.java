package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import ui.ResumenOrdenPage;

/**
 * Tarea para enviar el pedido a cocina.
 * Representa la intención de negocio: "enviar pedido"
 */
public class EnviarPedido implements Task {

    public EnviarPedido() {
    }

    public static EnviarPedido aLaCocina() {
        return Tasks.instrumented(EnviarPedido.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ResumenOrdenPage.BOTON_ENVIAR)
        );
    }
}
