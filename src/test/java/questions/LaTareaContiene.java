package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import userinterface.EstacionCocinaPage;

public class LaTareaContiene implements Question<Boolean> {

    private final String nombreProducto;

    private LaTareaContiene(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public static LaTareaContiene elProducto(String nombreProducto) {
        return new LaTareaContiene(nombreProducto);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Visibility.of(EstacionCocinaPage.PRODUCTO_CON_NOMBRE.of(nombreProducto))
                .answeredBy(actor);
    }
}
