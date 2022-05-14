package main.java.so.simulator.views.panels;

import main.java.so.simulator.controllers.Commands;
import main.java.so.simulator.views.Constants;
import main.java.so.simulator.views.components.MyJButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;

public class PanelProcessExecution extends JPanel {

    private ActionListener listener;
    private JLabel labelNameProcess;
    private JLabel labelTimeAssign;
    private JLabel labelTimeRest;
    private JLabel textFieldNameProcess;
    private JLabel textFieldTimeAssign;
    private JLabel textFieldTimeRest;
    private MyJButton btnStopProcess;

    public PanelProcessExecution(ActionListener listener) {
        this.listener = listener;
        setBorder(BorderFactory.createTitledBorder(null, "Proceso en ejecucion", TitledBorder.CENTER, TitledBorder.TOP));
        init();
    }

    private void init() {
        this.labelNameProcess = new JLabel(Constants.TEXT_LABEL_NAME_PROCESS);
        this.labelTimeAssign = new JLabel("Tiempo asignado: ");
        this.labelTimeRest = new JLabel("Tiempo restante: ");
        this.textFieldNameProcess = new JLabel();
        this.textFieldTimeAssign = new JLabel();
        this.textFieldTimeRest = new JLabel();
        this.btnStopProcess = new MyJButton(listener, Commands.BTN_STOP_PROCESS, "Detener");
        this.btnStopProcess.setEnabled(false);
        addToolTips();
        this.fill();
    }

    public void setEnableBtnStopProcess(boolean status){
        this.btnStopProcess.setEnabled(status);
    }
    
    public void setProcessActual(String nameProcessActual, int timeAssign, int timeRest) {
        this.textFieldNameProcess.setText(nameProcessActual);
        this.textFieldTimeAssign.setText(String.valueOf(timeAssign));
        setTimeRest(timeRest);
    }

    public void setTimeRest(int timeRest){
        this.textFieldTimeRest.setText(String.valueOf(timeRest));
    }

    public void setEnableComponents(boolean status){
        this.setEnabled(status);
        this.btnStopProcess.setEnabled(status);
    }

    public void resetComponents(){
        this.textFieldTimeAssign.setText("0");
        this.textFieldTimeRest.setText("0");
        this.textFieldNameProcess.setText("0");
    }

    private void addToolTips(){
        this.btnStopProcess.setToolTipText(Constants.TOOL_TIP_BTN_STOP_PROCESS);
    }

    private void fill() {
        this.setLayout(null);
        this.labelNameProcess.setBounds(20, 40, 120, 20);
        this.labelTimeAssign.setBounds(20, 70, 120, 20);
        this.labelTimeRest.setBounds(20, 100, 120, 20);
        this.textFieldNameProcess.setBounds(170, 40, 120, 25);
        this.textFieldTimeAssign.setBounds(170, 70, 120, 25);
        this.textFieldTimeRest.setBounds(170, 100, 120, 25);
        this.btnStopProcess.setBounds(50, 210, 150, 40);
        add(labelNameProcess);
        add(labelTimeAssign);
        add(labelTimeRest);
        add(textFieldNameProcess);
        add(textFieldTimeAssign);
        add(textFieldTimeRest);
        add(btnStopProcess);
    }
}
