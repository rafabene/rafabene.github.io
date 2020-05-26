---
title: 10 things to avoid in docker containers
---

So you finally surrendered to containers and discovered that they solve a lot of problems and have a lot of **advantages**:

![](https://rhdevelopers.files.wordpress.com/2014/11/shipping_containers_at_clyde.jpg?w=320)

- **First: Containers are immutable** – The OS, library versions, configurations, folders, and application are all wrapped inside the container. You guarantee that the same image that was tested in QA will reach the production environment with the same behaviour.
- **Second: Containers are lightweight** – The memory footprint of a container is small. Instead of hundreds or thousands of MBs, the container will only allocate the memory for the main process.
- **Third: Containers are fas**t – You can start a container as fast as a typical linux process takes to start. Instead of minutes, you can start a new container in few seconds.

However, many users are still treating containers just like typical virtual machines and forget that containers have an important characteristic: **Containers are disposable**.

{: .text-center}
> The mantra around containers:

{: .text-center}
 **“Containers are ephemeral”**.

{: .text-center}
![](https://rhdevelopers.files.wordpress.com/2015/03/rh_icon_container_with_app_flat.png?w=640)

This characteristic forces users to change their mindset on how they should handle and manage containers.

I explained all what you should **NOT** do in containers to keep extracting the best beneficts of it in [Red Hat Developers Blog - 10 things to avoid in docker containers](https://developers.redhat.com/blog/2016/02/24/10-things-to-avoid-in-docker-containers/). Check it out, and register at [Red Hat Developers website](https://developers.redhat.com/) to have access to more amazing content for Developers.

