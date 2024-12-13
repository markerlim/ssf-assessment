package vttp.batch5.ssf.noticeboard.repositories;

import java.io.Reader;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type
	//
	/*
	 * Write the redis-cli command that you use in this method in the comment.
	 * For example if this method deletes a field from a hash, then write the
	 * following
	 * redis-cli command
	 * hdel myhashmap a_key
	 *
	 *
	 */
	@Autowired
	@Qualifier("notice")
	private RedisTemplate<String, Object> redisTemplate;

	// set key field value
	public void insertNotices(String payload) {
		Reader reader = new StringReader(payload);
		JsonReader jsonReader = Json.createReader(reader);
		JsonObject jsonMsgObj = jsonReader.readObject();
		System.out.println(jsonMsgObj.toString());
		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
		valueOps.set(jsonMsgObj.getString("id"), jsonMsgObj.toString());
	}

	//randomKey
	public String randomKey() {

			return redisTemplate.randomKey();
	}

}
