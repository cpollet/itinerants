# All
Once you have the httpd-proxy image (Docker file from [cpollet/docker-images](https://github.com/cpollet/docker-images)):

# OS X
```
itinerants/webapp/devops$ docker run -it --rm --name httpd-itinerants --add-host="host:$(ipconfig getifaddr en1)" -p 0.0.0.0:8000:80 -v "$PWD"/httpd/:/usr/local/apache2/extra httpd-proxy
```

Visit http://docker.local:8000/, where docker.local is the IP address of your docker's VirtualBox.