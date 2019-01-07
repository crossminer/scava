FROM tomcat:9.0.14-jre8-slim

COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
COPY org.eclipse.scava.crossflow.web.war /usr/local/tomcat/webapps/
# ADD https://dist.apache.org/repos/dist/release/activemq/5.15.8/apache-activemq-5.15.8-bin.tar.gz activemq.tar.gz
COPY apache-activemq-5.15.8-bin.tar.gz activemq.tar.gz

RUN set -x \
	&& tar -zxf activemq.tar.gz \
	&& mv apache-activemq-5.15.8 ../activemq \
	&& rm -rf apache-activemq-5.15.8

COPY activemq.xml /usr/local/activemq/conf/activemq.xml
COPY jetty-realm.properties /usr/local/activemq/conf/jetty-realm.properties

CMD /usr/local/activemq/bin/activemq start && catalina.sh run