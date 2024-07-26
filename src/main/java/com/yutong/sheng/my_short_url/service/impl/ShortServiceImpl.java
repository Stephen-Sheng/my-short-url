package com.yutong.sheng.my_short_url.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.hash.Hashing;
import com.yutong.sheng.my_short_url.PO.SurlPO;
import com.yutong.sheng.my_short_url.common.ApiResponse;
import com.yutong.sheng.my_short_url.common.Base62Converter;
import com.yutong.sheng.my_short_url.common.BloomFilter;
import com.yutong.sheng.my_short_url.mapper.SurlMapper;
import com.yutong.sheng.my_short_url.service.ShortService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * @author yutongsheng
 */
@Service
public class ShortServiceImpl extends ServiceImpl<SurlMapper,SurlPO> implements ShortService {
    @Override
    public ApiResponse<String> createShortUrl(String url) {
        long hash = Hashing.murmur3_32_fixed().hashUnencodedChars(url).padToLong();
        String convertToBase62 = Base62Converter.convertToBase62(hash);
        String uniqueBase62 = checkUniqueHash(convertToBase62);
        if (StringUtils.isNotBlank(uniqueBase62)) {
            SurlPO surlPO = new SurlPO();
            surlPO.setLongUrl(url);
            surlPO.setShortUrl(uniqueBase62);
            BloomFilter.add(uniqueBase62);
            this.save(surlPO);
            return ApiResponse.ok(uniqueBase62);
        }
        return ApiResponse.ok(null);
    }

    /**
     *
     */
    public String checkUniqueHash(String base62) {
        // 检查布隆过滤器
        if (BloomFilter.contains(base62)) {
            // 如果在布隆过滤器中存在，查询数据库确定是否真的存在
            SurlPO surlPO = this.getOne(new LambdaQueryWrapper<SurlPO>().select(SurlPO::getLongUrl).eq(SurlPO::getShortUrl, base62));
            if (ObjectUtils.isNotEmpty(surlPO)) {
                // 如果数据库中存在，则生成新的62进制字符串
                String newBase62 = base62 + RandomStringUtils.randomAlphabetic(1);
                // 再次检查新生成的字符串，如果是递归调用，这将返回新生成的字符串
                return checkUniqueHash(newBase62);
            }
        }
        // 如果不存在或者是首次调用，返回原始的base62字符串
        return base62;
    }

    @Override
    public RedirectView redirect(String key) {
        SurlPO surlPO = this.getOne(new LambdaQueryWrapper<SurlPO>().select(SurlPO::getLongUrl).eq(SurlPO::getShortUrl, key));
        if (ObjectUtils.isNotEmpty(surlPO)) {
            return new RedirectView(surlPO.getLongUrl());
        }
        return null;
    }
}
