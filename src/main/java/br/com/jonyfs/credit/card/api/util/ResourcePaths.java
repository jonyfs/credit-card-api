package br.com.jonyfs.credit.card.api.util;

public class ResourcePaths {
	public static final String API = "api";
	public static final String ROOT_API = "/" + API;
	public static final String ID = "/{id}";

	public class Payment {
		public static final String NAME = "/payments";
		public static final String ROOT = ROOT_API + NAME;
		public static final String GET = ROOT_API + NAME + ID;
	}
}
