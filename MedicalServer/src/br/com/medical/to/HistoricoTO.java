package br.com.medical.to;

public final class HistoricoTO implements TO {

	private static final long serialVersionUID = -7986415663964595830L;
	
	public String doenca;

	public HistoricoTO(String doenca) {
		this.doenca = doenca;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HistoricoTO)) {
			return false;
		}
		return ((HistoricoTO) obj).doenca.equals(this.doenca);
	}

	@Override
	public String toString() {
		return this.doenca;
	}

}
