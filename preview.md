---
layout: page
title: JavaOne 2015 - Docker for JavaEE Developer
comments: true
---

[Docker](http://www.docker.com/) has been attracting attentions from the whole IT sector. The hability to create and distribute images easily and then execute containers fast made Docker become popular although the Container technology and concept not being something new.

Nowadays with many companies betting in the Cloud and also with microservices becoming a very popular "way" of architecturing applications, Docker seems to be the glue between them: Create a piece of software/service/process that can be easily created/distributed/upgraded/destroyed is something that can be extremelly risk and expensive if you don't use any kind o virtualization. But even distributing a Virtual Machine image can be expensive if we considere a Virtual Machine image size and the CPU and [memory footprint](https://en.wikipedia.org/wiki/Memory_footprint) to execute it.

On the other hand, Docker allows you to ["package an application with all of its dependencies into a standardized unit for software development"](http://www.docker.com/what-docker) called container. If you look the image bellow, you can see how it differs from a Virtual Machine and understand why Docker is so fast and lightweight.

![](/images/docker-containers-vms.png)

During the [JavaOne](https://www.oracle.com/javaone/index.html), [Markus Eisele](http://blog.eisele.net/) and I were pleased to present a *HOL* (Hands on Lab) to the Java Community. This HOL is targetted to Java EE developers who wants to learn more about Docker and how they can use it in a Java EE context. It covers from Docker basics to how to create a Docker cluster with Docker Swarm. You can have an overview of the content by watching this video:

<iframe width="560" height="315" src="https://www.youtube.com/embed/pxVPkfT8DKo" frameborder="0" allowfullscreen></iframe>

The lab was a amazing!  There were 80 people and yet a long line to join it.

![](https://pbs.twimg.com/media/CSb4YbTUYAAK121.jpg)

If everything that you read and saw until now increased your will to learn Docker, I have good news for you! The "Docker for Java EE Developers lab" can be executed in your home too. Simply go to [http://bit.ly/DockerJavaEE](http://bit.ly/DockerJavaEE) and have fun!!!

We love feedback! 

You can find the github repository with the sources of this Lab at: [https://github.com/redhat-developer/docker-java](https://github.com/redhat-developer/docker-java)

If you loved it, please share, comment, contribute! Help the community to be "Dockerized" :) - [http://bit.ly/DockerJavaEE](http://bit.ly/DockerJavaEE) 