package tp1.logic;

import java.util.ArrayList;
import java.util.List;

public class ActionList {
	
	private List<Action> list;
	private final int maxNumActions = 4;
	
	public ActionList(){
		this.list = new ArrayList<>();
		
	}
	/*
	public void add(Action act) {
		//Aplicar condiciones
		if (act != null) this.list.add(act);
		restringir();
	}
	*/
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
	
//	public void restringirLista() {
//		//System.out.println("restringiendo lista");
//		this.list = restringir();
//	}
	
	public List<Action> restringir() {
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		int lr = 0;
		int ud = 0;
		
		List<Action> list_aux = new ArrayList<>();
		for (int i = 0; i < this.list.size(); i++) {
            Action a = this.list.get(i);

            // 1) CONSERVA STOP (no cuenta para límites)
            if (a == Action.STOP) {
                list_aux.add(Action.STOP);
                continue;
            }

            // 2) HORIZONTAL: primera dirección manda, máx 4
            if (a == Action.LEFT) {
                if (!right) {                 // si apareció RIGHT antes, ignora LEFT
                    left = true;              
                    if (lr < 4) {
                        list_aux.add(Action.LEFT);
                        lr++;
                    }
                }
                continue; // ← evita seguir comprobando UP/DOWN inútilmente
            }

            if (a == Action.RIGHT) {
                if (!left) {                  // si apareció LEFT antes, ignora RIGHT
                    right = true;             
                    if (lr < 4) {
                        list_aux.add(Action.RIGHT);
                        lr++;
                    }
                }
                continue;
            }

            // 3) VERTICAL: primera dirección manda, máx 4
            if (a == Action.UP) {
                if (!down) {                  // si apareció DOWN antes, ignora UP
                    up = true;
                    if (ud < 4) {
                        list_aux.add(Action.UP);
                        ud++;
                    }
                }
                continue;
            }

            if (a == Action.DOWN) {
                if (!up) {                    // si apareció UP antes, ignora DOWN
                    down = true;
                    if (ud < 4) {
                        list_aux.add(Action.DOWN);
                        ud++;
                    }
                }
                continue;
            }
        }

        return list_aux;
	}
	
	public Action nextAction() {
		return this.list.isEmpty() ? null : this.list.remove(0);
	}
	
	public boolean anyActions() {
		return !this.list.isEmpty();
	}
	
	
}
