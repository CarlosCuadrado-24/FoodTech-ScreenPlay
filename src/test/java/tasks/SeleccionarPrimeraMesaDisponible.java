package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Tarea para seleccionar la primera mesa disponible del sistema.
 * Busca dinámicamente una mesa con estado "Disponible" y la selecciona.
 */
public class SeleccionarPrimeraMesaDisponible implements Task {

    public SeleccionarPrimeraMesaDisponible() {
    }

    public static SeleccionarPrimeraMesaDisponible delSistema() {
        return Tasks.instrumented(SeleccionarPrimeraMesaDisponible.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // XPath que busca la primera mesa con estado "Disponible"
        Target primeraMesaDisponible = Target
                .the("primera mesa disponible")
                .located(By.xpath("//div[contains(@data-testid, 'table-card-')]//span[contains(@data-testid, 'table-status-')][text()='Disponible']/ancestor::div[contains(@data-testid, 'table-card-')]"));

        actor.attemptsTo(
                Click.on(primeraMesaDisponible)
        );
    }
}
