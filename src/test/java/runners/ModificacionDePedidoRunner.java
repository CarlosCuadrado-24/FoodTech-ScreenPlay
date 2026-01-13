package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner para ejecutar los tests de la HU-QA-002.
 * Ejecuta los escenarios definidos en el feature HU_QA_002_ModificacionDePedidos.feature
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/HU_QA_002_ModificacionDePedidos.feature",
        glue = {"stepdefinitions", "stepdefinitions.hook"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ModificacionDePedidoRunner {
}
