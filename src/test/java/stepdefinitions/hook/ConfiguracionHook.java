package stepdefinitions.hook;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.WebDriverFacade;

/**
 * Hook para configurar el escenario antes de cada test.
 * Configura el actor que ejecutará las acciones con la habilidad de navegar web.
 */
public class ConfiguracionHook {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }
}
