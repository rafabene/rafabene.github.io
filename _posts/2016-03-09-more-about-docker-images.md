---
title: More about docker images size
---

I've published recently a blog post listing the [10 things to avoid in docker containers](/2016/02/24/10-things-to-avoid-in-docker/). If you haven't read this post yet, I strongly recommend you to do so. That blog post mentions 10 different recommendations that users should consider when dealing with docker containers. I've tried to make general recommendations and less opinionated about the specific use of the container, so no matter if you are running a database, a NodeJS application, or a Java EE application server, these recommendations still valid.

However the recommendation **#3 - Don’t create large images**, have caused many doubts when I mentioned *"Don’t install unnecessary packages or run “updates” (yum update) that downloads many files to a new image layer"*.  Many people have wondered how a simple *"yum update"* could create a large image. 

Today I published another post in [Red Hat Developers Blog - Keep it small: a closer look at Docker image sizing](http://developers.redhat.com/blog/2016/03/09/more-about-docker-images-size/) that explains that recommendation, and  explore the proper way to perform updates and keep the images small at the same time. [Check it out](http://developers.redhat.com/blog/2016/03/09/more-about-docker-images-size/), and register at [Red Hat Developers website](https://developers.redhat.com/) to have access to more related content for Developers.

![](https://rhdevelopers.files.wordpress.com/2015/01/docker-whale-home-logo.png?w=459)
