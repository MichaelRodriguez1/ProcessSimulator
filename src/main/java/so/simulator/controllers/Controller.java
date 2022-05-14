package main.java.so.simulator.controllers;

import main.java.so.simulator.models.Process;
import main.java.so.simulator.models.ProcessStateManager;
import main.java.so.simulator.models.exceptions.CPUException;
import main.java.so.simulator.views.Constants;
import main.java.so.simulator.views.GuiManager;
import main.java.so.simulator.views.components.Output;
import main.java.so.util.observer.Observer;
import main.java.so.util.observer.ObserverEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener, Observer {

    private GuiManager guiManager;
    private ProcessStateManager stateManager;

    public Controller() {
        this.guiManager = new GuiManager(this);
        this.stateManager = new ProcessStateManager(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case Commands.BTN_STAR_UCP:
                int timeAssign = guiManager.getTimeAssignUCP();
                stateManager.setCpuExecuteTime(timeAssign);
                guiManager.setEnableLists(true);
                guiManager.setEnablePanelAdminUCP(false);
                guiManager.setEnableLists(true);
                guiManager.setEnablePanelCreateProcess(true);
                guiManager.setEnableBtnWakeProcess(true);
                break;
            case Commands.BTN_CREATE_PROCESS:
                createProcess();
                guiManager.resetSpinnerPanelCreateProcess();
                guiManager.setEnablePanelProcessExecution(true);
                guiManager.sumCountName();
                break;
            case Commands.BTN_FINISH_UCP:
                stateManager.blockActualProcess();
                Process.resetSequential();
                Output.showInfoMessage("Simulacion finalizada.");
                stateManager = new ProcessStateManager(this);
                guiManager.setEnablePanelAdminUCP(true);
                guiManager.setEnablePanelCreateProcess(false);
                guiManager.setEnableLists(false);
                guiManager.setEnableBtnWakeProcess(false);
                guiManager.setEnablePanelProcessExecution(false);
                guiManager.resetSpinnerUCP();
                guiManager.resetComponentsPanelCurrentProcess();
                guiManager.clearLists();
                break;
            case Commands.BTN_WAKE_PROCESS:
                wakeProcess();
                break;
            case Commands.BTN_STOP_PROCESS:
                stateManager.blockActualProcess();
                break;
        }
    }

    private void wakeProcess() {
        String selected = guiManager.getSelectItem();
        stateManager.wakeUpProcess(selected);
        if (stateManager.hasCPUAvailable()) {
            try {
                stateManager.dispatchNextProcess();
                updateProcessView();
            } catch (CPUException e) {
                e.printStackTrace();
            }
        }
        updateListAndQueue();
    }

    private void createProcess() {
        int time = guiManager.getTimeNewProcess();
        if (stateManager.hasCPUAvailable()) {
            try {
                stateManager.addProcess(time);
                stateManager.dispatchNextProcess();
            } catch (CPUException e) {
                e.printStackTrace();
            }
        } else {
            stateManager.addProcess(time);
        }
        guiManager.updateReadyQueue(stateManager.getReadyQueue());
    }

    @Override
    public void update(ObserverEvent event) {
        switch (event) {
            case UPDATE_TIME:
                updateTime();
                break;
            case BLOCK:
                blockProcess();
                break;
            case TIME_EXPIRATION:
                nextProcess();
                break;
            case PROCESS_CHANGED:
                updateProcessView();
                break;
        }
    }

    /**
     * Actualiza los tiempos restantes de ejecución
     */
    private void updateTime() {
        int ucpTime = stateManager.getCPUTimeRemaining();
        guiManager.setTimeRestUCP(ucpTime);
        int processTime = stateManager.getProcessTimeRemaining();
        guiManager.setTimeRestProcess(processTime);
    }

    private void blockProcess() {
        //Bloquea el proceso de la UCP
        stateManager.blockProcess();
        try {
            if (stateManager.hasProcessesReady()) {
                stateManager.dispatchNextProcess();
                updateProcessView();
            } else {
                guiManager.resetComponentsPanelCurrentProcess();
                Output.showInfoMessage(Constants.NO_HAY_MAS_PROCESOS_POR_EJECUTAR);
            }
        } catch (CPUException e) {
            e.printStackTrace();
        }
        updateListAndQueue();
    }

    private void updateProcessView() {
        if (!stateManager.hasCPUAvailable()) {
            Process process = stateManager.getRunningProcess();
            guiManager.setProcessActual(
                    process.getProcessName(),
                    process.getSecondsOfExecution(),
                    process.getSecondsOfExecutionRemaining());
        }
    }

    private void nextProcess() {
        try {
            Process process = stateManager.finishProcessTurn();
            guiManager.setProcessActual(
                    process.getProcessName(),
                    process.getSecondsOfExecution(),
                    process.getSecondsOfExecutionRemaining()
            );
            guiManager.updateReadyQueue(stateManager.getReadyQueue());
        } catch (CPUException e) {
            e.printStackTrace();
        }
    }

    private void updateListAndQueue() {
        guiManager.updateBlockedList(stateManager.getBlockedList());
        guiManager.updateReadyQueue(stateManager.getReadyQueue());
    }
}
