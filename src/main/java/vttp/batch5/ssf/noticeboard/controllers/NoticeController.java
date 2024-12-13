package vttp.batch5.ssf.noticeboard.controllers;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping("")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView landingPage() {
        ModelAndView mav = new ModelAndView();
        Notice notice = new Notice();

        mav.setViewName("notice");
        mav.addObject("notice", notice);
        return mav;
    }

    @PostMapping(path = "/notice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView postNotice(@Valid @ModelAttribute Notice notice, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.addObject("notice", notice);
            mav.setViewName("notice");
            mav.setStatus(HttpStatusCode.valueOf(200));
            return mav;
        }

        Instant instant = notice.getPostDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrBuild = Json.createArrayBuilder();
        for (String s : notice.getCategories()) {
            jsonArrBuild.add(s);
        }
        JsonArray JArray = jsonArrBuild.build();
        jsonObject.add("title", notice.getTitle());
        jsonObject.add("poster", notice.getPoster());
        jsonObject.add("postDate", instant.toEpochMilli());
        jsonObject.add("categories", JArray);
        jsonObject.add("text", notice.getText());
        JsonObject Jobject = jsonObject.build();
        System.out.println(Jobject.toString());

        String postingMessage = noticeService.postToNoticeServer(Jobject);
        Reader reader = new StringReader(postingMessage);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonMsgObj = jsonReader.readObject();

        if (jsonMsgObj.getString("id") != null) {
            noticeService.insertNotices(postingMessage);
            String id = jsonMsgObj.getString("id");
            System.out.println(id);
            mav.addObject("id", id);
            mav.setViewName("success");
        } else {
            String message = jsonMsgObj.getString("message");
            mav.addObject("error", message);
            mav.setViewName("error");
        }

        return mav;
    }

    @ResponseBody
    @GetMapping(path="/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHealth(){
        if(!(noticeService.randomKey() == null)){
            return ResponseEntity.ok("{}");
        }else {
            return ResponseEntity.status(503).body("{}");
        }
    }
}

