package br.com.eric.telegram.kerobot.action;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eric.telegram.kerobot.action.goodkero.GoodKeroAction;
import br.com.eric.telegram.kerobot.action.hours.HoursAction;
import br.com.eric.telegram.kerobot.action.pokemon.PokemonAction;
import br.com.eric.telegram.kerobot.action.reminder.ReminderAction;
import br.com.eric.telegram.kerobot.action.reminder.delete.DeleteReminderAction;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Service
public class Executor {

	@Autowired
	private ReminderAction reminderAction;

	@Autowired
	private GoodKeroAction goodKeroAction;

	@Autowired
	private UpdateRegister updateRegister;

	@Autowired
	private DeleteReminderAction deleteReminderAction;

	@Autowired
	private PokemonAction pokemonAction;

	@Autowired
	private HoursAction hoursAction;

	private List<Action> textActions;

	private static final Logger logger = LogManager.getLogger(Executor.class);

	@PostConstruct
	public void init() {
		textActions = Arrays.asList(reminderAction, goodKeroAction, deleteReminderAction, pokemonAction, hoursAction);
	}

	public void execute(Update update) {
		updateRegister.validateMessage(update).ifPresent(message -> {
			logger.info("[MESSAGE] " + update.toJson());
			updateRegister.register(message);
			checkAndDo(update);
		});
	}

	private Boolean checkAndDo(Update update) {
		return update.getIfTextExists().map(up -> {
			return doAction(up, textActions);
		}).orElse(false);
	}

	private String getBetterText(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
	}

	private Boolean doAction(Update update, List<Action> actions) {
		String goodTxt = getBetterText(update.getText().get());
		for (Action action : actions) {
			List<String> patterns = action.getPatterns();
			for (int i = 0; i < patterns.size(); i++) {
				String pattern = patterns.get(i);
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(goodTxt);
				if (m.find()) {
					action.execute(update, i, m);
					return true;
				}
			}
		}
		return false;
	}

}
