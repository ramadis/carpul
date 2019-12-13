mvn clean package
rm usr/local/Tomcat/webapps/webapp.war
cp /Users/rama/Documents/paw-2017b-06/webapp/target/webapp.war usr/local/Tomcat/webapps/webapp.war
/usr/local/Tomcat/bin/shutdown.sh
/usr/local/Tomcat/bin/startup.sh