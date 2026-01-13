package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Alert;
import ui.ResumenOrdenPage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

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
                WaitUntil.the(ResumenOrdenPage.BOTON_ENVIAR, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(ResumenOrdenPage.BOTON_ENVIAR)
        );
        
        // Manejar el alert de confirmación
        try {
            Thread.sleep(1000); // Pequeña espera para que aparezca el alert
            Alert alert = BrowseTheWeb.as(actor).getDriver().switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            // Si no hay alert, continuamos
        }
    }
}
