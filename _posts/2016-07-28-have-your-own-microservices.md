---
title: Have your own Microservices playground
---

This post was originally posted on [Red Hat Developers](https://developers.redhat.com/blog/2016/07/27/have-your-own-microservices-playground/)

Microservices are standing at the “[Peak of Inflated Expectations](https://en.wikipedia.org/wiki/Hype_cycle)“. It’s immeasurable the number of developers and companies that want to bring in this new [development paradigm](http://www.joburg-archive.co.za/2006/pdfs/gds_book/chapter3.pdf) and don’t know what challenges they will face. Of course, the challenges and the reality of an Enterprise company that has been producing software for the last 10 or 20 years is totally different from the start-up company that just released its first software some months ago.


{: .center}
![](/assets/images/320px-gartner_hype_cycle-svg.png)

Before adopting microservices as an architectural pattern, there are several questions that need to be addressed:

- Which languages and technologies should I adopt?
- Where and how do I deploy my microservices?
- How do I perform service-discovery in this environment?
- How do I manage my data?
- How do I design my application to handle failure? (Yes! It will fail!) 
- How do I address authentication, monitoring and tracing?

{: .center}
![](/assets/images/microservices-concerns.png)

Wouldn’t it be interesting if the developer could have his or her own playground to explore and become familiarized with these approaches?

[Red Hat Developers](https://developers.redhat.com/) has worked intensively to integrate different opensource technologies and create a showcase of how cloud-native apps can interact in a microservices architecture: The [Helloworld-MSA](https://github.com/redhat-helloworld-msa/helloworld-msa) playground!

This distinct demo allows developers execute some “helloworld” microservices, built using different technologies, and explore the following features:


- Different implementations: [WildFly Swarm](http://wildfly-swarm.io/), Spring Boot, [Vert.X](http://vertx.io/) and [NodeJS](https://nodejs.org/).
- 3 invocation patterns: Browser as a client, [API-Gateway](http://microservices.io/patterns/apigateway.html), Chained invocation;
- [Service-registry](http://microservices.io/patterns/service-registry.html), Self-healing, Load-balancing  and [Blue/Green deployments](http://blog.christianposta.com/deploy/blue-green-deployments-a-b-testing-and-canary-releases/) – Provided by [OpenShift](https://www.openshift.com/);
- JVM Monitoring – Provided by [Jolokia](https://jolokia.org/);
- Service Monitoring – Provided by [Hystrix Dashboard](https://github.com/Netflix/Hystrix/tree/master/hystrix-dashboard) via [Kubeflix](https://github.com/fabric8io/kubeflix);
- Service Tracing – Provided by [Zipkin](http://zipkin.io/) via [Fabric8’s Kubernetes Zipkin](https://github.com/fabric8io/kubernetes-zipkin);
- [Circuit breaker](https://github.com/Netflix/Hystrix/wiki/Configuration#CommandCircuitBreaker) and [Fallback](https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback) – Provided by [Hystrix](https://github.com/Netflix/Hystrix);
- REST invocation – Provided by [Feign](https://github.com/Netflix/feign) via [HystrixFeign](https://github.com/Netflix/feign/tree/master/hystrix);
- REST API specification – Provided by [Swagger](http://swagger.io/);
- CI/CD pipelines – Provided by [Jenkins](https://jenkins.io/) with [Pipeline](https://wiki.jenkins-ci.org/display/JENKINS/Pipeline+Plugin) plugin.

All these pieces are deployed via the [CDK](http://developers.redhat.com/products/cdk/overview/) (Container Development Kit), which is essentially a [Vagrant](https://www.vagrantup.com/) file that creates a virtualized environment with Docker, Kubernetes and OpenShift Enterprise running on [RHEL (Red Hat Enterprise Linux)](http://developers.redhat.com/products/rhel/overview/).

To have a fast introduction of Helloworld-MSA, you can also watch Burr Sutter’s video:

<iframe width="560" height="315" src="https://www.youtube.com/embed/SPATMHP-xw8" frameborder="0" allowfullscreen></iframe>


Besides the instruction, developers can also find the source-code of the microservices, api-gateway and frontend in the following GitHub organization: [https://github.com/redhat-helloworld-msa/](https://github.com/redhat-helloworld-msa/)

A rendered HTML version of the instruction can be found in the following link: [http://bit.ly/msa-instructions](http://bit.ly/msa-instructions) – Try it yourself!

We hope that you enjoy! And remember: *We love feedbacks!* 