---
layout: post
title: Automating microservices deployment with Ansible
comments: true
---

This post was originally posted on [Red Hat Developers](https://developers.redhat.com/blog/2016/11/21/automating-microservices-deployment-with-ansible/)

One of the main principles of microservices is to be independently deployable. As a consequence, Microservices development and operation tend to be much more complex than a Monolith because of their distributed nature — if your IT team has not moved out yet from its silos and has adopted DevOps practices, the operations team will not really understand why they have to deploy hundreds of independent software pieces in opposite to the “good old monolith”.

__“You need a mature operations team to manage lots of services, which are being redeployed regularly”  ([Microservices trade-offs by Martin Fowler](https://martinfowler.com/articles/microservice-trade-offs.html)).__

The operations team and the software development team should work together adopting DevOps practices to avoid silos and deployment process where the software team throws the software over the wall.


{: .center}
![](/images/wall-of-confusion.png)

Ideally, each Microservices team is multifunctional and own the software artifact from conception to production. Given the multifunctional nature of these teams, “[infrastructure as code](https://www.thoughtworks.com/insights/blog/infrastructure-code-reason-smile) (IaC)” and automation are now a necessity. DevOps teams share the knowledge of server provisioning, configuration management and deployment. There are several tools and approaches for IaC. As an example, I can mention Kubernetes, that allows you to [define its objects as yaml or json files](https://github.com/redhat-developer-demos/kubernetes-lab/tree/master/kubernetes).

{: .center}
![](/images/microservices-teams.png)


A couple months ago, I published a blog post that shows how to [have your own (no-cost) microservices playground](/2016/07/28/have-your-own-microservices/).  The focus of this material is educational. It provides [instructions](http://bit.ly/msainstructions) on how to deploy each microservice independently. However, some people would like to see all of them running running in few minutes.

To show how you can run this microservices playground environment in less than 20 minutes, I decided to record the following screencast that shows how to create an OpenShift cluster using “oc cluster up” (Check out “[Four creative ways to create an OpenShift/Kubernetes dev environment](/2016/07/25/wildfly-swarm-openshift/)“), and deploy all of them using [Ansible](https://www.ansible.com/).

I hope that you enjoy.

<iframe width="560" height="315" src="https://www.youtube.com/embed/oWsW8GZbQR0" frameborder="0" allowfullscreen></iframe>