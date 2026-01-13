package userinterface;

import net.serenitybdd.screenplay.targets.Target;

public class EstacionCocinaPage {

    public static final Target TARJETA_TAREA = Target.the("tarjeta de tarea {0}")
            .locatedBy("[data-task-id='{0}']");

    public static final Target NUMERO_MESA_TAREA = Target.the("número de mesa de la tarea")
            .locatedBy("[data-testid='task-table-number']");

    public static final Target ESTADO_TAREA = Target.the("estado de la tarea")
            .locatedBy("[data-testid='task-status-badge']");

    public static final Target ID_ORDEN_TAREA = Target.the("ID de orden de la tarea")
            .locatedBy("[data-testid='task-order-id']");

    public static final Target PRODUCTOS_TAREA = Target.the("productos de la tarea")
            .locatedBy("[data-testid='task-products']");

    public static final Target PRODUCTO_EN_TAREA = Target.the("producto en posición {0} de la tarea")
            .locatedBy("[data-testid='task-product-{0}']");

    public static final Target TODAS_LAS_TAREAS = Target.the("todas las tarjetas de tareas")
            .locatedBy("[data-testid^='task-card-']");

    public static final Target TAREAS_POR_ESTACION = Target.the("tareas de la estación {0}")
            .locatedBy("[data-station='{0}']");

    public static final Target MENSAJE_SIN_TAREAS = Target.the("mensaje de sin tareas pendientes")
            .locatedBy("[data-testid='empty-tasks-message']");

    public static final Target PRODUCTO_CON_NOMBRE = Target.the("producto con nombre {0}")
            .locatedBy("[data-product-name='{0}']");

    public static final Target BOTON_INICIAR_TAREA = Target.the("botón para iniciar tarea {0}")
            .locatedBy("[data-testid='start-task-btn-{0}']");
}
