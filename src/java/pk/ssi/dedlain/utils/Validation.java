package pk.ssi.dedlain.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Validation {

	public static boolean isBlank(String string) {
		return string == null || string.trim().isEmpty();
	}

	public static boolean isEmail(String string) {
		final Pattern regexp= Pattern.compile(
        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
						+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		return regexp.matcher(string).matches();
	}
	
	public static class Errors extends Throwable {
		protected Map<String, ArrayList<String>> errors = new HashMap<String, ArrayList<String>>();

		public Errors(Map<String, ArrayList<String>> errors) {
			this.errors = errors;
		}

		public Errors() {
		}

		public Errors add(String field, String message) {
			ArrayList<String> fieldErrors = get(field);
			if (fieldErrors == null) {
				fieldErrors = new ArrayList<String>();
				errors.put(field, fieldErrors);
			}
			fieldErrors.add(message);
			return this;
		}

		public ArrayList<String> get(String field) {
			return errors.get(field);
		}

		public boolean any() {
			return !errors.isEmpty();
		}
		
	}
}