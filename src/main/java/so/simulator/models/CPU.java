package main.java.so.simulator.models;

import main.java.so.simulator.models.exceptions.CPUException;
import main.java.so.simulator.models.exceptions.ErrorCode;
import main.java.so.util.observer.Observable;
import main.java.so.util.observer.Observer;
import main.java.so.util.observer.ObserverEvent;

import java.util.concurrent.TimeUnit;

public class CPU implements Runnable {

    private final Observable observable = new Observable();
    public static final int TIME_STEP = 1;

    private boolean blockProcess;

    private int executionTime;
    private int executionTimeRemaining;

    private Process processRunning;

    public CPU(Observer observer) {
        observable.addObserver(observer);
    }

    public CPU(int executionTime) {
        this.executionTime = executionTime;
    }

    public void runProcess(Process process) throws CPUException {
        if (process == null) throw new CPUException(ErrorCode.NO_ASSIGNED_PROCESS);
        processRunning = process;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Proceso ejecutandose: " + processRunning.getProcessName());
        try {
            while (processRunning.isAlive() && hasTime() && !blockProcess) {
                //Resta el tiempo de ejecución para el proceso actual
                executionTimeRemaining -= TIME_STEP;
                //ejecuta el proceso el tiempo asignado
                processRunning.run(TIME_STEP);
                //duerme el hilo segundos
                TimeUnit.SECONDS.sleep(TIME_STEP);
                //notifica a los observadores que actualicen la vista
                observable.notify(ObserverEvent.UPDATE_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (blockProcess){
            blockProcess = false;
            processRunning.blockProcess();
            observable.notify(ObserverEvent.BLOCK);
        }else if (processRunning.isAlive()){
            observable.notify(ObserverEvent.TIME_EXPIRATION);
        }else {
            observable.notify(ObserverEvent.BLOCK);
        }
    }

    private boolean hasTime() {
        return executionTimeRemaining > 0;
    }

    public void addObserver(Observer observer) {
        observable.addObserver(observer);
    }

    public Process reset(){
        executionTimeRemaining = executionTime;
        return liberateCPU();
    }

    public Process liberateCPU(){
        Process process = processRunning;
        processRunning = null;
        return process;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getExecutionTimeRemaining() {
        return executionTimeRemaining;
    }

    public Process getProcessRunning() {
        return processRunning;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isFree(){
        return processRunning == null;
    }

    public void blockProcess(){
        blockProcess = true;
    }
}
