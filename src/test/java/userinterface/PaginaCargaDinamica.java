package userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class PaginaCargaDinamica extends PageObject {

    public static final Target BOTON_INICIO = Target.the("Botón para iniciar la carga dinámica")
            .locatedBy("//button[text()='Start']");

    public static final Target MENSAJE_FINAL = Target.the("Mensaje que aparece después de la carga")
            .locatedBy("//div[@id='finish']/h4");


}