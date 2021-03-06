package br.com.eric.telegram.kerobot.action.pokemon;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.models.MessageModel;

@Component
public class PokemonAction extends Action {
	
	@Autowired
	private PokemonExecutor pokemonExecutor;

	private final List<String> PATTERNS = Arrays.asList(
			"pokemon # *(?<pokemon>\\d+)"
			);
	
	@Override
	public void execute(MessageModel message, int patternPosition, Matcher matcher) {
		super.info("PokemonAction", "kero like pokemon too!");
		pokemonExecutor.execute(message, matcher.group("pokemon"));
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
