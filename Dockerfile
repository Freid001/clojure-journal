FROM java:8

LABEL maintainer=freid

ARG VERSION
ARG BUILD_TIME
ARG GHASH

ENV VERSION $VERSION
ENV BUILD_TIME $BUILD_TIME
ENV GHASH $GHASH

COPY target /opt/clojure-journal/bin
COPY resources /opt/clojure-journal/bin/resources
COPY entrypoint.sh /opt/clojure-journal/bin/entrypoint.sh

RUN chmod 500 /opt/clojure-journal/bin/entrypoint.sh

WORKDIR /opt/clojure-journal/bin

EXPOSE 3000

ENTRYPOINT ["/bin/sh", "./entrypoint.sh"]