package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import userinterface.EstacionCocinaPage;

public class NoHayTareasPendientes implements Question<Boolean> {

    public static NoHayTareasPendientes enLaEstacion() {
        return new NoHayTareasPendientes();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        boolean noHayTareas = EstacionCocinaPage.TODAS_LAS_TAREAS.resolveAllFor(actor).isEmpty();
        boolean hayMensaje = Visibility.of(EstacionCocinaPage.MENSAJE_SIN_TAREAS).answeredBy(actor);
        
        return noHayTareas || hayMensaje;
    }
}
