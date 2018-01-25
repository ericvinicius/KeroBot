package br.com.eric.telegram.kerobot.action.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.models.MessageModel;
import br.com.eric.telegram.kerobot.telegram.TelegramApiExecutor;
import br.com.eric.telegram.kerobot.util.StringUtil;

@Component
public class PokemonExecutor {

	@Autowired
	private TelegramApiExecutor botApi;

	public void execute(MessageModel message, String pokemon) {
		String url = "";
		int pokmeonNumber = Integer.parseInt(pokemon);
		url = send(pokmeonNumber);
		botApi.sendPhoto(message.getChat().getId(), url);
	}

	private String send(int pokmeonNumber) {
		String digits = StringUtil.fixNumberWith3Digits(pokmeonNumber);
		String generation = getGeneration(pokmeonNumber);
		return "https://github.com/LazoCoder/Pokemon-Terminal/raw/master/pokemonterminal/Images/" + generation + "/"
				+ digits + ".jpg";
	}

	public String getGeneration(int pokemonNumber) {
		if (pokemonNumber <= 151) {
			return "Generation I - Kanto";
		} else if (pokemonNumber <= 251) {
			return "Generation II - Johto";
		} else if (pokemonNumber <= 386) {
			return "Generation III - Hoenn";
		} else if (pokemonNumber <= 493) {
			return "Generation IV - Sinnoh";
		} else if (pokemonNumber <= 649) {
			return "Generation V - Unova";
		} else {
			return "Generation VI - Kalos";
		}
	}

}
