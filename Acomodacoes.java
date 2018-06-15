package src;
public class Acomodacoes {
		int quarto;
		boolean alugado;
		long hospedeCpf;
		double valor;
		int tempo;
		@Override
		public String toString() {
			return "[Quarto=" + quarto + ";\nAlugado=" + alugado + ";\nHóspede (CPF)=" + hospedeCpf + ";\nValor="
					+ valor + ";\nTempo=" + tempo + "]\n";
		}
		public Acomodacoes(int quarto, boolean alugado, long hospedeCpf, double valor, int tempo) {
			super();
			this.quarto = quarto;
			this.alugado = alugado;
			this.hospedeCpf = hospedeCpf;
			this.valor = valor;
			this.tempo = tempo;
		}

		public int getQuarto() {
			return quarto;
		}
		public void setQuarto(int quarto) {
			this.quarto = quarto;
		}
		public boolean isAlugado() {
			return alugado;
		}
		public void setAlugado(boolean alugado) {
			this.alugado = alugado;
		}
		public long getHospedeCpf() {
			return hospedeCpf;
		}
		public void setHospedeCpf(long cpf) {
			this.hospedeCpf = cpf;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

		public int getTempo() {
			return tempo;
		}

		public void setTempo(int tempo) {
			this.tempo = tempo;
		}
		
		
		
}
