package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import userinterface.EstacionCocinaPage;

public class LaCantidadDeTareas implements Question<Integer> {

    private final String estacion;

    private LaCantidadDeTareas(String estacion) {
        this.estacion = estacion;
    }

    public static LaCantidadDeTareas enLaEstacion(String estacion) {
        return new LaCantidadDeTareas(estacion);
    }

    public static LaCantidadDeTareas visibles() {
        return new LaCantidadDeTareas(null);
    }

    @Override
    public Integer answeredBy(Actor actor) {
        try {
            // Esperar un momento para que las tareas se carguen
            Thread.sleep(2000);
            
            if (estacion == null) {
                return EstacionCocinaPage.TODAS_LAS_TAREAS.resolveAllFor(actor).size();
            }

            String codigoEstacion = obtenerCodigoEstacion(estacion);
            return EstacionCocinaPage.TAREAS_POR_ESTACION.of(codigoEstacion).resolveAllFor(actor).size();
        } catch (Exception e) {
            // Si no se encuentran elementos, retornar 0 en lugar de fallar
            return 0;
        }
    }

    private String obtenerCodigoEstacion(String estacion) {
        switch (estacion.toUpperCase()) {
            case "BARRA":
                return "BAR";
            case "COCINA CALIENTE":
                return "HOT_KITCHEN";
            case "COCINA FRÍA":
                return "COLD_KITCHEN";
            default:
                return estacion.toUpperCase();
        }
    }
}
