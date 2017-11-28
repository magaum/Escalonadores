import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

//First-Come, First-Served//
public class FCFS {

	private int quantProc, iBurst, wt, tt, id;
	private String sBurst;
	private Random random = new Random();
	private List<FCFS> fcfs = new ArrayList<>();

	public FCFS(int quantProc) {
		this.quantProc = quantProc;
	}

	public FCFS(int iBurst, String sBurst) {
		this.iBurst = iBurst;
		this.sBurst = sBurst;
	}

	public void escalona() {
		int j =  -1;
		sBurst = JOptionPane.showInputDialog("Burst manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase();
		for(int i = 0; i < quantProc; i++) {
			id = i+1;
			iBurst = defineBurst(sBurst, id);
			FCFS p = new FCFS(iBurst, sBurst);
			fcfs.add(p);
			if(i == 0) {
				p.tt = p.iBurst;
			}else {
				p.tt = fcfs.get(j).tt+p.iBurst;
				p.wt = fcfs.get(j).iBurst+fcfs.get(j).wt;
			}
			j ++;
		}
	}

	public void imprime() {
		int tWt = 0,tTt = 0;
		System.out.println("Processo\tBurst\tWaiting Time\tTuraround");
		for (FCFS fcfs : fcfs) {
			tWt += fcfs.wt;
			tTt += fcfs.tt;
			System.out.print("P" + id);
			System.out.print("\t\t" + fcfs.iBurst);
			System.out.print("\t" + fcfs.wt);
			System.out.println("\t\t" + fcfs.tt);
		}
		System.out.println("Waiting Time Médio: "+(tWt)/quantProc);
		System.out.println("Turnaround: "+(tTt)/quantProc);
	}

	public int defineBurst(String opcao, int id) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do burst "+id+": "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}
}
