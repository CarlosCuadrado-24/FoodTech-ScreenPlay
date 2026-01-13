package ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Page Object para el panel de resumen de orden.
 * Contiene únicamente los locators (Target) sin lógica de negocio.
 */
public class ResumenOrdenPage {

    /**
     * Panel completo del resumen de orden
     */
    public static Target PANEL_RESUMEN = Target
            .the("panel de resumen de orden")
            .locatedBy("[data-testid='order-summary']");

    /**
     * Badge que indica que hay una orden activa
     */
    public static Target BADGE_ORDEN_ACTIVA = Target
            .the("badge de orden activa")
            .locatedBy("[data-testid='order-active-badge']");

    /**
     * Lista completa de productos en la orden
     */
    public static Target LISTA_PRODUCTOS = Target
            .the("lista de productos en la orden")
            .locatedBy("[data-testid='order-products-list']");

    /**
     * Item individual en la orden por nombre de producto
     */
    public static Target ITEM_ORDEN = Target
            .the("item {0} en la orden")
            .locatedBy("[data-testid='order-item-{0}']");

    /**
     * Nombre del producto dentro del item de orden (parametrizado por producto)
     */
    public static Target NOMBRE_EN_ORDEN = Target
            .the("nombre del producto {0} en orden")
            .locatedBy("//div[@data-testid='order-item-{0}']//span[@data-testid='order-item-name']");

    /**
     * Cantidad del producto en la orden (ej: "x2")
     */
    public static Target CANTIDAD_EN_ORDEN = Target
            .the("cantidad del producto {0} en orden")
            .locatedBy("//div[@data-testid='order-item-{0}']//span[@data-testid='order-item-quantity']");

    /**
     * Botón para eliminar un producto del pedido (aparece al hacer hover)
     */
    public static Target BOTON_ELIMINAR_PRODUCTO = Target
            .the("botón eliminar producto {0}")
            .locatedBy("[data-testid='remove-product-btn-{0}']");

    /**
     * Contenedor del total de items
     */
    public static Target CONTENEDOR_TOTAL = Target
            .the("contenedor del total")
            .locatedBy("[data-testid='order-total']");

    /**
     * Contador del total de items en la orden
     */
    public static Target TOTAL_ITEMS = Target
            .the("total de items en la orden")
            .locatedBy("[data-testid='total-items-count']");

    /**
     * Botón para enviar el pedido a cocina
     */
    public static Target BOTON_ENVIAR = Target
            .the("botón enviar pedido a cocina")
            .locatedBy("[data-testid='submit-order-btn']");
}
