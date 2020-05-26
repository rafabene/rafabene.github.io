---
title: Four different approaches to run WildFly Swarm in OpenShift
toc: true
---

This post was originally posted on [Red Hat Developers](http://developers.redhat.com/blog/2016/06/29/four-different-approaches-to-run-wildfly-swarm-in-openshift/).

[WildFly Swarm 1.0.0.Final](http://wildfly-swarm.io/) was released this week at DevNation. It allows the developer to package his application and a JavaEE runtime in a “fat-[jar](https://en.wikipedia.org/wiki/JAR_(file_format))” file. To execute the application, the developer will only need a [Java SE Runtime](http://openjdk.java.net/) installed and have access to the “fat-jar”. No other downloads or configurations are needed.

Besides being a well known (and consolidated) Java EE runtime, WildFly Swarm is also an excellent choice for Cloud-native Java apps through the “built-in support for third party apps and frameworks like [Logstash](https://www.elastic.co/products/logstash) and [NetFlix OSS](https://netflix.github.io/) projects like [Hystrix](https://github.com/Netflix/Hystrix) and [Ribbon](https://github.com/Netflix/Ribbon)“.

[OpenShift v3](https://www.openshift.com/) is [Red Hat](https://developers.redhat.com/)‘s [PaaS](https://en.wikipedia.org/wiki/Platform_as_a_service) based on [Linux containers](http://developers.redhat.com/containers/) and [Kubernetes](http://kubernetes.io/). It’s an amazing cloud platform that is capable of managing containers based on pre-existing Docker images or creating images from the source-code of your application in a process called [S2I](https://github.com/openshift/source-to-image) (Source-to-image).

This post will show 4 different approaches to deploy a [WildFly Swarm](http://wildfly-swarm.io/) application in [OpenShift v3](https://www.openshift.com/).


## CDK – Container Development Kit

{: .center}
![](https://rhdevelopers.files.wordpress.com/2016/06/images_icons_products_products_tools-5.png?w=104&h=103)

To explore these approaches, the developer should be able to have access to an OpenShift 3 environment. The [Red Hat Developers community](https://developers.redhat.com/) provides the [Red Hat Container Development Kit](http://developers.redhat.com/products/cdk/overview/) that is basically a [Vagrant](https://www.vagrantup.com/) box that creates a virtualized environment with Docker, Kubernetes and OpenShift running in [RHEL (Red Hat Enterprise Linux)](http://developers.redhat.com/products/rhel/overview/).

If you want to try these 4 approaches yourself, go to the [CDK Getting started page](http://developers.redhat.com/products/cdk/get-started/) and follow its instructions to install CDK and [download the CLI](https://access.redhat.com/downloads/content/290).

Login in OpenShift and create a new empty project.

{% highlight bash %}
$ oc login 10.1.2.2 -u openshift-dev -p devel
Login successful.


Using project "sample-project".

$ oc new-project wildflyswarm
Now using project "wildflyswarm" on server "https://10.1.2.2:8443".
{% endhighlight %}


## The example application

As an example app, we will use the “hello world” WildFly-Swarm microservice called “hola” that returns “hello world” in Spanish.  This example also shows how to integrate with [Hystrix](https://github.com/Netflix/Hystrix), [Feign](https://github.com/OpenFeign/feign), [Zipkin](http://zipkin.io/) and [Swagger](http://swagger.io/).

**Obs**: *For a complete MSA (Microservices Architecture) example that integrates technologies like WildFly Swarm, Spring-boot, Vert.x and NodeJS, browse the documentation available at:  [https://github.com/redhat-helloworld-msa/helloworld-msa](https://github.com/redhat-helloworld-msa/helloworld-msa)*

The source-code for the “hola” microservice is available in the following Github repository: [https://github.com/redhat-helloworld-msa/hola](https://github.com/redhat-helloworld-msa/hola). Clone it to your workspace.

{% highlight bash %}
$ git clone https://github.com/redhat-helloworld-msa/hola
{% endhighlight %}

### Approach 1 – S2I (Source-to-image)

{: .center}
![](https://rhdevelopers.files.wordpress.com/2014/09/openshift-logo-121c2a0c397c2a0121.jpeg?w=640)


This approach requires a [builder image](https://github.com/wildfly-swarm/sti-wildflyswarm) provided by the WildFly Swarm community. Let’s install it using the following command:

{% highlight bash %}
$ oc create -f https://raw.githubusercontent.com/wildfly-swarm/sti-wildflyswarm/master/1.0/wildflyswarm-sti-all.json
buildconfig "wildflyswarm-10-centos7-build" created
imagestream "swarm-centos" created
imagestream "wildflyswarm-10-centos7" created
{% endhighlight %}

**OBS**: This builder image is only available in the “wildflyswarm” project. To make it available to all projects, the resources should be installed under with administrative privileges under an internal “namespace/project” called “openshift”.

You can follow the build process of the builder image with the following command:
{% highlight bash %}
$ oc logs bc/wildflyswarm-10-centos7-build -f
...
Successfully built 3bffc608e3fa
I0619 06:34:21.276883 1 docker.go:93] Pushing image 172.30.67.31:5000/wildflyswarm/wildflyswarm-10-centos7:latest ...
I0619 06:36:08.596055 1 docker.go:97] Push successful
{% endhighlight %}

Once the build is complete, you finally an OpenShift application (we called it as “approach1”) using this Builder image with the following command:

{% highlight bash %}
$ oc new-app --name approach1 wildflyswarm-10-centos7~https://github.com/redhat-helloworld-msa/hola
...
--> Success
 Build scheduled, use 'oc logs -f bc/approach1' to track its progress.
 Run 'oc status' to view your app.
 {% endhighlight %}
 
#### Approach 1 characteristics:

- The developer will need to install the builder image previously.
- The  available version of the builder image uses CentOS7 as Operational System.
- The developer don’t need to know anything about Dockerfile syntax
- The developer don’t need Java or Maven installed locally. This is provided by the builder image.
- The maven repository is download again in every build.
- The developer don’t need to download the source code locally since you can use the git url as parameter of the builder image.
- OpenShift displays the commit information.

{: .center}
![](https://rhdevelopers.files.wordpress.com/2016/06/approach1.png?w=640)

### Approach 2 – OpenShift binary builds

{: .center}
![](https://rhdevelopers.files.wordpress.com/2014/09/openshift-logo-121c2a0c397c2a0121.jpeg?w=640)

This approach assumes that some steps provided by the builder image will be realised by the developer.  The developer will be responsible to create the “fat-jar” and also the Dockerfile.  OpenShift will read the Dockerfile and create the image for you.

Some extra commands are necessary in this approach:

{% highlight bash %}
# Create a binary build - This will generate the ImageStream
$ oc new-build --binary --name=approach2
...
# Create the fat jar with Maven
$ mvn package

# Start the build using local resources (fat jar and Dockerfile)
$ oc start-build approach2 --from-dir=. --follow
Uploading "." at commit "HEAD" as binary input for the build ...
Uploading directory "." as binary input for the build ...
...
I0619 07:07:37.501658 1 docker.go:93] Pushing image 172.30.67.31:5000/wildflyswarm/approach2:latest ...
I0619 07:09:17.039484 1 docker.go:97] Push successful

# Create the OpenShift application
$ oc new-app approach2
...
--> Success
 Run 'oc status' to view your app.
{% endhighlight %}

#### Approach 2 characteristics:

- The developer will be responsible to create the fat-jar and the Dockerfile
- The build time is reduced given that the fat-jar is built using the developer pre-cached Maven repository (~/.m2)
- The developer needs to have the sources locally. In fact it could use the fat-jar and the Dockerfile in a git repository, but it’s not a good-practice to store any jars in the git repository, specially this with more than a thousand megabytes.
- OpenShift doesn’t show the commit that originated the build.

{: .center}
![](https://rhdevelopers.files.wordpress.com/2016/06/approach2.png?w=640)

### Approach 3 – Use Fabric8 Maven plugin

{: .center}
![](https://rhdevelopers.files.wordpress.com/2016/06/cover.png?w=106&h=106)

[Fabric8](http://fabric8.io/) has a great community that “provides an opinionated open source microservices platform based on [Docker](https://www.docker.com/), [Kubernetes](http://kubernetes.io/) and [Jenkins](https://jenkins.io/)“. For that reason, many support [tools](http://fabric8.io/guide/tools.html) are available to help the developer to work with [Kubernetes](http://kubernetes.io/) and [OpenShift](https://www.openshift.org/).

This approach uses Fabric8 [Maven plugin](http://fabric8.io/guide/mavenPlugin.html) that generates a docker image and the Kubernetes JSON files that will be applied in OpenShift.

To use this approach, the Maven file of the application has to be customized. You can browse the [pom.xml file](https://github.com/redhat-helloworld-msa/hola/blob/master/pom.xml#L215-L249) of the example “hola” application to see the configurations of the Fabric8 Maven plugin.

It requires that the developer have access to the Internal Docker registry of OpenShift so he can publish the image there. To have the environment pointing to the internal OpenShift registry, CDK provides a Vagrant plugin called service-manager. We just need to execute the following command in the same folder of the Vagrantfile.

{% highlight bash %}
$ cd <CDK-PATH>/cdk/components/rhel/rhel-ose
$ eval "$(vagrant service-manager env docker)"
{% endhighlight %}

This command will export the DOCKER_HOST, DOCKER_CERT_PATH and DOCKER_TLS_VERIFY environment variables that will be used to publish the docker image internally.  After that, the deploy becomes as simple as:

{% highlight bash %}
$ mvn clean package docker:build fabric8:json fabric8:apply
{% endhighlight %}

#### Approach 3 characteristics:

- The developer needs access to the docker registry of OpenShift.
- The developer doesn’t need to run any OpenShift commands.
- Most Java developers are familiar with Maven.
- There’s no DeploymentConfig created by Fabric8 Maven plugin.
- Fabric8 Maven plugin automatically creates OpenShift Services and Routes.
- All OpenShift configuration is performed in the pom.xml
- The readinessProbe and livenessProbe can also be configured in the pom.xml

#### Approach 4 – Use a pre-built image from docker registry

{: .center}
![](https://rhdevelopers.files.wordpress.com/2014/05/homepage-docker-logo.png?w=133&h=110)

The blog post [“How to run Java fat-jars in Docker, Kubernetes and OpenShift”](/2016/06/25/run-fatjars-docker-openshift/) contains a complete description to easily craft a docker image to run “fat-jars”.  The Dockerfile for the “hola” application is simple as:

{% highlight bash %}
FROM fabric8/java-jboss-openjdk8-jdk:1.0.13

ENV JAVA_APP_JAR hola-swarm.jar
ENV AB_OFF true

EXPOSE 8080

ADD target/hola-swarm.jar /app/
{% endhighlight %}

The docker image of the “hola” application is published in Docker hub as redhatmsa/hola, which allows OpenShift to create an application as simple as:

{% highlight bash %}
$ oc new-app --name approach4 redhatmsa/hola
{% endhighlight %}

#### Approach 4 characteristics:

- A simple command creates the OpenShift application
- It needs a pre-built image in the docker registry
- Any changes in the source code requires the image to be rebuilt and published again

## Conclusion
Each one of these 4 approaches has its own advantages and disadvantages due to its individual characteristics. This blogs presents the characteristics of each one allowing the developer to choose the one that is most suitable for his needs.