package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import ui.MesasPage;

/**
 * Tarea para seleccionar una mesa en el sistema.
 * Representa la intención de negocio: "seleccionar una mesa"
 */
public class SeleccionarMesa implements Task {

    private final String numeroMesa;

    public SeleccionarMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public static SeleccionarMesa conNumero(String numeroMesa) {
        return Tasks.instrumented(SeleccionarMesa.class, numeroMesa);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(MesasPage.TARJETA_MESA.of(numeroMesa))
        );
    }
}
