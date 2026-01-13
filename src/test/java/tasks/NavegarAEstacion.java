package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavegarAEstacion implements Task {

    private final String estacion;

    private NavegarAEstacion(String estacion) {
        this.estacion = estacion;
    }

    public static NavegarAEstacion llamada(String estacion) {
        return new NavegarAEstacion(estacion);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String url = obtenerUrlPorEstacion(estacion);
        actor.attemptsTo(
                Open.url(url)
        );
    }

    private String obtenerUrlPorEstacion(String estacion) {
        switch (estacion.toUpperCase()) {
            case "BARRA":
            case "BAR":
                return "http://localhost:5173/bar";
            case "COCINA CALIENTE":
            case "HOT_KITCHEN":
                return "http://localhost:5173/hot-kitchen";
            case "COCINA FRÍA":
            case "COLD_KITCHEN":
                return "http://localhost:5173/cold-kitchen";
            default:
                return "http://localhost:5173";
        }
    }
}
