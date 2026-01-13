package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import ui.ResumenOrdenPage;

/**
 * Pregunta para obtener el total de items en la orden.
 * Retorna el número como String.
 */
public class ElTotalDeItems implements Question<String> {

    private ElTotalDeItems() {
    }

    public static ElTotalDeItems enLaOrden() {
        return new ElTotalDeItems();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ResumenOrdenPage.TOTAL_ITEMS)
                .answeredBy(actor)
                .trim();
    }
}
