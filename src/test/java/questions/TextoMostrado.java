package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class TextoMostrado implements Question<String> {

    private final Target elementoConTexto;

    public TextoMostrado(Target elementoConTexto) {
        this.elementoConTexto = elementoConTexto;
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(elementoConTexto).answeredBy(actor);
    }

    public static TextoMostrado textoMostrado(Target elementoConTexto){
        return new TextoMostrado(elementoConTexto);
    }
}