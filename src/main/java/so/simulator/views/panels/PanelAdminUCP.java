package main.java.so.simulator.views.panels;

import main.java.so.simulator.controllers.Commands;
import main.java.so.simulator.views.Constants;
import main.java.so.simulator.views.components.MyJButton;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelAdminUCP extends JPanel {

    private MyJButton btnStartUCP;
    private MyJButton btnFinishUCP;
    private JLabel timeRest;
    private JLabel timeAssign;
    private JSpinner spinnerTimeAssign;
    private JLabel textTimeRest;
    private JLabel textSegTimeAssign;
    private JLabel textSegTimeRest;

    public PanelAdminUCP(ActionListener listener) {
        this.setBorder(BorderFactory.createTitledBorder(Constants.TITTLE_PANEL_ADMIN_UCP));
        this.btnStartUCP = new MyJButton(listener, Commands.BTN_STAR_UCP, Constants.TEXT_BTN_START_UCP);
        this.btnFinishUCP = new MyJButton(listener, Commands.BTN_FINISH_UCP, Constants.TEXT_BTN_FINISH_UCP);
        this.timeAssign = new JLabel(Constants.TEXT_LABEL_TIME_ASSIGN);
        this.timeRest = new JLabel(Constants.TEXT_LABEl_TIME_REST);
        this.textSegTimeAssign = new JLabel(Constants.TEXT_SEG);
        this.textSegTimeRest = new JLabel(Constants.TEXT_SEG);
        this.spinnerTimeAssign = new JSpinner();
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        spinnerNumberModel.setMinimum(1);
        spinnerNumberModel.setMaximum(300);
        this.spinnerTimeAssign = new JSpinner(spinnerNumberModel);
        spinnerNumberModel.setValue(1);
        this.btnFinishUCP.setEnabled(false);
        this.textTimeRest = new JLabel();
        addToolTips();
        fill();
    }

    private void addToolTips(){
        this.spinnerTimeAssign.setToolTipText("Ingrese el tiempo que desee que la UCP atienda a cada proceso");
        this.btnFinishUCP.setToolTipText("Presione si desea detener la simulacion");
        this.btnStartUCP.setToolTipText("Presione si desea iniciar la simulacion");
    }

    private void fill() {
        this.setLayout(null);
        this.btnStartUCP.setBounds(250, 15, 100, 35);
        this.btnFinishUCP.setBounds(250, 50, 100, 35);
        this.timeAssign.setBounds(10, 20, 110, 20);
        this.timeRest.setBounds(10, 50, 110, 20);
        this.spinnerTimeAssign.setBounds(115, 15, 90, 30);
        this.textTimeRest.setBounds(120, 50, 90, 30);
        this.textSegTimeAssign.setBounds(210, 20, 30, 20);
        this.textSegTimeRest.setBounds(210, 50, 30, 20);
        add(btnStartUCP);
        add(btnFinishUCP);
        add(timeAssign);
        add(timeRest);
        add(spinnerTimeAssign);
        add(textSegTimeAssign);
        add(textSegTimeRest);

        add(textSegTimeAssign);
        add(textTimeRest);
    }

    public void setTimeRestUCP(int time) {
        this.textTimeRest.setText(String.valueOf(time));
    }

    public int getTimeAssignUCP() {
        return (int) spinnerTimeAssign.getValue();
    }

    public int getTimeRestUCP() {
        return Integer.parseInt(this.textTimeRest.getText());
    }

    public void resetComponentsUCP() {
        this.spinnerTimeAssign.setValue(1);
        this.textTimeRest.setText("");
    }

    public void setEnableComponents(boolean status){
        this.spinnerTimeAssign.setEnabled(status);
        this.btnFinishUCP.setEnabled(!status);
        this.setEnabled(status);
        this.btnStartUCP.setEnabled(status);
    }
}
