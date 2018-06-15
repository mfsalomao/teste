package src;
import java.io.*;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class AsiloMain {
	private static StringBuffer memoriaPessoas = new StringBuffer();
	private static StringBuffer memoriaAcomodacoes = new StringBuffer();
	// private static Scanner ler = new Scanner (System.in);
	private static final String acomodacoes = "acomodacoes.txt";
	private static final String pessoas = "pessoas.txt"; 
	public static void main(String[] args) {
		char opcao;
		primeiraVez();
		do {
			opcao = menu();
			switch (opcao) {
			case '1': menuPessoas(); break;
			case '2': menuAcomodacoes(); break;
			case '3': menuListarQuartos(); break;
			case '4': menuPesquisarPessoas(); break;
			case 'q': finalizar(); break;
			default: JOptionPane.showMessageDialog(null,"Opção invalida"); break;
			}
		} while (opcao != 'q') ;  
	}
	private static char menu() {
		char opcao;
		opcao = Character.toLowerCase(JOptionPane.showInputDialog("Menu:\n"
				+ "(1) Gerenciar pessoas. \n" 
				+ "(2) Gerenciar acomodações.\n" 
				+ "(3) Listagem de quartos\n" 
				+ "(4) Pesquisar pessoas\n" 
				+ "(q) Para finalizar programa.").charAt(0));
		return opcao;
	}
	private static void menuPessoas() {
		char opcao;
		do { 
			opcao = Character.toLowerCase(JOptionPane.showInputDialog("Gerenciamento de pessoas.\n"
					+ "Digite a opção desejada:\n"
					+ "(1) Cadastrar nova pessoa.\n"
					+ "(2) Alterar cadastro de pessoa.\n"
					+ "(3) Descadastrar pessoa.\n"
					+ "(q) Para voltar ao menu anterior").charAt(0));
			switch (opcao) { 
			case '1': inserirPessoa(); break;
			case '2': alterarPessoa(); break;
			case '3': descadastrarPessoa(); break;
			case 'q': break;
			default: JOptionPane.showMessageDialog(null,"Opção inválida");
			} 
		} while (opcao != 'q') ;
	}

	private static void menuAcomodacoes() {
		char opcao;
		do { 
			opcao = Character.toLowerCase(JOptionPane.showInputDialog("Gerenciamento de acomodações.\n"
					+ "Digite a opção desejada:\n"
					+ "(1) Inserir acomodação.\n"
					+ "(2) Alterar valor/hospede.\n"
					+ "(3) Alugar acomodação vaga.\n"
					+ "(4) Desalugar acomodação.\n"
					+ "(q) Para voltar ao menu anterior").charAt(0));
			switch (opcao) { 
			case '1': inserirAcomodacao(); break;
			case '2': alterarAcomodacao(); break;
			case '3': alugarAcomodacao(0,0,true); break;
			case '4': desalugarAcomodacao(0); break;
			case 'q': break;
			default: JOptionPane.showMessageDialog(null,"Opção inválida");
			} 
		} while (opcao != 'q') ;
	}
	private static void menuListarQuartos() {
		char opcao;
		do { 
			opcao = Character.toLowerCase(
					JOptionPane.showInputDialog("Escolha uma opção para listar os quartos:\n"
					+ "(1) Listar quartos desocupados.\n"
					+ "(2) Listar quartos alugados.\n"
					+ "(3) Listar todos os quartos.\n"
					+ "(q) Parar voltar ao menu anterior").charAt(0));
			switch (opcao) { 
			case '1': pesquisarAcomodacao(0, true, false, true); break;
			case '2': pesquisarAcomodacao(0, true, true, false); break;
			case '3': pesquisarAcomodacao(0, true, true, true); break;
			case 'q': break;
			default: JOptionPane.showMessageDialog(null,"Opção invalida");
			} 
		} while (opcao != 'q') ;
	}

	private static void menuPesquisarPessoas() {
		char opcao;
		do { 
			opcao = Character.toLowerCase(JOptionPane.showInputDialog("\nEscolha uma opção para pesquisar através:\n"
					+ "(1) Pesquisar pelo CPF.\n"
					+ "(2) Pesquisar pelo nome.\n"
					+ "(q) Para voltar ao menu anterior").charAt(0));
			switch (opcao) { 
			case '1': pesquisarPessoa("cpf",0,true); break;
			case '2': pesquisarPessoa("nome",0,true); break;
			case 'q': break;
			default: JOptionPane.showMessageDialog(null,"Opção inválida");
			} 
		} while (opcao != 'q') ;
	}
	private static void primeiraVez() {
		JOptionPane.showMessageDialog(null,"Olá, sejam bem vindos ao nosso gerenciamento eletronico de acomodações!\n"
				+ "Este programa foi desenvolvido por:\n"
				+ "Cesar Ávalos; Marcelo dos Santos; Martinho Salomão.\n"
				+ "Escolha uma das opções do menu para continuar.");
	}
	private static void finalizar() {
		JOptionPane.showMessageDialog(null,"Programa finalizado com sucesso.");
	}
	static void iniciarArquivo(StringBuffer memoria, String caminho){
		try{
			BufferedReader arqEntrada;
			arqEntrada = new BufferedReader (new FileReader (caminho));
			String linha = "";
			memoria.delete(0,memoria.length());
			while ( (linha = arqEntrada.readLine()) != null ) {
				memoria.append (linha + "\n");
			}
			arqEntrada.close();
		} catch (FileNotFoundException erro){
			JOptionPane.showMessageDialog(null,"Arquivo "+caminho+" não encontrado");
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Erro de Leitura ao iniciar"+caminho+"!");
		}
	}
	private static void gravar (StringBuffer memoria, String caminho){
		try{
			BufferedWriter arqSaida;
			arqSaida = new BufferedWriter(new FileWriter (caminho));
			arqSaida.write(memoria.toString());
			arqSaida.flush();
			arqSaida.close();
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Erro de gravação!");
		}
	}

	private static int pesquisarPessoa(String n, long HospedeCpf, boolean pesqQuarto) {
		String procura = null, cpf, nome, sexo = "", dataNasc = "0", contatoEmergencia = "0", telefone = "0";
		int quarto, inicio , ultimo, primeiro, fim;
		boolean achou = false;
		Pessoas reg = null;
		iniciarArquivo(memoriaPessoas, pessoas);
		try {
			if (memoriaPessoas.length()!=0) {
				if  (n.equals("cpf")) {
					if (HospedeCpf > 0) {
						procura = String.valueOf(HospedeCpf);
					} else {
						procura = JOptionPane.showInputDialog("Digite o CPF da pessoa que deseja pesquisar: ");
					}
				} else if  (n.equals("nome")) {
					procura = JOptionPane.showInputDialog("Digite o nome da pessoa que deseja pesquisar: ");
				}
				inicio = 0;
				while ((inicio != memoriaPessoas.length())&&(!achou)) {
					primeiro = memoriaPessoas.indexOf ("=",inicio);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					cpf = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					nome = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					sexo = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					dataNasc = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					telefone = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					fim = memoriaPessoas.indexOf("]",primeiro);
					contatoEmergencia = memoriaPessoas.substring(primeiro+1, fim);

					reg = new Pessoas (Long.valueOf(cpf), nome, Character.valueOf(sexo.charAt(0)), dataNasc, Long.valueOf(telefone), contatoEmergencia);
					if  ((n.equals("cpf")) && (reg.getCpf()== Long.valueOf(procura))) {
						achou = true;
					}  else if ((n.equals("nome")) && (reg.getNome().equals(procura))) {
						achou = true;
					}
					inicio = fim + 2;
				}
				if (achou) {
					JOptionPane.showMessageDialog(null,"Hospede: " + reg.toString());
					if (pesqQuarto) {
						quarto = pesquisarAcomodacao(reg.getCpf(), false, false, false);
						if (quarto > 0) {
							JOptionPane.showMessageDialog(null,"Hospede: " + reg.getNome() + " encontrado no quarto " + quarto+".");
						} else {
							JOptionPane.showMessageDialog(null,"Hospede: " + reg.getNome() + " não está ocupando nenhum quarto");
						} 						
					}
					return 1;
				} else { 
					if (pesqQuarto) {
				
						JOptionPane.showMessageDialog(null,"Hospede não encontrado.");
					}	
					return -1;
				}
			}else {
				JOptionPane.showMessageDialog(null,"Arquivo de pessoas se encontra vazio.\n");
				return -2;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return -3;
	}
	private static void alterarPessoa() {
		String procura = null, cpf, nome, sexo = "m", dataNasc = "0", contatoEmergencia = "0", telefone = "0";
		int quarto, inicio , ultimo, primeiro, fim = 0;
		boolean achou = false;
		Pessoas reg = null;
		iniciarArquivo(memoriaPessoas, pessoas);
		try {
			if (memoriaPessoas.length()!=0) {
				procura = JOptionPane.showInputDialog("Digite o CPF da pessoa que deseja alterar: ");
				inicio = 0;
				while ((inicio != memoriaPessoas.length())&&(!achou)) {
					primeiro = memoriaPessoas.indexOf ("=",inicio);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					cpf = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					nome = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					sexo = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					dataNasc = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					telefone = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					fim = memoriaPessoas.indexOf("]",primeiro);
					contatoEmergencia = memoriaPessoas.substring(primeiro+1, fim);

					reg = new Pessoas (Long.valueOf(cpf), nome, Character.valueOf(sexo.charAt(0)), dataNasc, Long.valueOf(telefone), contatoEmergencia);
					if  (reg.getCpf()== Long.valueOf(procura)) {
						achou = true;
					} else { 
						inicio = fim + 2;
					}
				}
				if (achou) {
					JOptionPane.showMessageDialog(null,"Pessoa encontrada.");
					char opcao;
					do { 
						JOptionPane.showMessageDialog(null,reg.toString());
						opcao = Character.toLowerCase(JOptionPane.showInputDialog("Escolha uma opção para alterar:\n"
								+ "(1) CPF\n"
								+ "(2) Nome\n"
								+ "(3) Sexo\n"
								+ "(4) Data de nascimento\n"
								+ "(5) Telefone\n"
								+ "(6) Contato de emergencia\n"
								//+ "(7) Alterar acomoda��o\n"
								+ "(8) Gravar dados\n"
								+ "(Q) Sair\n").charAt(0));
						switch (opcao) { 
						case '1':
							reg.setCpf(Long.parseLong(JOptionPane.showInputDialog("Digite um novo cpf: \n")));
							break;
						case '2': 
							reg.setNome(JOptionPane.showInputDialog("Digite um novo nome: \n"));
							break;
						case '3': 
							reg.setSexo(JOptionPane.showInputDialog("Digite um novo sexo: \n").charAt(0)); break;
						case '4': 
							reg.setDataNasc(JOptionPane.showInputDialog("Digite um nova data de Nascimento (dd/mm/aa)")); break;
						case '5': 			
							reg.setTelefone(Long.parseLong(JOptionPane.showInputDialog("Digite um novo telefone"))); break;
						case '6':							
							reg.setContatoEmergencia(JOptionPane.showInputDialog("Digite um novo contato de emergencia")); break;
						case '7':
							quarto = pesquisarAcomodacao(reg.getCpf(),false,false,false);
							if (quarto != 0) {
								JOptionPane.showMessageDialog(null,"Esta pessoa está alugando o quarto "+quarto);
							} else {
								JOptionPane.showMessageDialog(null,"Nenhum quarto alugado com esta pessoa.");
							}
							String desejAlug = JOptionPane.showInputDialog("Deseja alterar para um novo quarto? (S/N)");
							if (desejAlug.toLowerCase().equals("s")) {
								if (quarto != 0) {
									desalugarAcomodacao(quarto);
								} 
								int alugAcom = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do novo quarto:"));
								alugarAcomodacao(alugAcom,reg.getCpf(),false);
							}
						case '8':
							memoriaPessoas.replace(inicio, fim + 2,reg.toString());
							gravar(memoriaPessoas,pessoas);
						case 'q': break;
						default: JOptionPane.showMessageDialog(null,"Opção inválida");
						} 

					} while (opcao != 'q');


				} else {
					JOptionPane.showMessageDialog(null,"Pessoa com CPF "+procura+" não encontrada.");
				}
			}else{
				JOptionPane.showMessageDialog(null,"Arquivo de pessoas se encontra vazio.");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	private static void inserirPessoa(){
		String cpf;
		String nome;
		char sexoChar = 0;
		String sexo = "";
		String dataNasc = null;
		long telefone = 0;
		String contatoEmergencia = null;

		try {
			BufferedWriter saida;
			saida=new BufferedWriter(new FileWriter (pessoas,true));
			nome = JOptionPane.showInputDialog("Digite o nome completo: ");		
			while (nome.length()<9) {
				nome = JOptionPane.showInputDialog("Digite o nome completo: ");				
			}
			cpf = JOptionPane.showInputDialog("Digite o cpf, sem traços nem espaços: ");
			while (!cpf.matches("[0-9]{11}")) {
				cpf = JOptionPane.showInputDialog("Digite o cpf, sem traços nem espaços: ");
				if (pesquisarPessoa("cpf", Long.valueOf(cpf), false)>0) {
					JOptionPane.showMessageDialog(null,"Este cpf já foi cadastrado!");
					cpf = "1";
				}
			}			
			sexo = JOptionPane.showInputDialog("Digite o sexo (m/f): ");
			sexoChar = Character.toLowerCase(sexo.charAt(0));
			while ((sexoChar != 'm' )&&(sexoChar != 'f')||(sexo.length()>1)) {
				sexo = JOptionPane.showInputDialog("Digite o sexo no formato (m/f):");
				sexoChar = Character.toLowerCase(sexo.charAt(0));
			}
			dataNasc = JOptionPane.showInputDialog("Digite a data de nascimento no formato: dd/mm/aaaa: ");
			while (!dataNasc.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
				dataNasc = JOptionPane.showInputDialog("Digite a data de nascimento no formato: dd/mm/aaaa: ");		
			}
			String tel = JOptionPane.showInputDialog("Digite o numero de telefone: ");
			telefone = Long.valueOf(tel.replaceAll("[^0-9]",""));
			contatoEmergencia = JOptionPane.showInputDialog("Digite o contato de emergencia: ");
			
			Pessoas cadastro = new Pessoas(Long.valueOf(cpf), nome, sexoChar, dataNasc, Long.valueOf(telefone), contatoEmergencia);
			saida.write(cadastro.toString());
			saida.flush();
			saida.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Erro de inserção de dados");
			e.printStackTrace();
		}

	}

	private static void descadastrarPessoa() {
		String procura = null, cpf, nome, sexo = "m", dataNasc = "0", contatoEmergencia = "0", telefone = "0";
		int quarto, inicio , ultimo, primeiro, fim = 0;
		boolean achou = false;
		Pessoas reg = null;
		iniciarArquivo(memoriaPessoas, pessoas);
		try {
			if (memoriaPessoas.length()!=0) {
				procura = JOptionPane.showInputDialog("Digite o CPF da pessoa que deseja descadastrar: ");
				inicio = 0;
				while ((inicio != memoriaPessoas.length())&&(!achou)) {
					primeiro = memoriaPessoas.indexOf ("=",inicio);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					cpf = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					nome = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					sexo = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					dataNasc = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					ultimo = memoriaPessoas.indexOf(";",primeiro);
					telefone = memoriaPessoas.substring(primeiro+1, ultimo);
					primeiro = memoriaPessoas.indexOf ("=",ultimo+1);
					fim = memoriaPessoas.indexOf("]",primeiro);
					contatoEmergencia = memoriaPessoas.substring(primeiro+1, fim);

					reg = new Pessoas (Long.valueOf(cpf), nome, Character.valueOf(sexo.charAt(0)), dataNasc, Long.valueOf(telefone), contatoEmergencia);
					if  (reg.getCpf()== Long.valueOf(procura)) {
						achou = true;
					} else { 
						inicio = fim + 2;
					}
				}
				if (achou) {
					JOptionPane.showMessageDialog(null,"Registro encontrado. \n" + reg.toString());
					try {
						quarto = pesquisarAcomodacao(reg.getCpf(),false,false,false);
						if (quarto != 0) {
							desalugarAcomodacao(quarto);
						} else {
							JOptionPane.showMessageDialog(null,"Nenhum quarto alugado com esta pessoa.");
						}
						memoriaPessoas.delete(inicio, fim + 2);
						gravar(memoriaPessoas,pessoas);
						JOptionPane.showMessageDialog(null,"Descadastro realizado com sucesso.");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Pessoa com CPF "+procura+" não encontrada.");
				}
			}else{
				JOptionPane.showMessageDialog(null,"Arquivo de pessoas se encontra vazio.");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	private static void inserirAcomodacao(){
		String quarto = "0";
		String alugado = "false";
		String cpf = "0";
		String valor = "0";
		String tempo = "0";
		String resp;

		try{
			BufferedWriter saida;
			saida=new BufferedWriter(new FileWriter (acomodacoes,true));
			quarto = JOptionPane.showInputDialog("Digite o numero do quarto a ser inserido: ");
			//			if (!checarAcomodacao(Integer.valueOf(quarto))) {
			valor = JOptionPane.showInputDialog("Digite o valor a ser alugado: ");
			resp = JOptionPane.showInputDialog("O quarto está sendo alugado?(S/N)");
			if (resp.toLowerCase().equals("s")) {
				cpf = JOptionPane.showInputDialog("Digite o cpf da pessoa que está alugando");
				alugado="true";			
			} 
			Acomodacoes nova = new Acomodacoes(Integer.valueOf(quarto), Boolean.valueOf(alugado),Long.valueOf(cpf), Double.valueOf(valor), Integer.valueOf(tempo));
			saida.write(nova.toString());
			saida.flush();
			saida.close();
			//			} else {
			//				System.out.println("Este quarto j� existe.");
			//			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null,"Erro de inserção de dados da acomodacao");
		}
	}	

	private static int pesquisarAcomodacao(long pesqHospCpf, boolean listarNomes, boolean ocupados, boolean vagos) {
		String quarto, alugado, cpf, valor, tempo;
		int inicio, fim, ultimo, primeiro;
		boolean achou=false;
		iniciarArquivo(memoriaAcomodacoes, acomodacoes);
		if (memoriaAcomodacoes.length()!=0) {
			alugado = "";
			cpf = "";
			valor = "";
			tempo = "";
			inicio = 0;
			JOptionPane.showMessageDialog(null,"Iniciando procura de acomodacões: ");
			while ((inicio != memoriaAcomodacoes.length())&&(!achou)) {

				primeiro = memoriaAcomodacoes.indexOf ("=", inicio);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				quarto = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				alugado = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				cpf = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				valor = memoriaAcomodacoes.substring(primeiro+1, ultimo);	
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				fim = memoriaAcomodacoes.indexOf ("]", primeiro);
				tempo = memoriaAcomodacoes.substring(primeiro+1, fim);		

				Acomodacoes acomodacao = new Acomodacoes(Integer.parseInt(quarto),Boolean.valueOf(alugado),Long.valueOf(cpf), Double.valueOf(valor), Integer.valueOf(tempo));
				// Aqui lista os quartos ocupados 
				if ((ocupados)&&(acomodacao.isAlugado())) {
					JOptionPane.showMessageDialog(null,acomodacao.toString());
					// Listando nome do hospede e dados usando pesquisa
					if (listarNomes) {
						pesquisarPessoa("cpf",acomodacao.getHospedeCpf(),false);
					}
				}
				// Lista quarto vagos
				if ((vagos)&&(!acomodacao.isAlugado())) {
					JOptionPane.showMessageDialog(null,acomodacao.toString());
				}
				// Caso informado CPF na fun��o, esta pesquisa ir� retornar
				// o numero do quarto q o Hospede esteja ocupando.
				if ((pesqHospCpf > 0) && (pesqHospCpf==acomodacao.getHospedeCpf())) {
					return acomodacao.getQuarto();
				} 
				inicio = fim + 2;
			}
			return 0;

		}else{
			JOptionPane.showMessageDialog(null,"Arquivo de "+acomodacoes+" vazio");
		}
		return -1;
	}

	private static void alterarAcomodacao() {
		String quarto, alugado, cpf, valor, tempo;
		int inicio, fim, ultimo, primeiro;
		boolean achou=false;
		int procura;
		iniciarArquivo(memoriaAcomodacoes, acomodacoes);
		if (memoriaAcomodacoes.length()!=0) {
			procura = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do quarto para alteração:"));
			alugado = "";
			cpf = "";
			valor = "";
			tempo = "";
			inicio = 0;
			while ((inicio != memoriaAcomodacoes.length())&&(!achou)) {
				primeiro = memoriaAcomodacoes.indexOf ("=", inicio);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				quarto = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				alugado = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				cpf = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				valor = memoriaAcomodacoes.substring(primeiro+1, ultimo);	
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				fim = memoriaAcomodacoes.indexOf ("]", primeiro);
				tempo = memoriaAcomodacoes.substring(primeiro+1, fim);		

				Acomodacoes reg = new Acomodacoes(Integer.parseInt(quarto),Boolean.valueOf(alugado),Long.valueOf(cpf), Double.valueOf(valor), Integer.valueOf(tempo));
				if (procura==reg.getQuarto()){
					JOptionPane.showMessageDialog(null, "Quarto encontrado. \n" + reg.toString());
					String altValorQuarto = JOptionPane.showInputDialog("Deseja alterar o valor do quarto ?(S/N)");
					if (altValorQuarto.toLowerCase().equals("s")) {
						valor = JOptionPane.showInputDialog("Digite o novo valor do quarto: ");
						reg.setValor(Double.valueOf(valor));
					} 

					if (alugado.equals("false")) {
						JOptionPane.showMessageDialog(null,"Este quarto "+quarto+" está desocupado.");
					} else {
						JOptionPane.showMessageDialog(null,"Este quarto "+quarto+" está ocupado.");
					}
					String novoHosp = JOptionPane.showInputDialog("Deseja atribuir novo hospede?(S/N) ");
					if (novoHosp.toLowerCase().equals("s")) {
						cpf = JOptionPane.showInputDialog("Digite o cpf da pessoa que está alugando: ");
						reg.setHospedeCpf(Long.valueOf(cpf));
						reg.setAlugado(true);
					} 
					memoriaAcomodacoes.replace(inicio, fim+2,reg.toString());
					gravar(memoriaAcomodacoes, acomodacoes);
					achou = true;
				}

				inicio = fim + 2;
			}
			if (achou){
				//System.out.println("\naltera��o de hospedagem realizada com sucesso");
			}else{
				JOptionPane.showMessageDialog(null,"Quarto não encontrado");
			}
		}else{
			JOptionPane.showMessageDialog(null,"Arquivo de "+acomodacoes+" vazio");
		}
	}

	private static void alugarAcomodacao(int novoQuarto, long novoCpf, boolean novoValor) {
		String quarto, alugado, cpf, valor, tempo, resp;
		int inicio, fim, ultimo, primeiro;
		boolean achou=false;
		iniciarArquivo(memoriaAcomodacoes, acomodacoes);
		if (memoriaAcomodacoes.length()!=0) {
			cpf = "";
			valor = "0";
			tempo = "0";
			inicio = 0;
			resp = "";
			while ((inicio != memoriaAcomodacoes.length())&&(!achou)) {
				primeiro = memoriaAcomodacoes.indexOf ("=", inicio);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				quarto = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				alugado = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				cpf = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				valor = memoriaAcomodacoes.substring(primeiro+1, ultimo);	
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				fim = memoriaAcomodacoes.indexOf ("]", primeiro);
				tempo = memoriaAcomodacoes.substring(primeiro+1, fim);		

				Acomodacoes reg = new Acomodacoes(Integer.parseInt(quarto),Boolean.valueOf(alugado),Long.valueOf(cpf), Double.valueOf(valor), Integer.valueOf(tempo));
				JOptionPane.showMessageDialog(null,"novoquarto"+novoQuarto+"get"+reg.getQuarto()+"novocpf"+novoCpf);
				if ((novoQuarto != 0 ) && (reg.getQuarto() == novoQuarto)) {
					if (reg.isAlugado()) {
						JOptionPane.showMessageDialog(null,"Este quarto já está alugado");
						achou = true;
					} else {
						reg.setHospedeCpf(Long.valueOf(cpf));
						reg.setAlugado(true);
						memoriaAcomodacoes.replace(inicio, fim+2,reg.toString());
						gravar(memoriaAcomodacoes, acomodacoes);
						achou = true;
					}
				} else if (!reg.isAlugado()){
					JOptionPane.showMessageDialog(null,"Quarto vago encontrado");
					System.out.println(reg.toString()); 
					if (novoValor) {
						resp = JOptionPane.showInputDialog("Deseja alterar o valor do quarto ?(S/N)");
						if (resp.toLowerCase().equals("s")) {
							valor = JOptionPane.showInputDialog("Digite o novo valor do quarto: ");
							reg.setValor(Double.valueOf(valor));
						} 
					}
					if (novoCpf == 0) {
						cpf = JOptionPane.showInputDialog("Digite o cpf da pessoa que irá alugar");
					} else {
						cpf = String.valueOf(novoCpf);
					}
					reg.setHospedeCpf(Long.valueOf(cpf));
					reg.setAlugado(true);
					memoriaAcomodacoes.replace(inicio, fim+2,reg.toString());
					gravar(memoriaAcomodacoes, acomodacoes);
					achou = true;
				}
				inicio = fim + 2;
			}
			if (achou){
				//System.out.println("\naltera��o de hospedagem realizada com sucesso");
			}else{
				JOptionPane.showMessageDialog(null,"Não existem acomodações vagas no momento");
			}
		}else{
			JOptionPane.showMessageDialog(null,"Arquivo de "+acomodacoes+" vazio");
		}
	}
	private static void desalugarAcomodacao(int pesqQuarto) {
		String quarto, alugado, cpf, valor, tempo, resp;
		int inicio, fim, ultimo, primeiro;
		boolean achou=false;
		int procura;
		iniciarArquivo(memoriaAcomodacoes, acomodacoes);
		if (memoriaAcomodacoes.length()!=0) {
			if (pesqQuarto == 0) {
				procura = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do quarto para alteração: "));
			} else {
				procura = pesqQuarto;
			}
			alugado = "";
			cpf = "";
			valor = "";
			tempo = "";
			inicio = 0;
			resp = "";
			while ((inicio != memoriaAcomodacoes.length())&&(!achou)) {
				primeiro = memoriaAcomodacoes.indexOf ("=", inicio);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				quarto = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				alugado = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro);
				cpf = memoriaAcomodacoes.substring(primeiro+1, ultimo);
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				ultimo = memoriaAcomodacoes.indexOf (";", primeiro); 
				valor = memoriaAcomodacoes.substring(primeiro+1, ultimo);	
				primeiro =  memoriaAcomodacoes.indexOf ("=", ultimo + 1);
				fim = memoriaAcomodacoes.indexOf ("]", primeiro);
				tempo = memoriaAcomodacoes.substring(primeiro+1, fim);		

				Acomodacoes reg = new Acomodacoes(Integer.parseInt(quarto),Boolean.valueOf(alugado),Long.valueOf(cpf), Double.valueOf(valor), Integer.valueOf(tempo));
				if (procura==reg.getQuarto()){
					JOptionPane.showMessageDialog(null,"Quarto encontrado.\n " + reg.toString());
					if (alugado.equals("true")) {
						resp = JOptionPane.showInputDialog("Deseja desalugar este quarto ?(S/N) ");
						if (resp.toLowerCase().equals("s")) {
							reg.setAlugado(false);
							reg.setHospedeCpf(0);
							memoriaAcomodacoes.replace(inicio, fim+2,reg.toString());
							gravar(memoriaAcomodacoes, acomodacoes);
						} 

					} else {
						JOptionPane.showMessageDialog(null,"Este quarto não está ocupado.");
					}
					achou = true;
				}

				inicio = fim + 2;
			}
			if (achou){
				//System.out.println("\naltera��o de hospedagem realizada com sucesso");
			}else{
				JOptionPane.showMessageDialog(null,"Quarto não encontrado");
			}
		}else{
			JOptionPane.showMessageDialog(null,"Arquivo de "+acomodacoes+" vazio");
		}
	}

}