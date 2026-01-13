package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import ui.MesasPage;

/**
 * Pregunta para obtener el estado de una mesa.
 * Retorna el texto del estado (ej: "Disponible", "Ocupada")
 */
public class ElEstadoDeLaMesa implements Question<String> {

    private final String numeroMesa;

    private ElEstadoDeLaMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public static ElEstadoDeLaMesa conNumero(String numeroMesa) {
        return new ElEstadoDeLaMesa(numeroMesa);
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(MesasPage.ESTADO_MESA.of(numeroMesa))
                .answeredBy(actor)
                .trim();
    }
}
