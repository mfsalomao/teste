package src;
public class Pessoas {
	long cpf;
	String nome;
	char sexo;
	String dataNasc;
	long telefone;
	String contatoEmergencia;
@Override
	public String toString() {
		return "\n[Cpf=" + cpf + ";\nNome=" + nome + ";\nSexo=" + sexo + ";\nData de Nascimento=" + dataNasc + ";\nTelefone="
				+ telefone + ";\nContato de Emergencia=" + contatoEmergencia + "]\n";
	}
public Pessoas(long cpf, String nome, char sexo, String dataNasc, long telefone, String contatoEmergencia) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.contatoEmergencia = contatoEmergencia;
	}
	public long getCpf() {
		return cpf;
	}
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public String getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}
	public String getContatoEmergencia() {
		return contatoEmergencia;
	}
	public void setContatoEmergencia(String contatoEmergencia) {
		this.contatoEmergencia = contatoEmergencia;
	}
	public long getTelefone() {
		return telefone;
	}
	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}

}
