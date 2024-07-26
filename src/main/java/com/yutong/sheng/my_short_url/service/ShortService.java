package com.yutong.sheng.my_short_url.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yutong.sheng.my_short_url.PO.SurlPO;
import com.yutong.sheng.my_short_url.common.ApiResponse;
import org.springframework.web.servlet.view.RedirectView;

public interface ShortService extends IService<SurlPO> {
    ApiResponse<String> createShortUrl(String url);

    RedirectView redirect(String key);
}
