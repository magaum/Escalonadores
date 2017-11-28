import javax.swing.JOptionPane;

public class Principal {

	public static void main(String[] args) {
		String proc;
		int quantProc;
		boolean valida;
		String opcao = "";
		do {
			opcao = JOptionPane.showInputDialog("Selecione uma op��o: \n" 
					+ "1 - FCFS\n" 
					+ "2 - SJF\n" 
					+ "3 - SRTF\n"
					+ "4 - Round Robin\n"
					+ "5 - Multinivel\n" 
					+ "6 - Sair");
			
			if(opcao.equals("6")) {
			//Por padr�o o JOptionPane ConfirmDialog cria um bot�o cancelar, para eliminar este bot�o � necess�rio passar o par�metro YES_NO_OPTION
			//O retorno do Confirm Dialog � um int, 0 para sim, 1 para n�o e 2 para cancelar				
				int escolha = JOptionPane.showConfirmDialog(null,"Deseja realmente sair?" ,"Sair",JOptionPane.YES_NO_OPTION,1);
				if(escolha != 0) {
					opcao = "";
				}
			}
			//Teste para validar a op��o selecionada
			else if(Integer.valueOf(opcao) > 0 && Integer.valueOf(opcao) < 6){
			//Valida��o da quantidade de processos
			do {
				proc = (JOptionPane.showInputDialog("Digite a quantidade de processos: "));
				valida = validaProc(proc);
			}while(!valida);
			
			quantProc = Integer.valueOf(proc);
			
			switch (opcao) {
			case "1": {
				FCFS fcfs = new FCFS(quantProc);
				fcfs.escalona();
				fcfs.imprime();
				opcao = "";
				break;
			}
			case "2": {
				SJF sjf = new SJF(quantProc);
				sjf.escalona();
				sjf.imprime();
				opcao = "";
				break;
			}
			case "3": {
				SRTF srtf = new SRTF(quantProc);
				srtf.escalona();
				srtf.imprime();
				opcao = "";
				break;
			}
			case "4": {
				RoundRobin rr = new RoundRobin(quantProc);
				rr.escalona();
				rr.imprime();
				opcao = "";
				break;
			}
			case "5": {
			Multinivel multinivel = new Multinivel(quantProc);
			multinivel.escalona();
			multinivel.imprime();
			opcao = "";
				break;
			}
			case "6": {
				break;
			}
			default: {
				break;
			}
			}
			}
			//Tratativa para op��es inv�lidas
			else {
				opcao = "";
			}
		} while (opcao == "");
		
	}
	//Tratativa para convers�o do valor solicitado via JOptionPane
	public static boolean validaProc(String proc) {
		try {
			Integer.parseInt(proc);
			return true;
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Digite apenas inteiros");
			return false;
		}
	}
}
