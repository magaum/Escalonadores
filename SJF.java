import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

//Shorted Jobs First//
public class SJF implements Comparable<SJF> {
	private int quantProc, iBurst, wt, tt;
	private String sBurst;
	private int id;
	private Random random = new Random();
	private List<SJF> sjf = new ArrayList<>();

	public SJF(int quantProc) {
		this.quantProc = quantProc;
	}

	public SJF(int iBurst,int id, String sBurst) {
		this.iBurst = iBurst;
		this.id = id;
		this.sBurst = sBurst;
	}

	public void escalona() {
		int j = -1;
		sBurst = JOptionPane.showInputDialog("Burst manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase();
		for (int i = 0; i < quantProc; i++) {
			iBurst = defineBurst(sBurst);
			id = i+1;
			SJF p = new SJF(iBurst,id, sBurst);
			sjf.add(p);
		}
		Collections.sort(sjf);
		for (int i = 0; i < quantProc; i++) {
			if(i == 0) {
				sjf.get(i).tt = sjf.get(i).iBurst;
			}else {
				sjf.get(i).tt = sjf.get(j).tt+sjf.get(i).iBurst;
				sjf.get(i).wt = sjf.get(j).iBurst+sjf.get(j).wt;
			}
			j ++;
		}
	}

	public void imprime() {
		int tWt = 0, tTt = 0;
		System.out.println("Processo\tBurst\tWaiting Time\tTuraround");
		for (SJF sjf : sjf) {
			tWt += sjf.wt;
			tTt += sjf.tt;
			System.out.print("P" + sjf.id);
			System.out.print("\t\t" + sjf.iBurst);
			System.out.print("\t" + sjf.wt);
			System.out.println("\t\t" + sjf.tt);
		}
		System.out.println("Waiting Time Médio: " + (tWt) / quantProc);
		System.out.println("Turnaround: " + (tTt) / quantProc);
	}

	public int defineBurst(String opcao) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do burst: "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}

	@Override
	public int compareTo(SJF p) {
		if (iBurst < p.iBurst) {
			return -1;
		} else if (iBurst > p.iBurst) {
			return 1;
		} else {
			return 0;
		}
	}
}
