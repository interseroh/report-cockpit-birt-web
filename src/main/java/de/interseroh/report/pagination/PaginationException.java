package de.interseroh.report.pagination;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class PaginationException extends Throwable {

	private static final long serialVersionUID = 4485371007080326063L;

	public PaginationException() {
	}

	public PaginationException(String message) {
		super(message);
	}

	public PaginationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaginationException(Throwable cause) {
		super(cause);
	}

	public PaginationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
