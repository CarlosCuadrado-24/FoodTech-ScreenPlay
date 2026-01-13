package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import ui.ProductosPage;

/**
 * Tarea para agregar un producto al pedido.
 * Representa la intención de negocio: "agregar producto"
 */
public class AgregarProducto implements Task {

    private final String nombreProducto;

    public AgregarProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public static AgregarProducto conNombre(String nombreProducto) {
        return Tasks.instrumented(AgregarProducto.class, nombreProducto);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Convertir nombre del producto a formato del selector
        // Ej: "Gin Tonic Premium" -> "gin-tonic-premium"
        String selectorProducto = nombreProducto
                .toLowerCase()
                .replaceAll("\\s+", "-");

        actor.attemptsTo(
                Click.on(ProductosPage.BOTON_AGREGAR_PRODUCTO.of(selectorProducto))
        );
    }
}
