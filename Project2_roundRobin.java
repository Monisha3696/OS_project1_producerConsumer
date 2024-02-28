import java.util.LinkedList;
import java.util.Queue;

class RoundRobinScheduling {
    public static void main(String[] args) {
        int quantum = 4; // Time quantum for round robin scheduling
        RoundRobin rr = new RoundRobin(quantum);
        rr.addProcess(new Process(1, 10)); 
        rr.addProcess(new Process(2, 15)); 
        rr.addProcess(new Process(3, 20)); 
        rr.execute();
    }
}

class RoundRobin {
    private int quantum; // Time quantum
    private Queue<Process> queue; // Queue of processes

    public RoundRobin(int quantum) {
        this.quantum = quantum;
        this.queue = new LinkedList<>();
    }

    public void addProcess(Process process) {
        queue.add(process);
    }

    public void execute() {
        while (!queue.isEmpty()) {
            Process current = queue.poll();
            // Simulate executing the process for the quantum time
            int remainingTime = current.execute(quantum);

            if (remainingTime > 0) {
                // Process is not finished, add it back to the queue
                queue.add(current);
            } else {
                System.out.println("Process " + current.id + " completed.");
            }
        }
    }
}

class Process {
    int id;
    int totalTime;
    int remainingTime;

    public Process(int id, int totalTime) {
        this.id = id;
        this.totalTime = totalTime;
        this.remainingTime = totalTime;
    }

    // Simulates executing the process and returns the remaining time
    public int execute(int quantum) {
        System.out.println("Executing process " + id + " for " + Math.min(quantum, remainingTime) + " units.");
        remainingTime -= quantum;
        return Math.max(remainingTime, 0);
    }
}
