package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import static userinterface.PaginaCargaDinamica.BOTON_INICIO;

public class EsperarCargaDinamica implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        
        actor.attemptsTo(
                Click.on(BOTON_INICIO)
        );
    }

    public static EsperarCargaDinamica hacerClicEnBotonInicio(){
        return Tasks.instrumented(EsperarCargaDinamica.class);
    }
}