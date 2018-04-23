FROM tomcat:9
RUN rm -rf /usr/local/tomcat/webapps/
COPY target/ /usr/local/tomcat/webapps
COPY server.xml /usr/local/tomcat/conf/server.xml
ENTRYPOINT /usr/local/tomcat/bin/catalina.sh run
WORKDIR /usr/local/tomcat/webapps