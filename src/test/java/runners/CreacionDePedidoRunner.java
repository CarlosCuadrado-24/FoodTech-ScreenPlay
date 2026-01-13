package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner para ejecutar los tests de la HU-QA-001.
 * Ejecuta los escenarios definidos en el feature HU_QA_001_CreacionDePedido.feature
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/HU_QA_001_CreacionDePedido.feature",
        glue = {"stepdefinitions", "stepdefinitions.hook"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        tags = "not @wip"
)
public class CreacionDePedidoRunner {
}
