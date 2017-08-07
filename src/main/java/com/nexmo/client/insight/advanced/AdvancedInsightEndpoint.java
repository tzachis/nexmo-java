/*
 * Copyright (c) 2011-2017 Nexmo Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.nexmo.client.insight.advanced;

import com.nexmo.client.HttpWrapper;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.SignatureAuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.voice.endpoints.AbstractMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AdvancedInsightEndpoint extends AbstractMethod<AdvancedInsightRequest, AdvancedInsightResponse> {
    private static final Class[] ALLOWED_AUTH_METHODS = new Class[]{SignatureAuthMethod.class, TokenAuthMethod.class};

    private static final String DEFAULT_URI = "https://api.nexmo.com/ni/advanced/json";

    private String uri = DEFAULT_URI;

    public AdvancedInsightEndpoint(HttpWrapper httpWrapper) {
        super(httpWrapper);
    }

    public AdvancedInsightEndpoint(HttpWrapper httpWrapper, String baseUri) {
        super(httpWrapper);
        uri = baseUri + "/ni/advanced/json";
    }

    public String getUri() {
        return uri;
    }

    @Override
    protected Class[] getAcceptableAuthMethods() {
        return ALLOWED_AUTH_METHODS;
    }

    @Override
    public RequestBuilder makeRequest(AdvancedInsightRequest request) throws NexmoClientException, UnsupportedEncodingException {
        RequestBuilder requestBuilder = RequestBuilder.post(uri)
                .addParameter("number", request.getNumber());
        if (request.getCountry() != null) {
            requestBuilder.addParameter("country", request.getCountry());
        }
        if (request.getIpAddress() != null) {
            requestBuilder.addParameter("ip", request.getIpAddress());
        }
        if (request.getCnam() != null) {
            requestBuilder.addParameter("cnam", request.getCnam().toString());
        }
        return requestBuilder;
    }

    @Override
    public AdvancedInsightResponse parseResponse(HttpResponse response) throws IOException {
        return AdvancedInsightResponse.fromJson(new BasicResponseHandler().handleResponse(response));
    }
}
