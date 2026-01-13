package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import ui.ResumenOrdenPage;

/**
 * Pregunta para verificar si el pedido está vacío (no contiene productos).
 * Retorna true si el pedido está vacío, false si contiene al menos un producto.
 */
public class ElPedidoEstaVacio implements Question<Boolean> {

    private ElPedidoEstaVacio() {
    }

    public static ElPedidoEstaVacio delMesero() {
        return new ElPedidoEstaVacio();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        // El pedido está vacío si el total de items es 0 o cadena vacía
        String totalItemsStr = ElTotalDeItems.enLaOrden().answeredBy(actor);
        
        // Si la cadena está vacía o es "0", el pedido está vacío
        if (totalItemsStr == null || totalItemsStr.trim().isEmpty()) {
            return true;
        }
        
        try {
            Integer totalItems = Integer.parseInt(totalItemsStr);
            return totalItems == 0;
        } catch (NumberFormatException e) {
            // Si no se puede parsear, consideramos que está vacío
            return true;
        }
    }
}
