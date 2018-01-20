package br.com.eric.telegram.kerobot.action.goodkero;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eric.telegram.kerobot.action.Action;
import br.com.eric.telegram.kerobot.telegram.models.Update;

@Component
public class GoodKeroAction extends Action {
	
	@Autowired
	private GoodKeroExecutor goodKeroExecutor;

	private final List<String> PATTERNS = Arrays.asList(
			"(((kero+|@?cutekerobot) +)?(seu? +)?(lind(o+|inho+)|fof(o+|inho+)|gat(o+|inho+))((kero+|@?cutekerobot))?)",
			"(((kero+|@?cutekerobot) +)?(((muito+ +)?obrigad(inh)?(o+|a+))|(vale(u+|w+)|vlw+)|(thank(s+)?|tks+))((kero+|@?cutekerobot))?)",
			"^(?!.*( nao | nunca | no )).*(?<word>bom|boa) (?<time>dia|tarde|noite).*"
			);
	
	@Override
	public void execute(Update update, int patternPosition, Matcher matcher) {
		super.info("GoodKeroAction", "kero is a good boy!");
		
		switch (patternPosition) {
		case 0:
			goodKeroExecutor.compliment(update);
			break;
		case 1:
			goodKeroExecutor.thanks(update);
			break;
		case 2:
			goodKeroExecutor.goodTime(update, matcher.group("word") + " " + matcher.group("time"));
		}
		
	}

	@Override
	public List<String> getPatterns() {
		return PATTERNS;
	}

}
