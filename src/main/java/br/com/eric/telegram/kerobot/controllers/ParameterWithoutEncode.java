package br.com.eric.telegram.kerobot.controllers;

import java.lang.reflect.Type;

import com.github.ljtfreitas.restify.http.client.charset.Encoding;
import com.github.ljtfreitas.restify.http.contract.Parameters;
import com.github.ljtfreitas.restify.http.contract.metadata.EndpointMethodParameterSerializer;

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