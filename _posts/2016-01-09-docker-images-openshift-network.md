---
layout: post
title: From Docker images to Openshift + Docker Networks
comments: true
---

On my [last post](/2015/12/15/docker-learning-path-wildfly/), I talked about how to run the same set of Docker images using [Docker](https://docs.docker.com/engine/userguide/), [Docker Compose](https://docs.docker.com/compose/), [Docker Machine](https://docs.docker.com/machine/), [Docker Swarm](https://docs.docker.com/swarm/) and [Kubernetes](http://kubernetes.io/).

The purpose was to show a suggested learning path from raw Docker CLI to a distrubute Docker cluster (using Docker Swarm or Kubernetes).

As I promissed, I've updated this lab to include two new features that I want to show:

- Docker Networks

- Openshift v3

## Docker Networks

Since [Docker 1.9](https://blog.docker.com/2015/11/docker-1-9-production-ready-swarm-multi-host-networking/), networking is now a first class citizen and ready to use with Docker Swarm and Compose.

With the introduction of [Docker Networks](https://docs.docker.com/engine/userguide/networking/dockernetworks/), [docker linking](https://docs.docker.com/engine/userguide/networking/default_network/dockerlinks/) is expected to be deprecated and removed in a future release.   

The environment consists in a [WildFly](http://www.wildfly.org/) container with [Ticket Monster application](http://www.jboss.org/ticket-monster/) connected to a [Postgres](http://www.postgresql.org/) container. To load balance the application, I use [Apache httpd](https://httpd.apache.org/) with [mod_cluster](http://mod-cluster.jboss.org/). The overview diagram of this environment can be seen in the following picture:

![](/images/docker_mod_cluster.png)

Now, the [docker-compose.yml](https://github.com/rafabene/devops-demo/blob/master/compose/docker-compose.yml) file contains reference to a docker network called *"mynet"*. This network should be available before the execution of the container through the command  *docker network create mynet*. 

With [multi-host networks](https://docs.docker.com/engine/userguide/networking/get-started-overlay/) available, the same [docker-compose.yml](https://github.com/rafabene/devops-demo/blob/master/compose/docker-compose.yml) file is now being used also on the [docker swarm cluster](https://github.com/rafabene/devops-demo/blob/master/swarm/Readme.md).

When a network is specified in Docker container, the `/etc/hosts` file of all other containers in the network is updated to include the name of the container. You can see this behaviour running the following commands:

{% highlight bash %}
$ docker network create mynet
$ docker run -d --name container1 --net mynet jboss/wildfly
$ docker run -d --name container2 --net mynet jboss/wildfly
$ docker run -d --name container3 --net mynet jboss/wildfly

$ docker exec container1 cat /etc/hosts
....
172.18.0.3	container2
172.18.0.3	container2.mynet
172.18.0.4	container3
172.18.0.4	container3.mynet
{% endhighlight %}

## Openshift v3

![](/images/openshift_logo.png)

Another great addition was the inclusal of [*Openshift instructions*](https://github.com/rafabene/devops-demo/blob/master/openshift/Readme.md) for the same lab. 

One of the biggest differences is that Openshift provide [*Routes feature*](https://docs.openshift.com/enterprise/3.0/architecture/core_concepts/routes.html) that allow external host name mapping and load balancing. Due to this "native" feature, we don't need to deploy [mod_cluster](http://mod-cluster.jboss.org/) as part of the lab environment. 

Instead of using the [default postgres image](https://hub.docker.com/_/postgres/), Openshift provides a [Docker image for running PostgreSQL](https://hub.docker.com/r/openshift/postgresql-92-centos7/). This image can provide database services based on username, password, and database name settings provided via configuration.

After deploying the Database, the [*Openshift instructions*](https://github.com/rafabene/devops-demo/blob/master/openshift/Readme.md) contains instructions to deploy the WildFly using two methods:

- Using 3 CLI commands to deploy a POD, a Service and a Router.
- Deploy the POD, Service and Router defined in a [single YAML file](https://github.com/rafabene/devops-demo/blob/master/openshift/wildfly-rc-service-route.yaml).

One of the greatest contributions of this lab adition, is that it can also be used as an overview guide to run Docker images inside Openshift.

Don't forget to check the recently added [*Openshift instructions*](https://github.com/rafabene/devops-demo/blob/master/openshift/Readme.md) 

## Conclusion

While following the instructions of [github.com/rafabene/devops-demo](https://github.com/rafabene/devops-demo), you will face 5 different ways to have the same environment being executed with only [Docker tools](http://www.docker.com/products/overview#/docker_toolbox) (Compose, Machine, Swarm) and also with [Kubernetes](http://kubernetes.io/) and [Openshift](https://www.openshift.org/).

Executing it in the proposed order can provide you a didatic guide on how they are related to each other. This lab can also be used as a template for your most of middleware containers and applications, and at the same time, You will be in touch with the latest version and features of the Docker universe since I plan to continue updating this test environment frequently.
