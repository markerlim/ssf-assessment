package vttp.batch5.ssf.noticeboard.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;

	@Value("${noticeboard.api}")
	private String BASE_URL;

	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type
	public String postToNoticeServer(JsonObject jsonObject) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "";
		if (BASE_URL.isEmpty()) {
			url = "https://publishing-production-d35a.up.railway.app/notice";
		} else {
			url = BASE_URL;
		}

		RequestEntity<String> request = RequestEntity
				.post(URI.create(url))
				.contentType(MediaType.APPLICATION_JSON)
				.body(jsonObject.toString());

		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		return response.getBody();

	}

	public void insertNotices(String payload) {
		noticeRepository.insertNotices(payload);
	}

	public String randomKey() {
		return noticeRepository.randomKey();
	}
}
