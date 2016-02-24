---
layout: post
title: 10 things to avoid in docker containers
comments: true
---

So you finally surrendered to containers and discovered that it solves a lot of problems and has a lot of **advantages**:

{: .center}
![](https://rhdevelopers.files.wordpress.com/2014/11/shipping_containers_at_clyde.jpg?w=320)

- **First: It’s immutable** – The, OS, Library versions, configurations, folders, application is there. You guarantee that the same image tested in QA will arrive land in the production with the same behaviour.
- **Second: It’s lightweight** – The memory footprint of a container is too small. Instead of hundreds or thousands of MBs, it will just allocate the memory for the main process plus some tens of MBs.
- **Third: It’s fast** – You can start a container as fast as a single process takes to start. Instead of minutes, you can start a new container in at most some few seconds.

But many users still treating containers just like typical virtual machines and forget that they also have another great advantage besides many others: It’s disposable.


> The big mantra about containers: 

{: .center}
 **“Containers are ephemeral”**.

{: .center}
![](https://rhdevelopers.files.wordpress.com/2015/03/rh_icon_container_with_app_flat.png?w=640)

This characteristic *“per se”* forces users to change their mindset on how they should handle and manage containers.

I explained all what you should **NOT** do in containers to keep extracting the best beneficts of it in [Red Hat Developers Blog](http://developerblog.redhat.com/2016/02/24/10-things-to-avoid-in-docker-containers/). Check it out, and register at [Red Hat Developers website](https://developers.redhat.com/) to have access to more amazing content for Developers.

