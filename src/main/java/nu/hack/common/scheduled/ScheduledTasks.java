package nu.hack.common.scheduled;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nu.hack.offer.dto.OfferCreateRequest;
import nu.hack.offer.service.OfferService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

	private final ParsedOfferRepository parsedOfferRepository;
	private final OfferService offerService;

	private static final Integer halykOfferStart = 230;
	private static final Integer halykOfferEnd = 240;

	private static final String halykBaseUrl = "https://halykbank.kz/promo";

	private static final String halykGptRequest = "from this, if and only if the promo is about bonuses on some category of payments/purchases, make a table entry for table Offers. If the promo is not about bonuses, skip.\nDO NOT include ANYTHING but the JSON entry.\nYour answer should be in the following format:\n{\n\"bankCardId\": Int,\n\"categoryId\": Int,\n\"percentage\": float,\n\"conditions\": String(1024). Leave the text in Russian here,\n\"dateFrom\": Date in format YYYY-MM-DD,\n\"dateTo\": Date in format YYYY-MM-DD\n}\nThe categories are:\n1,Clothing\n2,Taxi\n3,Food Delivery\n4,Restaraunts\n5,Fuel stations\n6,Beauty salons\n7,Supermarkets\n8,Travel\n9,Mobile connection\nIf the bonuses are given for ANY purchase, just leave categoryId to be null. If a category isn't present, just insert a random value from 1 to 9.\nBankCardId:\n14,Halyk Bonus\n15,Diamond Card\nIf bonuses are given to only one of those cards, set bankCardId to 14 or 15 respectively.\nIf bonuses are given to any card (or the promo doesn't specify it), make 2 json entry for each bankCardId.";

	private static final String systemRequest = "You are an expert in banking bonuses and promos. You can also read links. You ONLY send json replies without additional explanations ONLY WITH FIELDS bankCardId, categoryId, percentage, conditions, dateFrom, dateTo";

	@Scheduled(cron = "0 0 1 * * SUN")
	public void parseOffers() {
		//todo: fetch offers
		ArrayList<JSONObject> answers = new ArrayList<>();
		var offerNumber = halykOfferStart;
		while (offerNumber <= halykOfferEnd) {
			var url = String.format("%s/%d", halykBaseUrl, offerNumber);
			log.info("Processing base url {}", url);
			if (parsedOfferRepository.existsByLink(url)) {
				log.info("Base url {} already processed, skipping", url);
				offerNumber++;
				continue;
			}
			var prompt = String.format("%s %s", url, halykGptRequest);
			var answer = chatGPT(prompt);
			try {
				JSONObject j = new JSONObject(answer);
				answers.add(j);
				var parsedOffer = new ParsedOfferEntity(url, true, null);
				parsedOffer = parsedOfferRepository.save(parsedOffer);
				log.info("Parsed url {} successfully, ID {}", url, parsedOffer.getId());
			} catch (JSONException e) {
				try {
					Pattern pattern = Pattern.compile("\\{.*?\\}", Pattern.DOTALL);
					Matcher matcher = pattern.matcher(answer);
					while (matcher.find()) {
						JSONObject js = new JSONObject(matcher.group());
						answers.add(js);
					}
					var parsedOffer = new ParsedOfferEntity(url, true, null);
					parsedOffer = parsedOfferRepository.save(parsedOffer);
					log.info("Parsed url {} successfully, ID {}", url, parsedOffer.getId());
				} catch (JSONException a) {
					System.out.println("Exception occurred for request Number " + offerNumber);
					var parsedOffer = new ParsedOfferEntity(url, false, e.getMessage());
					parsedOffer = parsedOfferRepository.save(parsedOffer);
					log.error("Could not parse url {}, ID {}", url, parsedOffer.getId());
				}
			}
			offerNumber++;
		}
		createOffers(answers);
	}

	public static String chatGPT(String prompt) {
		String url = "https://api.openai.com/v1/chat/completions";
		String apiKey = "YOUR_API_KEY";
		String model = "gpt-4-turbo-2024-04-09";

		try {
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + apiKey);
			connection.setRequestProperty("Content-Type", "application/json");

			// The request body
			String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"system\", \"content\": \"" + StringEscapeUtils.escapeJson(systemRequest) + "\"}, {\"role\": \"user\", \"content\": \"" + StringEscapeUtils.escapeJson(prompt) + "\"}]}";
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(body);
			writer.flush();
			writer.close();

			// Response from ChatGPT
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			StringBuffer response = new StringBuffer();

			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();

			// calls the method to extract the message.

			return extractMessageFromJSONResponse(response.toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String extractMessageFromJSONResponse(String response) {
		int start = response.indexOf("content") + 11;

		int end = response.indexOf("\"", start);

		var res = response.substring(start, end);
		log.info("Response from chatgpt is {}", res);
		return res;
	}

	public void createOffers(List<JSONObject> jsonObjects) {
		log.info("Started creating offers");

		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		objectMapper.registerModule(module);

		for (JSONObject jsonObject : jsonObjects) {
			try {
				OfferCreateRequest offerCreateRequest = objectMapper.readValue(jsonObject.toString(), OfferCreateRequest.class);
				offerService.create(offerCreateRequest);
				log.info("Successfully saved an offer");
			} catch (IOException e) {
				log.error("Some error occured {}", e.getMessage()); // Handle the exception appropriately
			}
		}
	}

	static class LocalDateDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, com.fasterxml.jackson.databind.DeserializationContext deserializationContext) throws IOException {
			String date = jsonParser.getText();
			return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
		}
	}
}