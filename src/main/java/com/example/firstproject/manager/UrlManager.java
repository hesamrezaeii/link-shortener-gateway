package com.example.firstproject.manager;

import com.example.firstproject.mongo.document.Url;
import com.example.firstproject.mongo.repo.UrlRepo;
import com.example.firstproject.request.MakeShortLinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class UrlManager {
    @Autowired
    private UrlRepo urlRepo;

    public Url getOrMakeNewUrl(MakeShortLinkRequest request) {
        Url toReturn = urlRepo.findByOriginalUrl(request.getUrl());
        if (toReturn == null) {
            int random = (int) ((Math.random() * 89999) + (10000));
            Date currentDate = Calendar.getInstance().getTime();
            toReturn = new Url(request.getUrl(), "" + random, currentDate);
        }
        toReturn = urlRepo.save(toReturn);

        return toReturn;
    }
}
