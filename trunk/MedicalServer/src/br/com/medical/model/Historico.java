package br.com.medical.model;

import java.io.Serializable;

import br.com.safehibernate.interceptor.SafeField;

public class Historico implements Serializable {

	private static final long serialVersionUID = -7415123767728136038L;

	private Integer codigo;
	
	@SafeField
	private String doenca;

	public String getDoenca() {
		return this.doenca;
	}

	public void setDoenca(String doenca) {
		this.doenca = doenca;
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}
