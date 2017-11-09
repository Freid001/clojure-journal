FROM ubuntu:xenial

LABEL maintainer=freid
ENV WORK_DIR /var/www

# install dependancies
RUN apt-get update
RUN apt-get install -y default-jre curl wget

RUN wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
RUN chmod +x lein
RUN mv lein /usr/local/bin

# copy to /var/www
COPY . $WORK_DIR

# make it a working directory
WORKDIR $WORK_DIR

# fix permissions
RUN chmod a+x /var/www/entrypoint.sh

# expose port 3000
EXPOSE 3000

# make entrypoint
ENTRYPOINT ["/bin/sh", "/var/www/entrypoint.sh"]

# lein ring server

# docker run -it clojure_journal bash
# docker exec -it clojure_journal bash
