FROM tomcat:8-jre8

# Remove default webapps
RUN rm -r /usr/local/tomcat/webapps/*

# And deploy itinerants-webservice.war
COPY target/itinerants-webservice.war /usr/local/tomcat/webapps/ROOT.war

