package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import ui.ResumenOrdenPage;

/**
 * Tarea para eliminar un producto del pedido antes de enviarlo.
 * Hace click en el botón eliminar del producto especificado.
 * 
 * Principio SRP: Solo hace una cosa - eliminar un producto del resumen
 */
public class EliminarProducto implements Task {

    private final String nombreProducto;

    public EliminarProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public static EliminarProducto llamado(String nombreProducto) {
        return Tasks.instrumented(EliminarProducto.class, nombreProducto);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Convertir nombre del producto a formato kebab-case para el data-testid
        String productoKebabCase = nombreProducto
                .toLowerCase()
                .replaceAll("\\s+", "-");

        actor.attemptsTo(
                Click.on(ResumenOrdenPage.BOTON_ELIMINAR_PRODUCTO.of(productoKebabCase))
        );
    }
}
