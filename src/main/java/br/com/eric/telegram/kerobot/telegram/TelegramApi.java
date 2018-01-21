package br.com.eric.telegram.kerobot.telegram;

import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.QueryParameter;

import br.com.eric.telegram.kerobot.telegram.models.MessageResponse;

@Path("https://api.telegram.org/")
public interface TelegramApi {

	// https://api.telegram.org/bot480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs/getWebhookinfo
	// keytool -genkey -keyalg RSA -alias 64.137.242.226 -keystore keystore.jks
	// -storepass 50116658 -validity 360 -keysize 2048
	// keytool -importkeystore -srckeystore keystore.jks -destkeystore
	// keystore.p12 -srcstoretype jks -deststoretype pkcs12
	// openssl pkcs12 -in keystore.p12 -out keystore.pem -nokeys
	// Edit application.properties
	// curl -i -X POST -H "Content-Type: multipart/form-data" -F
	// "certificate=@keys/keystore.pem"
	// https://api.telegram.org/bot480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs/setWebhook\?url\=https://64.137.242.226:8443/webhook
	public final String TOKEN = "480394771:AAEXAhXgyzaZPpCBNsdOreSxsclgNNmofCs";
	public final Integer ADMIN_CHAT_ID = 174439923;

	@Get
	@Path("/bot" + TOKEN + "/sendMessage")
	public MessageResponse sendMessage(@QueryParameter("chat_id") Integer chat_id, @QueryParameter("text") String text);

	@Get
	@Path("/bot" + TOKEN + "/sendVideo")
	public MessageResponse sendVideo(@QueryParameter("chat_id") Integer chat_id,
			@QueryParameter(value = "video") String videoURL);

	@Get
	@Path("/bot" + TOKEN + "/sendPhoto")
	public MessageResponse sendPhoto(@QueryParameter("chat_id") Integer chat_id,
			@QueryParameter(value = "photo") String photoURL);
}
