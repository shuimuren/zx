package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.request.PostStringRequest;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostJsonBuilder extends OkHttpRequestBuilder<PostJsonBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostJsonBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostJsonBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
