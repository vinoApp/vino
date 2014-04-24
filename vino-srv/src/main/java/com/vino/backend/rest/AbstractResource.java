package com.vino.backend.rest;

import com.google.common.base.Optional;
import com.vino.backend.model.Response;

public abstract class AbstractResource {

    public Response ok() {
        return Response
                .withStatuses(Optional.of(Response.TechnicalStatus.OK), Optional.of(Response.BusinessStatus.OK))
                .build();
    }

    public Response technical(Response.TechnicalStatus status, String message) {
        return Response
                .withStatuses(Optional.of(status), Optional.<Response.BusinessStatus>absent())
                .withMessage(message)
                .build();
    }

    public Response technical(Response.TechnicalStatus status) {
        return Response
                .withStatuses(Optional.of(status), Optional.<Response.BusinessStatus>absent())
                .build();
    }

    public Response business(Response.BusinessStatus status, String message) {
        return Response
                .withStatuses(Optional.<Response.TechnicalStatus>absent(), Optional.of(status))
                .withMessage(message)
                .build();
    }

    public Response business(Response.BusinessStatus status) {
        return Response
                .withStatuses(Optional.<Response.TechnicalStatus>absent(), Optional.of(status))
                .build();
    }

    public Response business(boolean result) {
        return Response
                .withStatuses(
                        Optional.<Response.TechnicalStatus>absent(),
                        Optional.of(result ? Response.BusinessStatus.OK : Response.BusinessStatus.ERROR)
                )
                .build();
    }
}
