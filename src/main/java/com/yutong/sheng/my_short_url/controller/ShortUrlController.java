package com.yutong.sheng.my_short_url.controller;

import com.yutong.sheng.my_short_url.common.ApiResponse;
import com.yutong.sheng.my_short_url.service.ShortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
@RestController
public class ShortUrlController {

    @Resource
    private ShortService shortUrlService;

    @GetMapping("/createShortUrl")
    public ApiResponse<String> createShortUrl(String url) {
        return shortUrlService.createShortUrl(url);
    }

    @GetMapping(value="/{key}")
    public RedirectView redirect (@PathVariable String key) {
        return shortUrlService.redirect(key);
    }
}
