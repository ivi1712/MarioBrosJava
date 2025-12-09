package tp1.logic;

import java.util.ArrayList;
import java.util.List;

public class ActionList {
	
	private List<Action> list;
	private final int maxNumActions = 4;
	
	public ActionList(){
		this.list = new ArrayList<>();
		
	}
	
	public void add(Action act) {
        if (act == null) return;
        int leftCount = 0;
        int rightCount = 0;
        int upCount = 0;
        int downCount = 0;

        for (Action a : this.list) {
            switch (a) {
                case LEFT -> leftCount++;
                case RIGHT -> rightCount++;
                case UP -> upCount++;
                case DOWN -> downCount++;
                default -> {} 
            }
        }

        // 2. Filtro previo: Verificamos si podemos añadir la nueva acción
        if (canAddAction(act, leftCount, rightCount, upCount, downCount)) {
            this.list.add(act);
        }
    }

	// metodo aux para comprobar si se puede añadir
    private boolean canAddAction(Action act, int left, int right, int up, int down) {
        switch (act) {
            case STOP:  return true;
            case LEFT:  return right == 0 && left < maxNumActions;
            case RIGHT: return left == 0 && right < maxNumActions;
            case UP:    return down == 0 && up < maxNumActions;
            case DOWN:  return up == 0 && down < maxNumActions;
            default:    return false;
        }
    }
	
	
	public Action nextAction() {
		return this.list.isEmpty() ? null : this.list.remove(0);
	}
	
	public boolean anyActions() {
		return !this.list.isEmpty();
	}
	
	
}
