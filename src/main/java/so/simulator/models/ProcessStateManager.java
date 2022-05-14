package main.java.so.simulator.models;

import main.java.so.simulator.models.exceptions.CPUException;
import main.java.so.simulator.models.exceptions.ErrorCode;
import main.java.so.util.observer.Observer;
import main.java.so.util.queue.Queue;

import java.util.ArrayList;
import java.util.Comparator;

public class ProcessStateManager {

    private ArrayList<Process> blockedList;
    private Queue<Process> readyQueue;
    private CPU cpu;

    public ProcessStateManager(Observer observer) {
        //crea la lista de bloqueados
        blockedList = new ArrayList<>();

        //Crea una cola que compara los procesos por su nombre
        readyQueue = new Queue<>(Comparator.comparing(Process::getProcessName));

        //Crea la el objeto encargado de manejar el turno de uso de la CPU
        cpu = new CPU(observer);
    }

    /**
     * Obtiene la lista de los nombres de los procesos
     * que se encuentran en la cola de espera
     * @return lista de nombres de procesos
     */
    public ArrayList<String> getReadyQueue(){
        ArrayList<String> queue = new ArrayList<>();
        for (Process p : readyQueue) {
            queue.add(p.getProcessName());
        }
        return queue;
    }

    /**
     * Obtiene la lista de los nombres de los procesos
     * que se encuentran en la lista de procesos bloqueados
     * @return lista de nombres de procesos
     */
    public ArrayList<String> getBlockedList(){
        ArrayList<String> list = new ArrayList<>();
        blockedList.forEach(process -> list.add(process.getProcessName()));
        return list;
    }

    public void addCPUObserver(Observer observer){
        cpu.addObserver(observer);
    }

    /**
     * Crea un nuevo proceso y lo ubica en la cola de espera
     * @param secondsOfExecution Los segundos que se ejecutará este procesos
     */
    public void addProcess(int secondsOfExecution){
        Process process = new Process(secondsOfExecution);
        readyQueue.push(process);
    }

    /**
     * Pone en estado de ejecución el primer proceso
     * que se encuentre en la cola de espera
     * @throws CPUException si no hay procesos en la linea de espera
     * @see ErrorCode codigo específico de la excepcion
     */
    public Process dispatchNextProcess() throws CPUException {
        Process process = readyQueue.poll();
        if (process == null) throw new CPUException(ErrorCode.NO_PROCESS_READY);
        cpu.runProcess(process);
        return process;
    }

    /**
     * Envia el proceso en estado de ejecución a la lista de bloqueados
     * y libera la UCP
     */
    public void blockProcess(){
        blockedList.add(cpu.reset());
    }

    /**
     * Despierta el proceso  con el nombre especificado de la lista de bloqueados y lo
     * envia a la cola de espera
     * @param processName nombre del proceso
     */
    public void wakeUpProcess(String processName){
        // TODO: 14/12/21 Testear código
        Process process = blockedList.stream()
                .filter(p -> p.getProcessName().equals(processName))
                .findFirst().orElse(null);
        if (process != null) {
            blockedList.remove(process);
            process.wakeUp();
            readyQueue.push(process);
        }
    }

    /**
     * Envia el proceso en ejecucion a la lista de espera
     * y le asigna la cpu al siguiente proceso en la cola
     * @throws CPUException si no hay mas procesos a ejecutar
     * @return El nuevo proceso en ejecucion
     */
    public Process finishProcessTurn() throws CPUException {
        Process process = cpu.reset();
        readyQueue.push(process);
        return dispatchNextProcess();
    }

    /**
     * Indica si aún hay procesos en la cola de espera
     */
    public boolean hasProcessesReady(){
        return !readyQueue.isEmpty();
    }

    /**
     * Establece el tiempo que la UCP otorgará a cada proceso
     * @param seconds segundos de ejecución
     */
    public void setCpuExecuteTime(int seconds){
       cpu.setExecutionTime(seconds);
    }

    /**
     * Obtienen el tiempo de ejcución restante que la CPU
     * dispone para el proceso actual
     * @return segundos de ejecución para el proceso actual
     */
    public int getCPUTimeRemaining() {
        return cpu.getExecutionTimeRemaining();
    }

    /**
     * Obtiene el tiempo que le queda al proceso en ejecución en la CPU
     * para completar su tiempo de ejecución general
     * @return segundos restantes de ejecución total del proceso
     */
    public int getProcessTimeRemaining() {
        return cpu.getProcessRunning().getSecondsOfExecutionRemaining();
    }

    public Process getRunningProcess(){
        return cpu.getProcessRunning();
    }

    public boolean hasCPUAvailable(){
        return cpu.isFree();
    }

    public void blockActualProcess(){
        cpu.blockProcess();
    }
}
