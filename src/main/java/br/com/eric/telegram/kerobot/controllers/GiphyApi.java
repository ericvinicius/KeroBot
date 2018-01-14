package br.com.eric.telegram.kerobot.controllers;

import java.lang.reflect.Type;

import com.github.ljtfreitas.restify.http.client.charset.Encoding;
import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Parameters;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.QueryParameter;
import com.github.ljtfreitas.restify.http.contract.metadata.EndpointMethodParameterSerializer;

import br.com.eric.telegram.kerobot.gifhy.models.Response;

@Path("https://api.giphy.com/v1/gifs/")
public interface GiphyApi {

	public static final String TOKEN = "KHRMzcL2YzsQhIU4szBNrXqD9FKgv2Vr";

	@Get
	@Path("/random")
	public Response random(@QueryParameter("rating") String ratingName, @QueryParameter("tag") String tag,
			@QueryParameter(value = "api_key", serializer = ParameterWithoutEncode.class) String api_key);

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

	public class ParameterWithoutEncode implements EndpointMethodParameterSerializer {

		@Override
		@SuppressWarnings("rawtypes")
		public String serialize(String name, Type type, Object source) {
			if (source == null) {
				return null;

			} else if (source instanceof Iterable) {
				return serializeAsIterable(name, (Iterable) source);

			} else {
				return encode(name) + "=" + source.toString();
			}
		}

		private String encode(String value) {
			return Encoding.UTF_8.encode(value);
		}

		@SuppressWarnings("rawtypes")
		private String serializeAsIterable(String name, Iterable source) {
			Parameters parameters = new Parameters();

			for (Object e : source) {
				parameters.put(name, e.toString());
			}

			return parameters.queryString();
		}
	}

}
