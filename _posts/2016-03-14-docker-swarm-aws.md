---
layout: post
title: Docker swarm cluster in AWS
comments: true
---

Containers, containers, containers everywhere! 

During the last year, I've been talking and showing [docker containers](http://rafabene.com/talks/). However, because of possible network issues (speed, firewall, latency, etc) in some places, I prefer to run my docker host (daemon) in the cloud. To make that possible, I use [Docker Machine](https://docs.docker.com/machine/) which contains [drivers](https://docs.docker.com/machine/drivers/) for many cloud providers like Amazon, Azure, GCE, VirtualBox, etc. Since I've been using AWS for while and docker-machine has a driver for it, the use of AWS to run a docker daemon seems a good choice. 

As [I've told in a previous post](/2015/12/15/docker-learning-path-wildfly/), I have [an existing test environment](https://github.com/rafabene/devops-demo/) that I use to explore and show different container's tools and platforms ([docker](https://github.com/rafabene/devops-demo/blob/master/Dockerfiles/ticketmonster-ha/Readme.md), [docker-compose](https://github.com/rafabene/devops-demo/blob/master/compose/Readme.md), [swarm](https://github.com/rafabene/devops-demo/blob/master/swarm/Readme.md), [kubernetes](https://github.com/rafabene/devops-demo/blob/master/kubernetes/Readme.md), [openshift](https://github.com/rafabene/devops-demo/blob/master/openshift/Readme.md), etc). This environment allows me to simulate docker networks and scaling. To be able to simulate multi-host docker containers, I need to have multiple hosts. For simple scenarios and to show a docker container environment, the use of docker-swarm + docker-compose running in AWS seems to be fair enough. 

If you want to try [docker swarm](https://docs.docker.com/swarm/) in [AWS](https://aws.amazon.com/), I recorded the following screencast that guides you through the steps to run it. For a production-ready container based platform, I use and suggest [OpenShift Enterprise v3](https://www.openshift.com/enterprise/features.html).

<iframe width="560" height="315" src="https://www.youtube.com/embed/JgUyI-MIKZ0" frameborder="0" allowfullscreen></iframe>

I hope this information is useful for you.