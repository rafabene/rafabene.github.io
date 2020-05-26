---
title: How to run Java fat-jars in Docker, Kubernetes and OpenShift
---

In a world where agility matters, the pursuit to reduce wasted time in environment configurations is apparent in many technologies. Some techniques, such as Virtual Machines, that enable distribution of pre-configured images have existed for decades, while others like [Linux containers](http://developers.redhat.com/containers/) are more recent.

Even platforms like Java allow developers to package all dependencies, resources and configuration files in single [JAR](https://en.wikipedia.org/wiki/JAR_(file_format)) (Java Archive) file. What started initially as way to have executable Java classes in Java SE (Standard Edition), has now gained notoriety also in the Enterprise. The promise to deliver runnable servers in a “fat-jar” that contains not only your application, but also the server runtime and its resources (libraries, datasources, transaction configuration, etc); made projects like [WildFly Swarm](http://wildfly-swarm.io/), Spring Boot and [Vert.x](http://vertx.io/blog/my-first-vert-x-3-application/) become very popular in “Java land”.

Although these projects allow the “packaging” of the server runtime, an elastic environment like “Cloud computing” stands in need of another “layer” of wrapping, and Linux containers are perfect for it. When you wrap your “fat-jar” in a container, you can also provide a custom execution environment for you JAR file that provides an Operational System, the Java Virtual Machine, and it can also be enriched with JMX (Java Management Extensions) that enable easy monitoring of the JVM. You can also set configuration flags that enable debugging, etc.

![](https://rhdevelopers.files.wordpress.com/2016/06/bean-jar.png?w=200)

Today I published another post in [Red Hat Developers Blog](http://developers.redhat.com/blog/2016/06/22/how-to-run-java-fat-jars-in-docker-kubernetes-and-openshift/) that explains my suggested and prefered method for it. [Check it out](http://developers.redhat.com/blog/2016/06/22/how-to-run-java-fat-jars-in-docker-kubernetes-and-openshift/), and register at [Red Hat Developers website](https://developers.redhat.com/) to have access to more related content for Developers.