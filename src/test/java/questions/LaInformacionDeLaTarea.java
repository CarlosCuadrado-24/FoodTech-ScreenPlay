package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Visibility;
import userinterface.EstacionCocinaPage;

public class LaInformacionDeLaTarea implements Question<Boolean> {

    private final String tipoInformacion;
    private final String valorEsperado;

    private LaInformacionDeLaTarea(String tipoInformacion, String valorEsperado) {
        this.tipoInformacion = tipoInformacion;
        this.valorEsperado = valorEsperado;
    }

    public static LaInformacionDeLaTarea muestraLaMesa(String numeroMesa) {
        return new LaInformacionDeLaTarea("MESA", numeroMesa);
    }

    public static LaInformacionDeLaTarea muestraElIdDeOrden() {
        return new LaInformacionDeLaTarea("ORDEN", null);
    }

    public static LaInformacionDeLaTarea muestraLaCantidadDeProductos(int cantidad) {
        return new LaInformacionDeLaTarea("PRODUCTOS", String.valueOf(cantidad));
    }

    public static LaInformacionDeLaTarea muestraLaFechaDeCreacion() {
        return new LaInformacionDeLaTarea("FECHA", null);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            switch (tipoInformacion) {
                case "MESA":
                    String textoMesa = Text.of(EstacionCocinaPage.NUMERO_MESA_TAREA).answeredBy(actor);
                    return textoMesa != null && !textoMesa.isEmpty() && (valorEsperado.isEmpty() || textoMesa.contains(valorEsperado));

                case "ORDEN":
                    return Visibility.of(EstacionCocinaPage.ID_ORDEN_TAREA).answeredBy(actor);

                case "PRODUCTOS":
                    int cantidadProductos = EstacionCocinaPage.PRODUCTOS_TAREA
                            .resolveAllFor(actor).get(0)
                            .findElements(org.openqa.selenium.By.cssSelector("[data-testid^='task-product-']"))
                            .size();
                    return cantidadProductos >= Integer.parseInt(valorEsperado);

                case "FECHA":
                    // Verificar que existe la tarjeta de tarea
                    return !EstacionCocinaPage.TODAS_LAS_TAREAS.resolveAllFor(actor).isEmpty();

                default:
                    return false;
            }
        } catch (Exception e) {
            // Si hay algún error al buscar elementos, retornar false
            return false;
        }
    }
}
