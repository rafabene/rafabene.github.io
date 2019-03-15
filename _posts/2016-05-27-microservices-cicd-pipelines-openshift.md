---
layout: post
title: Microservices CI/CD Pipelines in OpenShift
comments: true
---

One of the greatest advantages of using Docker containers is the fact that you can move them between environments. A promotion from Development to a Production environment, shouldn't take more than some few seconds. This is one aspect of ["Continuous Delivery"](http://martinfowler.com/bliki/ContinuousDelivery.html)

Because [Microservices Architectures](http://microservices.io/) are "independently replaceable and upgradeable", they are the best scenario to show a ["Deployment Pipeline"](http://martinfowler.com/bliki/DeploymentPipeline.html).

![](/images/pipeline.jpg)

In the last two months, I've been working on a Sample application called "Red Hat Helloworlds MSA" that demonstrates different aspects of microservices. This application show how you can indendently deploy the microservices using different technologies (JAX-RS ([EAP](http://developers.redhat.com/products/eap/overview/) and [WildFly Swarm](http://wildfly-swarm.io/)), [Spring-boot](http://projects.spring.io/spring-boot/), [Vert.X](http://vertx.io/), [Dropwizard](http://www.dropwizard.io/), [NodeJS](https://nodejs.org), etc) and how you can use different architectural patterns to integrate them. It also uses [Netflix OSS](https://netflix.github.io/), integrated via [Kubeflix](https://github.com/fabric8io/kubeflix), and [ZipKin](http://zipkin.io/) for tracing.

The source of these microservices are available in the following repo: [https://github.com/redhat-helloworld-msa/helloworld-msa](https://github.com/redhat-helloworld-msa/helloworld-msa). You can follow those steps and have your own "microservices playground". There's an amazing demo in a video recorded by [Burr Sutter](https://twitter.com/burrsutter): [https://www.youtube.com/watch?v=SPATMHP-xw8](https://www.youtube.com/watch?v=SPATMHP-xw8 )

But what happens if you "detect an error" in production? How can you be benefited by [OpenShift](https://www.openshift.com/) to replace this "error" in a transparent way for the microservices client? To answer this question, I recorded a demo that shows how you can use [Jenkins](https://jenkins.io/) and the [Pipeline plugin](https://github.com/jenkinsci/pipeline-plugin/blob/master/TUTORIAL.md) to trigger the build and promote deployments from the "Development" to "Production". The most important part to me in this whole deployment process is the fact that the application is always available. Enjoy de video and don't forget to try it yourself!

<iframe width="560" height="315" src="https://www.youtube.com/embed/N8R3-eNVoEc" frameborder="0" allowfullscreen></iframe>