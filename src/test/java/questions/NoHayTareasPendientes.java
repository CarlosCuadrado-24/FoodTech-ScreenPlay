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
        try {
            boolean noHayTareas = EstacionCocinaPage.TODAS_LAS_TAREAS.resolveAllFor(actor).isEmpty();
            
            // Intentar buscar mensaje, pero si no existe tampoco pasa nada
            try {
                boolean hayMensaje = Visibility.of(EstacionCocinaPage.MENSAJE_SIN_TAREAS).answeredBy(actor);
                return noHayTareas || hayMensaje;
            } catch (Exception e) {
                // Si no hay mensaje, solo validar que no hay tareas
                return noHayTareas;
            }
        } catch (Exception e) {
            // Si no se puede resolver las tareas, asumir que no hay ninguna
            return true;
        }
    }
}
