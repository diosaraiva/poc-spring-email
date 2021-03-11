package com.diogo.springemail.domain;

import java.io.Serializable;

public class Email implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String remetente;
	private String senhaRemetente;

	private String[] destinatarios;

	private String assunto;
	private String mensagem;

	//Getters&Setters

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getSenhaRemetente() {
		return senhaRemetente;
	}

	public void setSenhaRemetente(String senhaRemetente) {
		this.senhaRemetente = senhaRemetente;
	}

	public String[] getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String[] destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}