import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class Multinivel implements Comparable<Multinivel> {
	private int id, quantProc, iQuantum, iBurst, wt, tr; // quantidade de processos / valor do burst /
															// waiting
															// time /
	// turnaround / tempo de chegada
	private final int fIBurst; // Variáveis declaradas como final pois recebem o valor recebido do tempo de
								// chegada e do burst
	private String sQuantum, sBurst; // Strings que definem se o burst/tempo de chegada serão manuais
	private Random random = new Random();
	private List<Multinivel> multinivel = new ArrayList<>();

	public Multinivel(int quantProc) {
		this.quantProc = quantProc;
		this.fIBurst = 0;
	}

	public Multinivel(int iBurst, int id) {
		this.iBurst = iBurst;
		this.id = id;
		this.fIBurst = iBurst;
	}

	public void escalona() { // recebe o total de iterações necessárias para consumir todos os processos
		// (linha 45)
		int menor = 0; // definirá qual é o processo com menor burst (linha 50)
		sQuantum = JOptionPane.showInputDialog("Quantum manual ou automático? [m/A]");
		sQuantum = sQuantum.toLowerCase();
		iQuantum = defineQuantum(sQuantum);
		sBurst = JOptionPane.showInputDialog("Burst manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase(); // deixa letra minúscula para evitar problemas
		for (int i = 0; i < quantProc; i++) { // instancia os processos
			iBurst = defineBurst(sBurst); // chama função para definição do burst manual / automático (linha 90)
			id = i + 1; // soma sentinela "i" para não existir id 0
			Multinivel p = new Multinivel(iBurst, id);
			multinivel.add(p); // adiciona objeto a um AmultinivelayList de processos
		}
		final int fQuantum = iQuantum;
		for (int i = 0; i < fQuantum * quantProc; i++) {
			imprime();
			System.out.println("Quantum: " + iQuantum);
			boolean executou = false;
			for (int k = 0; k < quantProc; k++) {
				if (multinivel.get(k).iBurst > 0) {
					if (menor == quantProc) {
						break;
					}
					if (k != menor) {
						multinivel.get(k).wt++;
					}

					if (k == menor && !executou) {
						executou = true;
						multinivel.get(menor).iBurst--;
						iQuantum--;
						if (multinivel.get(menor).iBurst == 0)
							iQuantum = 0;
					}

					multinivel.get(k).tr++;

				}
			}

			if (iQuantum == 0) {
				iQuantum = fQuantum;
				menor++;
			}
			if (menor == quantProc) {
				break;
			}
		}
		int aux = 0;
		for (int i = 0; i < quantProc; i++) {
			aux += multinivel.get(i).iBurst;
		}
		// Após a finalização do quantum por todos os processos (uma vez apenas), os que
		// restaram irão ser consumidos com o SJF
		menor = 0;
		Collections.sort(multinivel);
		for (int i = 0; i < aux; i++) {
			imprime();
			boolean executou = false;
			for (int k = 0; k < quantProc; k++) {
				if(multinivel.get(menor).iBurst == 0 && !executou) {
					menor ++;
				}
				if(menor == quantProc)
					menor = 0;
				if (multinivel.get(k).iBurst > 0) {

					if (k != menor){
						multinivel.get(k).wt++;
					}
					if (k == menor && !executou) {
						multinivel.get(k).iBurst--;
						executou = true;
					} 
					multinivel.get(k).tr++;
				}
				
			}
		}
	}

	public void imprime() {
		// tempo total de espera / tempo total de turnaround
		float tWt = 0, trt = 0;
		System.out.println("Processo\tBurst\tConsumo\tWaiting Time\tTurnaround");
		for (Multinivel multinivel : multinivel) {
			tWt += multinivel.wt;
			trt += multinivel.tr;
			System.out.print("P" + multinivel.id);
			System.out.print("\t\t" + multinivel.fIBurst);
			System.out.print("\t" + multinivel.iBurst);
			System.out.print("\t" + multinivel.wt);
			System.out.println("\t\t" + multinivel.tr);
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

	public int defineBurst(String opcao) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do burst: "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}

	@Override
	public int compareTo(Multinivel p) {
		if (iBurst < p.iBurst) {
			return -1;
		} else if (iBurst > p.iBurst) {
			return 1;
		} else {
			return 0;
		}
	}
}
