---
layout: post
title: How to expose Kubernetes Services
comments: true
---
An undeniable thruth is that [Docker](http://www.docker.com/) is the standard technology for containers and it is pushing forward the adoption of microservices architecutures as I mentioned in [my previous post](/2015/10/29/javaone-docker-hol/). But I challenge you: What would you use to spread your containers in the cloud, handle their IP addresses (service location) and manage it (heath state, replication, scaling, etc)? The fastest answer to this question is to use any Docker "cluster" technologies. 

In this field, two projects are the most popular: [Docker Swarm](https://docs.docker.com/swarm/) and [Kubernetes](http://kubernetes.io/). These project sare the proof that a series of surrounding technologies raises to support Docker as a container technology

### The difference between Docker Swarm and Kuberntes

Docker Swarm is the "native" clustering for Docker containers and here are some advantages of using it:

- It's really simple usage. You see all cluster nodes as a single host.
- Uses the same Docker API (see above).
- Easy installation with [Docker Machine](https://docs.docker.com/machine/).
- Can use [Docker Compose](https://docs.docker.com/compose/) to scale the containers.

In the other hand, Kubernetes is much more complex to learn but once you get familiar with some main concepts ([Pods](http://kubernetes.io/v1.1/docs/user-guide/pods.html), [Replication Controllers](http://kubernetes.io/v1.1/docs/user-guide/replication-controller.html), [Services](http://kubernetes.io/v1.1/docs/user-guide/services.html) and [Labels](http://kubernetes.io/v1.1/docs/user-guide/labels.html)) of it, you quickly notice that Kubernetes is more robust to address container's needs for a clustered environment in the cloud.

I've been using both and my personal conclusion is that simple container's clusters are fine with Docker Swarm but complex applications pushed me to Kubernetes. Note that the feature gap between Docker Swarm and Kubernetes is getting smaller each Docker release, speacilly with the recent [Docker 1.9](https://blog.docker.com/2015/11/docker-1-9-production-ready-swarm-multi-host-networking/).

### Exposing Kubernetes Services

#### Setup the example

If you are familiar with Kubernetes, you probably have already deployed your application in it. Anyways, a very frequent question is how to access the application.

To explore the available options, lets start Kubernetes using Vagrant/VirtualBox

{% highlight bash linenos %}
export KUBERNETES_PROVIDER=vagrant
curl -sS https://get.k8s.io | bash
{% endhighlight %}

Now Let's create a file with the following content:

- Mysql (POD)
- MySql (Service)
- Wildfly (Replication Controller)


{% highlight yaml linenos %}
apiVersion: v1
kind: Pod
metadata:
  name: mysql-pod
  labels:
    name: mysql-pod
    context: docker-k8s-lab
spec:
  containers:
    -
      name: mysql
      image: mysql:latest
      env:
        -
          name: "MYSQL_USER"
          value: "mysql"
        -
          name: "MYSQL_PASSWORD"
          value: "mysql"
        -
          name: "MYSQL_DATABASE"
          value: "sample"
        -
          name: "MYSQL_ROOT_PASSWORD"
          value: "supersecret"
      ports:
        -
          containerPort: 3306
----
apiVersion: v1
kind: Service
metadata:
  name: mysql-service
  labels:
    name: mysql-pod
    context: docker-k8s-lab
spec:
  ports:
    # the port that this service should serve on
    - port: 3306
  # label keys and values that must match in order to receive traffic for this service
  selector:
    name: mysql-pod
    context: docker-k8s-lab
----
apiVersion: v1
kind: ReplicationController
metadata:
  name: wildfly-rc
  labels:
    name: wildfly
    context: docker-k8s-lab
spec:
  replicas: 1
  template:
    metadata:
      labels:
        name: wildfly
        context: docker-k8s-lab
    spec:
      containers:
      - name: wildfly-rc-pod
        image: arungupta/wildfly-mysql-javaee7:k8s
        ports:
        - containerPort: 8080
{% endhighlight %}

Now we can depoy it to the cluster:


{% highlight yaml linenos %}
$ kubectl create -f <path to the file>
pods/mysql-pod
services/mysql-service
replicationcontrollers/wildfly-rc
{% endhighlight %}

#### Acessing the application

From now, we have 3 ways to access the application. Kubernetes spreads the application PODs among the nodes. The only way to find out and keep track of those Pods is using `Kubernetes Services`.

Kubernetes allows you to define 3 types of services using the `ServiceType` field in its yaml file.

Valid values for the `ServiceType` field are:

- *`ClusterIP`*: use a cluster-internal IP only - this is the default and is discussed above. Choosing this value means that you want this service to be reachable only from inside of the cluster.
- *`NodePort`* : on top of having a cluster-internal IP, expose the service on a port on each node of the cluster (the same port on each node). You'll be able to contact the service on any <NodeIP>:NodePort address.
- *`LoadBalancer`*: on top of having a cluster-internal IP and exposing service on a NodePort also, ask the cloud provider for a load balancer which forwards to the Service exposed as a <NodeIP>:NodePort for each Node.

As you can see in `mysql-service` this field was not specifed, which means that the MySql is accessible only inside the cluster. But if you want to access WildFly you may need to be inside on of Kubernetes node. 

##### a) From inside the cluster

To access the application you first need to get the IP address of the POD:

{% highlight bash linenos%}
#Get the name of the pod
$ kubectl.sh get pods
NAME               READY     STATUS    RESTARTS   AGE
mysql-pod          1/1       Running   0          49m
wildfly-rc-3w0fr   1/1       Running   0          49m

# Get the IP of the pod using the pod name
{% raw %}
$ kubectl.sh get -o template po wildfly-rc-3w0fr --template={{.status.podIP}}
10.246.1.5
{% endraw %}
#Access the application using the POD IP address
[vagrant@kubernetes-minion-1 ~]$ curl http://10.246.1.5:8080/employees/resources/employees/
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><collection><employee><id>1</id><name>Penny</name></employee><employee><id>2</id><name>Sheldon</name></employee><employee><id>3</id><name>Amy</name></employee><employee><id>4</id><name>Leonard</name></employee><employee><id>5</id><name>Bernadette</name></employee><employee><id>6</id><name>Raj</name></employee><employee><id>7</id><name>Howard</name></employee><employee><id>8</id><name>Priya</name></employee></collection>

{% endhighlight %}

##### b) Exposing the NodePort

As you can see, there's no WildFly Kubernetes Service defined, that's why we need many steps to get the IP address of the POD running WildFly. If we kill the POD, the IP address will change. So, for frontend services like WildFly, it's recommended to create a Kubernetes Service and access the application through it. 

The only single difference is that we want now to access this POD from outside our cluster. We will now explore how to do it using the NodePort. First we need to define the following Kubernetes Service file:


{% highlight yaml linenos %}
apiVersion: v1
kind: Service
metadata: 
  name: wildfly-service
  labels: 
    name: wildfly
    context: docker-k8s-lab
spec: 
  type: NodePort
  ports:
    # the port that this service should serve on
    - port: 8080
  # label keys and values that must match in order to receive traffic for this service
  selector: 
    name: wildfly
    context: docker-k8s-lab
{% endhighlight %}

Now, Kubernetes master will allocate a port (default: 30000-32767), and each Node will proxy that port (the same port number on every Node) into the WildFly POD.

{% highlight yaml linenos %}
$ kubectl.sh create -f <path to wildfly service file>
You have exposed your service on an external port on all nodes in your
cluster.  If you want to expose this service to the external internet, you may
need to set up firewall rules for the service port(s) (tcp:30140) to serve traffic.

See http://releases.k8s.io/HEAD/docs/user-guide/services-firewalls.md for more details.
services/wildfly-service
{% endhighlight %}

Finally you can now use the combination between the `node ip` and the `service port` to access the service.

{% highlight bash linenos %}
#Get the Node IP
$ kubectl.sh get nodes
NAME         LABELS                              STATUS
10.245.1.3   kubernetes.io/hostname=10.245.1.3   Ready

#Get the PORT of the service
$ kubectl.sh describe se wildfly-service
Name:               wildfly-service
Namespace:          default
Labels:             context=docker-k8s-lab,name=wildfly
Selector:           context=docker-k8s-lab,name=wildfly
Type:               NodePort
IP:                 10.247.39.239
Port:               <unnamed> 8080/TCP
NodePort:           <unnamed> 30140/TCP
Endpoints:          10.246.88.7:8080
Session Affinity:   None
No events.

#Finally Access the application from outside the cluster
open your browser at http://10.245.1.3:30140/employees/
{% endhighlight %}


##### The other ways (LoadBalancer and OpenShift)

You can define `ServiceType=LoadBalancer`. But that works only for cloud provider which support external load balancers. There's a `work in progress` project at [https://github.com/kubernetes/contrib/tree/master/service-loadbalancer](https://github.com/kubernetes/contrib/tree/master/service-loadbalancer) that is self-defined as "how to set up a bank of haproxy for platforms that don't have load balancers".

Since Kubernetes running on Vagrant doesn't support `ServiceType=LoadBalancer` at this moment, I never used it. When I have such requirenment I strongly recommend to use OpenShift. 

OpenShift v3 is built on top of Kubernetes and brings a series of improvements for final users like [ Routes](https://docs.openshift.com/enterprise/3.0/architecture/core_concepts/routes.html), but that's a subject for another topic.

![](https://blog.openshift.com/wp-content/uploads/routing.png)


## More Kubernetes on Devoxx MA

I decided to explore this subject when I was preparing the materials for the Univesity [Docker and Kubernetes for Java EE Developers](http://cfp.devoxx.ma/2015/talk/UMK-8733/Docker_and_Kubernetes_for_Java_EE_Developers) for Devoxx Marroco.

If you will be at this amazing event, you're invite to join me in a two hours university on Monday 16th 08:30-10:30 at Studio des Arts Vivants, Casablanca, Morroco.

Cya!

