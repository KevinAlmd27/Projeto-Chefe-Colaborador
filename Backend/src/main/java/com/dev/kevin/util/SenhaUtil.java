package com.dev.kevin.util;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {

	public static String validacaoSenha(String password) {
		if (password == null || password.isEmpty()) {
			return "A senha não pode estar vazia.";
		}

		int score = 0;
		boolean lengthValid = password.length() >= 8;
		boolean hasUpperCase = Pattern.compile("[A-Z]").matcher(password).find();
		boolean hasLowerCase = Pattern.compile("[a-z]").matcher(password).find();
		boolean hasDigit = Pattern.compile("\\d").matcher(password).find();
		boolean hasSpecialChar = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]").matcher(password).find();

		if (lengthValid)
			score++;
		if (hasUpperCase)
			score++;
		if (hasLowerCase)
			score++;
		if (hasDigit)
			score++;
		if (hasSpecialChar)
			score++;

		return getPasswordStrengthMessage(score, lengthValid, hasUpperCase, hasLowerCase, hasDigit, hasSpecialChar);
	}

	private static String getPasswordStrengthMessage(int score, boolean lengthValid, boolean hasUpperCase,
			boolean hasLowerCase, boolean hasDigit, boolean hasSpecialChar) {
		if (score == 5)
			return "Senha muito forte.";
		if (score == 4)
			return "Senha forte.";
		if (score == 3)
			return "Senha moderada.";

		StringBuilder message = new StringBuilder("Senha fraca. Faltando: ");
		if (!lengthValid)
			message.append("mínimo 8 caracteres, ");
		if (!hasUpperCase)
			message.append("letra maiúscula, ");
		if (!hasLowerCase)
			message.append("letra minúscula, ");
		if (!hasDigit)
			message.append("número, ");
		if (!hasSpecialChar)
			message.append("caractere especial, ");

		return message.substring(0, message.length() - 2) + ".";

	}

	// Método para criptografar uma senha
	public static String criptografiaSenha(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12 é o fator de custo
	}

}
