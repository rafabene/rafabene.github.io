---
layout: post
title: Starting with Cloud-Native applications
comments: true
---

Four years ago I wrote a ["Docker learning path"](/2015/12/15/docker-learning-path-wildfly/) so people could easily learn how to take [baby steps](https://dictionary.cambridge.org/us/dictionary/english/baby-step) toward Kubernetes. Essentially the steps are: 1 - learn docker, 2 - learn docker-compose , 3 - learn Docker Swarm *(Today I would suggest skipping this step)*, and 4 - learn Kubernetes.

While that [post]((/2015/12/15/docker-learning-path-wildfly/)) had focused on suggesting a learning path for people to adopt containers, this blog post is focused on suggesting a learning path for developers to produce cloud native applications. 

In the last years, backend developers are interested in producing cloud-native applications, but still many of them don't know where to start. Because I've been [talking in conferences](/talks/), [recording videos](https://www.youtube.com/user/rafabene), [writing a book](https://www.oreilly.com/library/view/microservices-for-java/9781492038290/) about microservices and cloud-native applications, people come to me asking **"Where should I start?"**. So I decided to write this blog post to help those developers. Here is my suggestion of baby steps, no matter what language you are using.

## Baby steps toward Cloud Native applications

These steps assume two applications (two microservices) that will communicate with each other, and handle the most common scenarios in a cloud native environment. The expected result will be a microservice A calling a microservice B. You're encouraged to implement the uses cases bellow to gain the expertise needed to create cloud-native applications.

- Use your favorite language to create a **microservice B** with some [REST endpoints](https://restfulapi.net/) to perform [CRUD operations](https://www.codecademy.com/articles/what-is-crud). 

    {: .center}
    Suggested read: [CRUD and REST](https://www.codecademy.com/articles/what-is-crud)

- Document your REST endpoints with [OpenAPI or Swagger](https://swagger.io/docs/specification/about/). 

    {: .center}
    Suggested read for Java Developers: [Swagger UI on MicroProfile OpenAPI](https://www.phillip-kruger.com/post/microprofile_openapi_swaggerui/)

- Create a **microservice A** to consume some REST endpoints from the **microservice B** that you've created. You can start simple by consuming the GET operation from the **microservice B**.

- Externalize the configuration of your **microservice A** by allowing an environment variable to inform the URL of the **microservice B**.

    {: .center}
    Suggested video: [MicroProfile Config API in 5 minutes](https://www.youtube.com/watch?v=PKAaYyDxJbA)

- Stop **microservice B** and make **microservice A** fault tolerant with [Fallback](https://badia-kharroubi.gitbooks.io/microservices-architecture/patterns/communication-patterns/fallback-pattern.html). 
- Make **microservice A** take 1 minute to reply and make **microservice B** [timeout](https://stackoverflow.com/questions/49704708/what-is-a-connection-timeout-during-a-http-request) within 10 seconds.
- Make **microservice A** responds randomly with a HTTP error, and makes **microservice B** retries the connection in case of an error. **microservice A** should always get a valid reply or a fallback at this point.

    {: .center}
    Recommended read for Java developers: [Quarkus and MicroProfile Fault Tolerance](https://quarkus.io/guides/microprofile-fault-tolerance)

- Implement a distributed tracing with [Opentracing](https://opentracing.io/) between **microservices A and B**. Tip: You will need to forward the HTTP headers from A to B.
    
    {: .center}
    Suggested example project: <https://github.com/rafabene/tracing-demo>

- Implement Authentication in a REST endpoint of the **microservice B** and make **microservice A** propagate the credentials to **microservice B**. Use [JWT](https://jwt.io/) - JSON Web Tokens.
    
    {: .center}
    Suggested read to for Java developers: [Build a REST API Using Java, MicroProfile, and JWT Authentication](https://developer.okta.com/blog/2019/07/10/java-microprofile-jwt-auth)

## Move to the cloud

- Package your **microservices A and B** in a container. Each one on one container. Publish them in a public container registry like [Docker hub](https://hub.docker.com/) or [Quay.io](https://quay.io/).
- Run your **microservices A and B** in a Kubernetes Cluster. For rapid and local startup you can use [minikube](https://kubernetes.io/docs/setup/learning-environment/minikube/). For on-premises production usage, you can rely on [Oracle Linux Cloud Native Environment](https://docs.oracle.com/en/operating-systems/olcne/). If you want to try OLCNE locally, you can use [this Vagrant file](https://github.com/oracle/vagrant-boxes/tree/master/OLCNE) for it.
- Create a CI/CD Pipeline to take your source code to production. There are many solutions in the market. I've demoed [Jenkins](https://jenkins.io/) and Kubernetes in the video [CI/CD Deployment pipeline using Jenkins on Kubernetes to deploy a Quarkus Cloud-native application](https://www.youtube.com/watch?v=T2lVK8iU5XU)
- Implement [Kubernetes Liveness probe and Readiness probe](https://cloud.google.com/blog/products/gcp/kubernetes-best-practices-setting-up-health-checks-with-readiness-and-liveness-probes). The Readiness probe should mark your application as *"ready"* only when **microservice A** can communicate with **microservice A**.
- Install a Service Mesh solution like [Istio](https://istio.io/) on your Kubernetes Cluster and perform some uses cases like the ones found in this [Istio tutorial](/istio-tutorial/)

    {: .center}
    Suggested read to for Portguese readers: [10 Vídeos para aprender sobre desenvolvimento Cloud-Native](/2020/01/09/10-videos-cloud-native/)

## Next Steps

These are my recommendations to learn how to taker your CRUD application to a next level using different cloud native technologies. Of course, sky's the limit as you can see at [CNCF Cloud Native Interactive Landscape](https://landscape.cncf.io/zoom=250). There are other challenges that might appear and it will cause you to learn patterns like ["EDA - Event-driven architecture"](https://microservices.io/patterns/data/event-driven-architecture.html), [CQRS](https://microservices.io/patterns/data/cqrs.html), [Sagas](https://microservices.io/patterns/data/saga.html), [Serverless](https://fnproject.io/), etc. But I don't want to scare you. Like every pattern, you will only use any of them when you need it.

Furthermore, if you are a Java developer, please, subscribe to my youtube channel at <https://youtube.com/rafabene>. I'm starting a series of videos about the [MicroProfile](https://microprofile.io/) APIs. You can also help me by following me on Twitter ([@rafabene](https://twitter.com/rafabene)), and you can also help other developers by sharing this content.

If you want to know more about a specific topic, just drop me a comment below.