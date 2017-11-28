import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class RoundRobin {
	// id do processo / quantidade de processos / quantum / valor do burst / waiting time / turnaround
	private int id, quantProc, iQuantum, iBurst, wt, tr;
	// turnaround / tempo de chegada
	private final int fIBurst; // Variável declarada como final pois recebe o valor do tempo de chegada e do burst
	private String sQuantum, sBurst; // Strings que definem se o burst/tempo de chegada serão manuais
	private Random random = new Random();
	private List<RoundRobin> rr = new ArrayList<>();

	public RoundRobin(int quantProc) {
		this.quantProc = quantProc;
		this.fIBurst = 0;
	}

	public RoundRobin(int iBurst, int id) {
		this.iBurst = iBurst;
		this.id = id;
		this.fIBurst = iBurst;
	}

	public void escalona() {
		int aux = 0; // recebe o total de iterações necessárias para consumir todos os processos (linha 41)
		int menor = 0; // indice do processo que será executado
		sQuantum = JOptionPane.showInputDialog("Quantum manual ou automático? [m/A]");
		sQuantum = sQuantum.toLowerCase();
		iQuantum = defineQuantum(sQuantum);
		sBurst = JOptionPane.showInputDialog("Burst manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase(); // deixa letra minúscula para evitar problemas
		for (int i = 0; i < quantProc; i++) { // instancia os processos
			id = i + 1; // soma sentinela "i" para não existir id 0
			iBurst = defineBurst(sBurst, id); // chama função para definição do burst manual / automático (linha 90)
			RoundRobin p = new RoundRobin(iBurst, id);
			rr.add(p); // adiciona objeto a um ArrayList de processos
			aux += p.iBurst;
		}
		final int fQuantum = iQuantum;
		for (int i = 0; i < aux; i++) {
			imprime();
			System.out.println("Quantum: " + iQuantum);
			boolean executou = false;
			if(rr.get(menor).iBurst == 0)
				while(rr.get(menor).iBurst == 0) {
				menor++;
				if (menor == quantProc) 
				menor = 0;
			}
			for (int k = 0; k < quantProc; k++) {
				if (rr.get(k).iBurst > 0) {
					if (menor == quantProc) {
						menor = 0;
						iQuantum = fQuantum;
					}
					if (k != menor) {
						rr.get(k).wt++;
					}

					if (k == menor && !executou) {
						executou = true;
						rr.get(menor).iBurst--;
						iQuantum--;
						if (rr.get(menor).iBurst == 0)
							iQuantum = 0;
					}

					rr.get(k).tr++;

				}
			}
			
			if (iQuantum == 0) {
				iQuantum = fQuantum;
				menor++;
			}
			if (menor == quantProc) {
				menor = 0;
			}
		}
	}

	public void imprime() {
		// tempo total de espera / tempo total de turnaround
		float tWt = 0, trt = 0;
		System.out.println("Processo\tBurst\tConsumo\tWaiting Time\tTurnaround");
		for (RoundRobin rr : rr) {
			tWt += rr.wt;
			trt += rr.tr;
			System.out.print("P" + rr.id);
			System.out.print("\t\t" + rr.fIBurst);
			System.out.print("\t" + rr.iBurst);
			System.out.print("\t" + rr.wt);
			System.out.println("\t\t" + rr.tr);
		}
		System.out.println("Waiting Time Médio: " + (tWt) / quantProc);
		System.out.println("Turnaround: " + (trt) / quantProc);
	}

	public int defineQuantum(String opcao) {
		if (opcao.equals("m")) {
			iQuantum = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do quantum: "));
		} else if (opcao.equals("a")) {
			iQuantum = random.nextInt(50) + 1;
		}
		return iQuantum;

	}

	public int defineBurst(String opcao, int id) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do burst do processo "+id+": "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}
}
