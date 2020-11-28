package es.uclm.esi.utilities;

import org.apache.commons.lang3.StringUtils;

/**
 * La clase contiene distintas comprobaciones para la contrasena. Se ha
 * programado de tal manera que se puedan ir llamando a los metodos de uno en
 * uno, devolviendo true en caso de que cumplan los requisitos y false en caso
 * de que no para asi poder informar del fallo concreto de la contrasena al
 * usuario.
 * 
 * @author German
 *
 */

public class PoliticaContraSegura {

	private PoliticaContraSegura() {
		throw new IllegalStateException("Clase de utilidades");
	}

	/**
	 * Metodo para comprobar que la contrasena tiene más de 8 caracteres
	 * 
	 * @author German
	 * @param String contrasena a comprobar
	 * @return boolean, true en caso de que cumpla el requisito, false en caso
	 *         contrario
	 */

	public static boolean caracteres(String contra) {
		return contra.length() > 8;
	}

	/**
	 * Metodo para comprobar que la contrasena tiene, al menos, un número
	 * 
	 * @author German
	 * @param String contrasena a comprobar
	 * @return boolean, true en caso de que cumpla el requisito, false en caso
	 *         contrario
	 */

	public static boolean numero(String contra) {
		for (int i = 0; i < contra.length(); i++) {
			if (esNumerico(String.valueOf(contra.charAt(i))))
				return true;
		}
		return false;
	}

	/**
	 * Metodo para comprobar si es numerico
	 * 
	 * @param String contrasena a comprobar
	 * @return boolean, true en caso de que cumpla el requisito, false en caso
	 *         contrario
	 */

	public static boolean esNumerico(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para comprobar que tiene, al menos, una mayuscula
	 * 
	 * @author German
	 * @param String contrasena a comprobar
	 * @return boolean, true en caso de que cumpla el requisito, false en caso
	 *         contrario
	 */

	public static boolean mayuscula(String contra) {
		for (int i = 0; i < contra.length(); i++)
			if (Character.isUpperCase(contra.charAt(i)))
				return true;
		return false;
	}

	/**
	 * Metodo para comprobar que tiene, al menos, una minúscula
	 * 
	 * @author German
	 * @param String contrasena a comprobar
	 * @return boolean, true en caso de que cumpla el requisito, false en caso
	 *         contrario
	 */

	public static boolean minuscula(String contra) {
		for (int i = 0; i < contra.length(); i++)
			if (Character.isLowerCase(contra.charAt(i)))
				return true;
		return false;
	}

	/**
	 * Metodo para comprobar que solo tiene caracteres alfanumericos sin espacios.
	 * En caso de querer espacios podría usar isAlphanumericSpace().
	 * 
	 * @author German
	 * @param String contrasena a comprobar
	 * @return boolean, true en caso de que cumpla el requisito, false en caso
	 *         contrario
	 */

	public static boolean alfanum(String contra) {
		return StringUtils.isAlphanumeric(contra);
	}
}