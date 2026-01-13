package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

/**
 * Tarea para navegar a la página de gestión de pedidos.
 * Representa la acción de negocio: "acceder al sistema"
 */
public class Navegar implements Task {

    private final String url;

    public Navegar(String url) {
        this.url = url;
    }

    public static Navegar alSistemaDeGestionDePedidos() {
        return Tasks.instrumented(Navegar.class, "http://localhost:5173");
    }

    public static Navegar aLaUrl(String url) {
        return Tasks.instrumented(Navegar.class, url);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(url)
        );
    }
}
