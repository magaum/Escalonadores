
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

//Shortest-Remaining-Time-First
public class SRTF implements Comparable<SRTF> {
	private int quantProc, iBurst, wt, tr, tc; //quantidade de processos / valor do burst / waiting time / turnaround / tempo de chegada
	private final int tfc, fIBurst; //Variáveis declaradas como final pois recebem o valor recebido do tempo de chegada e do burst
	private String sBurst, sTc; //Strings que definem se o burst/tempo de chegada serão manuais
	private int id; //id do processo
	private Random random = new Random();
	private List<SRTF> srtf = new ArrayList<>();

	public SRTF(int quantProc) {
		this.quantProc = quantProc;
		this.tfc = 0;
		this.fIBurst = 0;
	}

	public SRTF(int iBurst, int id, int tc, String sBurst) {
		this.iBurst = iBurst;
		this.id = id;
		this.tc = tc;
		this.sBurst = sBurst;
		this.tfc = tc;
		this.fIBurst = iBurst;
	}

	public void escalona() {
		int aux = 0; //recebe o total de iterações necessárias para consumir todos os processos (linha 45)
		int menor = 0; //definirá qual é o processo com menor burst (linha 50)
		sBurst = JOptionPane.showInputDialog("Burst manual ou automático? [m/A] ");
		sBurst = sBurst.toLowerCase(); //deixa letra minúscula para evitar problemas
		for (int i = 0; i < quantProc; i++) { //instancia os processos 
			iBurst = defineBurst(sBurst); //chama função para definição do burst manual / automático (linha 90)
			sTc = JOptionPane.showInputDialog("Tempo manual ou automático? [m/A] ");
			sTc = sTc.toLowerCase();
			tc = defineTc(sTc);
			id = i + 1; //soma sentinela "i" para não existir id 0
			SRTF p = new SRTF(iBurst, id, tc, sBurst); 
			srtf.add(p); //adiciona objeto a um ArrayList de processos
			aux += p.iBurst+p.tc;
		}
		for (int i = 0; i < aux; i++) {
			Collections.sort(srtf); // Ordena processos por burst 
			imprime(); // Exibe passo a passo para demonstrar o consumo dos processos
			if(srtf.get(menor).iBurst == 0) //avança sentinela "menor" quando o processo for consumido
				menor ++;
			if(menor == quantProc)
				break;
			boolean executou = false; //flag para determinar quando processo com menor burst executou
			for (int k = 0; k < quantProc; k++) { //varre os processos para consumo e determinação do tempo de espera
/* Enquando o tempo de chegada não for alcançado pela sentinela "i" ou caso o processo estiver consumido, não é necessário executar outras verificações*/
				if (srtf.get(k).iBurst > 0) { 
					//consome processo com menor burst 
					if(k == menor) {
						srtf.get(menor).iBurst--;
						executou = true;
						
					}
					//outros processos aguardam para serem executados e seu tempo de espera aumenta
					if (k != menor && executou) {
						srtf.get(k).wt ++;
					}
					//execução de outros processos quando o processo com menor burst não é alcançado pela sentinela "i"
					if (k != menor && !executou){
						srtf.get(k).iBurst--;
					}
					//incremento do tempo de chegada para que todos os processos tenham valor da sentinela "i" e a execução do algoritmo seja correta
					srtf.get(k).tc++;
					//definição do turnaround
					srtf.get(k).tr ++;
				}
			}		
		}
	}

	public void imprime() {
		//tempo total de espera / tempo total de turnaround
		float tWt = 0, trt = 0;
		System.out.println("Processo\tBurst\tConsumo\tWaiting Time\tTurnaround\tTempo Chegada");
		for (SRTF srtf : srtf) {
			tWt += srtf.wt;
			trt += srtf.tr;
			System.out.print("P" + srtf.id);
			System.out.print("\t\t" + srtf.fIBurst);
			System.out.print("\t" + srtf.iBurst);
			System.out.print("\t" + srtf.wt);
			System.out.print("\t\t" + srtf.tr);
			System.out.println("\t\t" + srtf.tfc);
		}
		System.out.println("Waiting Time Médio: " + (tWt) / quantProc);
		System.out.println("Turnaround: " + (trt) / quantProc);
	}

	public int defineBurst(String opcao) {
		if (opcao.equals("m")) {
			iBurst = Integer.valueOf(JOptionPane.showInputDialog("Tamanho do burst: "));
		} else if (opcao.equals("a")) {
			iBurst = random.nextInt(50) + 1;
		}
		return iBurst;
	}

	public int defineTc(String opcao) {
		if (opcao.equals("m")) {
			tc = Integer.valueOf(JOptionPane.showInputDialog("Tempo de chegada: "));
		} else if (opcao.equals("a")) {
			tc = random.nextInt(50) + 1;
		}
		return tc;
	}

	//sobescrição do metodo compareTo para organizar o ArrayList com o atributo burst
	@Override
	public int compareTo(SRTF p) {
		if (iBurst < p.iBurst) {
			return -1;
		} else if (iBurst > p.iBurst) {
			return 1;
		} else {
			return 0;
		}
	}
}
