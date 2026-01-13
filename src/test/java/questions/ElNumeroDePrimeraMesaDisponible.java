package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Pregunta para obtener el número de la primera mesa disponible.
 * Busca dinámicamente una mesa con estado "Disponible" y retorna su número.
 */
public class ElNumeroDePrimeraMesaDisponible implements Question<String> {

    private ElNumeroDePrimeraMesaDisponible() {
    }

    public static ElNumeroDePrimeraMesaDisponible enElSistema() {
        return new ElNumeroDePrimeraMesaDisponible();
    }

    @Override
    public String answeredBy(Actor actor) {
        Target primeraMesaDisponible = Target
                .the("número de primera mesa disponible")
                .located(By.xpath("//div[contains(@data-testid, 'table-card-')]//span[contains(@data-testid, 'table-status-')][text()='Disponible']/preceding-sibling::span[contains(@data-testid, 'table-number-')]"));

        return primeraMesaDisponible.resolveFor(actor).getText().trim();
    }
}
