package main.java.so.simulator.views;

import java.awt.*;

public interface Constants {

    String ICON_PATH = "src/main/resources/SimulatorIcon.png";
    String TITTLE = "Simulador: Transiciones de procesos";
    Dimension SIZE = new Dimension(800, 640);

    String TITTLE_PANEL_ADMIN_UCP = " Administracion UCP ";
    String TITTLE_PANEL_CREATION_PROCESS = " Creacion de proceso ";
    String TEXT_LABEL_TIME_ASSIGN = "Tiempo asignado: ";
    String TEXT_LABEl_TIME_REST = "Tiempo restante: ";
    String TEXT_SEG = "Seg.";
    String TEXT_BTN_START_UCP = "Iniciar";
    String TEXT_BTN_FINISH_UCP = "Finalizar";
    String NO_HAY_MAS_PROCESOS_POR_EJECUTAR = "No hay mas procesos por ejecutar";
    Font FONT_LIST = new Font("Arial", Font.PLAIN, 15);
    String TEXT_BTN_CREATE_PROCESS = "Crear";
    String TEXT_LABEL_NAME_PROCESS = "Nombre:";
    String TEXT_LABEL_TIME_PROCESS = "Tiempo: ";
    String PRESIONE_PARA_CREAR_UN_PROCESO_CON_LOS_DATOS_INGRESADOS = "Presione para crear un proceso con los datos ingresados";
    String INGRESE_EL_TIEMPO_DE_EJECUCION_DEL_PROCESO = "Ingrese el tiempo de ejecucion del proceso";
    String TOOL_TIP_BTN_WAKE_PROCESS = "Presione para despertar el proceso selecionado de la lista de procesos bloqueados";
    String TOOL_TIP_BTN_STOP_PROCESS = "Presione si desea detener el proceso en ejecucion";
}

