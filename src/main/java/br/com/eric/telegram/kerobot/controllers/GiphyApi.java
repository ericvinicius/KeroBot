package br.com.eric.telegram.kerobot.controllers;

import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.QueryParameter;

import br.com.eric.telegram.kerobot.gifhy.models.Response;

@Path("https://api.giphy.com/v1/gifs/")
public interface GiphyApi {

	public static final String TOKEN = "KHRMzcL2YzsQhIU4szBNrXqD9FKgv2Vr";

	@Get
	@Path("/random")
	public Response random(@QueryParameter("rating") String ratingName, @QueryParameter("tag") String tag, @QueryParameter("api_key") String api_key);

	public enum Rating {
		Y("Y"), G("G"), PG("PG"), PG13("PG-13"), R("R");

		private String name;

		private Rating(String name) {
			this.setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
