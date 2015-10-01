---
layout: post
title: The rise of Javascript
comments: true
---
I'm the kind of person who believes that staying in a comfort zone keeps you out
of *"Where the magic happens"*.

![](/images/wherethemagichappens.jpg)

I've worked with [JBoss Seam Framework](http://www.seamframework.org) for a long time (2007-2012) and the way
it solved the integration between the frontend (with JSF) and the backend (with
EJB, JPA and Bean Validation) influenced me a lot on how I develop using Java EE
6/7 with CDI and JSF. Besides the influence, for many other reasons I always try to keep my source code as close
as possible from the spec. And why am I telling you that? I just wanted you to
understand why I'm starting to look to Javascript frameworks only in 2015.

During the last years I saw the "rise and fall" of many Javascript frameworks
and I watched with a very skeptical look on the way that people were using it.
I couldn't believe that having a Javascript calling a REST endpoint could be
more productive and less error prone than JSF (or any other Java web framework).
I always said to myself that once that a JS framework gets "stable" in the
market, I would learn it.

![](/images/javascript-frameworks-days.png)

This year, a very popular web project (20.000
simultaneous access) that I worked (2008) was refactored
from JSF to [AngularJS](http://www.angularjs.org) and the backend in Java had the
Service Layer exposed as REST endpoints using JAX-RS. As result of this refactoring,
the server demand dropped from 100 instances to 20 instances because with AngularJS all the frontend processing happens on
the browser keeping the servers responsible just to handle light REST
invocations.

This scenario for Angular and also the
[Google
Trends](http://www.google.com/trends/explore?hl=en-US&q=ember+js,+angular+js,+backbone+js,+react+js,+meteor+js&cmpt=q&tz=Etc/GMT%2B4&tz=Etc/GMT%2B4&content=1)
placed AngularJS in my radar of technologies to learn and get expertise. So
I was suggested by [George Gastaldi](https://twitter.com/gegastaldi) to take
a look on a serie of videos called ["Desvendando a linguagem
Javascript"](https://www.youtube.com/playlist?list=PLQCmSnNFVYnT1-oeDOSBnt164802rkegc)
and ["Tudo sobre
AngularJS"](https://www.youtube.com/playlist?list=PLQCmSnNFVYnTD5p2fR4EXmtlR6jQJMbPb)
by [Rodrigo Branas](https://twitter.com/rodrigobranas). With those videos
I could make my first dive in the JS world.

<script type="text/javascript"
src="//www.google.com/trends/embed.js?hl=en-US&q=ember+js,+angular+js,+backbone+js,+react+js,+meteor+js&cmpt=q&tz=Etc/GMT%2B4&tz=Etc/GMT%2B4&content=1&cid=TIMESERIES_GRAPH_0&export=5&w=500&h=330"></script>

Using tools like [bower](http://bower.io/), [Yeoman](http://yeoman.io/) and
[Grunt](http://gruntjs.com/)/[Gulp](http://gulpjs.com) made me see the beauty of
[NodeJS](https://nodejs.org/en/), [NPM](https://www.npmjs.com/) and [Functional
Programming](https://www.youtube.com/playlist?list=PL0zVEGEvSaeEd9hlmCXrk5yUyqUag-n84).

It is much probable that you will see more posts related to these subjects from
now on. So here I drop a mental note to myself:

*Keep calm and Learn Javascript!*

![](/images/keep-calm-and-learn-javascript.png)

P.S.  I still believe that Java will keep strong in the Enterprise!

