import javax.swing.JOptionPane;

public class Principal {

	public static void main(String[] args) {
		String proc;
		int quantProc;
		boolean valida;
		String opcao = "";
		do {
			opcao = JOptionPane.showInputDialog("Selecione uma opção: \n" 
					+ "1 - FCFS\n" 
					+ "2 - SJF\n" 
					+ "3 - SRTF\n"
					+ "4 - Round Robin\n"
					+ "5 - Multinivel\n" 
					+ "6 - Sair");
			
			if(opcao.equals("6")) {
			//Por padrão o JOptionPane ConfirmDialog cria um botão cancelar, para eliminar este botão é necessário passar o parâmetro YES_NO_OPTION
			//O retorno do Confirm Dialog é um int, 0 para sim, 1 para não e 2 para cancelar				
				int escolha = JOptionPane.showConfirmDialog(null,"Deseja realmente sair?" ,"Sair",JOptionPane.YES_NO_OPTION,1);
				if(escolha != 0) {
					opcao = "";
				}
			}
			//Teste para validar a opção selecionada
			else if(Integer.valueOf(opcao) > 0 && Integer.valueOf(opcao) < 6){
			//Validação da quantidade de processos
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
			//Tratativa para opções inválidas
			else {
				opcao = "";
			}
		} while (opcao == "");
		
	}
	//Tratativa para conversão do valor solicitado via JOptionPane
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
