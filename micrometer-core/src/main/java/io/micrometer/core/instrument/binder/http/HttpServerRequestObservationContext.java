/*
 * Copyright 2020 VMware, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.core.instrument.binder.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.common.lang.Nullable;
import io.micrometer.observation.transport.RequestReplyReceiverContext;

/**
 * Context that holds information for metadata collection regarding Servlet HTTP requests
 * observations.
 * <p>
 * This context also extends {@link RequestReplyReceiverContext} for propagating tracing
 * information during HTTP request processing.
 *
 * @author Brian Clozel
 * @author Marcin Grzejszczak
 * @since 1.12.0
 */
public class HttpServerRequestObservationContext
        extends RequestReplyReceiverContext<HttpServletRequest, HttpServletResponse> {

    @Nullable
    private String pathPattern;

    public HttpServerRequestObservationContext(HttpServletRequest request, HttpServletResponse response) {
        super(HttpServletRequest::getHeader);
        setCarrier(request);
        setResponse(response);
    }

    @Nullable
    public String getPathPattern() {
        return this.pathPattern;
    }

    public void setPathPattern(@Nullable String pathPattern) {
        this.pathPattern = pathPattern;
    }

}