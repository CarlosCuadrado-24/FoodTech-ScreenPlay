package userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaCargaDinamica {

    public static final Target BOTON_INICIO = Target.the("Botón de inicio")
            .located(By.id("start"));

    public static final Target MENSAJE_FINAL = Target.the("Mensaje final")
            .located(By.id("finish"));
}
