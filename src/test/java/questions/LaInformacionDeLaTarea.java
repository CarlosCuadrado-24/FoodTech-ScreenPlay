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
        switch (tipoInformacion) {
            case "MESA":
                String textoMesa = Text.of(EstacionCocinaPage.NUMERO_MESA_TAREA).answeredBy(actor);
                return textoMesa != null && textoMesa.contains(valorEsperado);
                
            case "ORDEN":
                return Visibility.of(EstacionCocinaPage.ID_ORDEN_TAREA).answeredBy(actor);
                
            case "PRODUCTOS":
                int cantidadProductos = EstacionCocinaPage.PRODUCTOS_TAREA
                        .resolveAllFor(actor).get(0)
                        .findElements(org.openqa.selenium.By.cssSelector("[data-testid^='task-product-']"))
                        .size();
                return cantidadProductos == Integer.parseInt(valorEsperado);
                
            case "FECHA":
                // Verificar que existe algún elemento de tiempo/fecha en la tarea
                return Visibility.of(EstacionCocinaPage.TARJETA_TAREA.of("1")).answeredBy(actor);
                
            default:
                return false;
        }
    }
}
