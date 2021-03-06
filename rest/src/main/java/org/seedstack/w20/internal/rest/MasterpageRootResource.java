/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.w20.internal.rest;

import com.google.inject.Inject;
import org.seedstack.seed.rest.spi.RootResource;
import org.seedstack.w20.internal.MasterPageBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class MasterpageRootResource implements RootResource {
    @Inject
    private MasterPageBuilder masterPageBuilder;

    @Override
    public Response buildResponse(HttpServletRequest httpServletRequest, UriInfo uriInfo) {
        if (!httpServletRequest.getRequestURI().endsWith("/")) {
            String queryString = httpServletRequest.getQueryString();

            if (queryString != null) {
                return Response.status(302).header(HttpHeaders.LOCATION, httpServletRequest.getRequestURI() + "/" + queryString).build();
            } else {
                return Response.status(302).header(HttpHeaders.LOCATION, httpServletRequest.getRequestURI() + "/").build();
            }
        } else {
            return Response.ok(masterPageBuilder.build(httpServletRequest)).type(MediaType.TEXT_HTML_TYPE).build();
        }
    }
}
