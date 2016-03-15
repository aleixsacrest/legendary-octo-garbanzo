package es.upc.fib.ia;

/**
 * Created by alexmiro on 15/3/16
 */

public class GoalTest {
    //TODO: decidir quin tipus de funcio usar
    Estat goal = new Estat();

    public boolean isGoalState(Estat state) {
        return state.equals(goal);
    }

    /*
    public boolean isGoalState (Estat state) {
        return state.isGoal();
    }
    */

}