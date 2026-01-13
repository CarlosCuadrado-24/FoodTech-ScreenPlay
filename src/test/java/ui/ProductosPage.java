package ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Page Object para la gestión de productos del menú.
 * Contiene únicamente los locators (Target) sin lógica de negocio.
 */
public class ProductosPage {

    /**
     * Tarjeta completa de un producto específico
     */
    public static Target TARJETA_PRODUCTO = Target
            .the("tarjeta del producto {0}")
            .locatedBy("[data-testid='product-card-{0}']");

    /**
     * Nombre del producto en la tarjeta
     */
    public static Target NOMBRE_PRODUCTO = Target
            .the("nombre del producto")
            .locatedBy("[data-testid='product-name']");

    /**
     * Badge que indica que el producto fue agregado
     */
    public static Target BADGE_AGREGADO = Target
            .the("badge de producto agregado en {0}")
            .locatedBy("[data-testid='product-card-{0}'] [data-testid='product-added-badge']");

    /**
     * Botón para agregar un producto al pedido
     */
    public static Target BOTON_AGREGAR_PRODUCTO = Target
            .the("botón agregar producto {0}")
            .locatedBy("[data-testid='add-product-btn-{0}']");
}
