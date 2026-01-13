package ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Page Object para la gestión de mesas del restaurante.
 * Contiene únicamente los locators (Target) sin lógica de negocio.
 */
public class MesasPage {

    /**
     * Tarjeta completa de una mesa específica
     * @param numeroMesa Número de la mesa (ej: "A1")
     */
    public static Target TARJETA_MESA = Target
            .the("tarjeta de la mesa {0}")
            .locatedBy("[data-testid='table-card-{0}']");

    /**
     * Número visible de la mesa
     */
    public static Target NUMERO_MESA = Target
            .the("número de la mesa {0}")
            .locatedBy("[data-testid='table-number-{0}']");

    /**
     * Estado de la mesa (Disponible/Ocupada)
     */
    public static Target ESTADO_MESA = Target
            .the("estado de la mesa {0}")
            .locatedBy("[data-testid='table-status-{0}']");
}
