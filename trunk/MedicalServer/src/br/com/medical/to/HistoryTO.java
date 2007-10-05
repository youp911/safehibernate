package br.com.medical.to;

public final class HistoryTO implements TO {

	private static final long serialVersionUID = -7986415663964595830L;
	
	public String doenca;

	public HistoryTO(String doenca) {
		this.doenca = doenca;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HistoryTO)) {
			return false;
		}
		return ((HistoryTO) obj).doenca.equals(this.doenca);
	}

	@Override
	public String toString() {
		return this.doenca;
	}

}
