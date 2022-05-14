package main.java.so.simulator.models;

public class Process {

    //Contador para los nombres de los procesos
    private static int sequential;

    private String processName;
    private int secondsOfExecution;//Segundos que el proceso va estar en ejecucuión
    private int secondsOfExecutionRemaining;//Segundos que le quedan al proceso del tiempo de ejecucucion

    /**
     * Crea un nuevo proceso y le asigna su tiempo de ejecución
     *
     * @param secondsOfExecution tiempo de ejecución en segundos
     */
    public Process(int secondsOfExecution) {
        this.processName = String.valueOf(sequential);
        this.secondsOfExecution = secondsOfExecution;
        secondsOfExecutionRemaining = secondsOfExecution;
        sequential++;
    }

    public void run(int time) {
        if (time > secondsOfExecution) {
            secondsOfExecutionRemaining = 0;
        } else {
            secondsOfExecutionRemaining -= time;
        }
    }

    public void wakeUp() {
        secondsOfExecutionRemaining = secondsOfExecution;
    }

    public boolean isAlive() {
        return secondsOfExecutionRemaining > 0;
    }

    public void blockProcess() {
        secondsOfExecutionRemaining = 0;
    }

    public String getProcessName() {
        return processName;
    }

    public int getSecondsOfExecution() {
        return secondsOfExecution;
    }

    public int getSecondsOfExecutionRemaining() {
        return secondsOfExecutionRemaining;
    }

    public static void resetSequential(){
        sequential = 0;
    }
}
