---
title: Book Digest - Modern Java EE Design Patterns
---

The last time that I talked about a book here was in [2007](/2007/04/20/caia-na-real-e-seja/). But I had again that feeling that pushes me to talk more about a specific book: *Modern Java EE Design Patterns - Building Scalable Architecture for Sustainable Enterprise Development* - from [Markus Eisele](http://blog.eisele.net/p/about-me_15.html)

I admire Markus' communication and technical skills and this year I was fortunate to start working with him and [Arun Gupta](http://blog.arungupta.me/about/) on a project that is giving a lot of fruits to the community:  ["Docker for Java EE Developers - The Hands on Lab"](https://github.com/redhat-developer/docker-java/graphs/contributors) - But after 5 months working together, it was just during the [JavaOne](http://developers.redhat.com/events/javaone/2015/) that we met in person. On this ocasion, he was releasing his book [*"Modern Java EE Design Patterns"*](http://blog.eisele.net/2015/10/my-book-modern-java-ee-design-patterns.html) and I had the chance to get my printed (and signed) copy on Red Hat's booth.

![](http://2.bp.blogspot.com/-czsVBeHTDT0/Vhe8MNYE8xI/AAAAAAAA-aY/NjOEa9mOV8Q/s320/cover-markus.png)
![](/assets/images/markusbook.png)

But it was just this weekend during a long trip to Casablanca, Morocco for [DevoxxMA](http://devoxx.ma/) that I had the chance to read it completely. 

The book starts with a brief introduction on how is the software development today for Enterprise, and also with a resumed history from the very first release of Java Enterprise Edition until nowadays. This makes the reader to understand why certain architectural approaches were adopted along the years and why it didn't prevail. This opened space to understand the moment that we are living now and the subject of the book: DevOps, Microservices, Containers, Cloud, etc...

Without talking about Java, Markus explains the principles about software design for scalable Enterprise. The microservices strategies for adoption is very well presented with discussions about the [Greenfield](https://en.wikipedia.org/wiki/Greenfield_project) vs [Brownfield](https://en.wikipedia.org/wiki/Brownfield_(software_development)), and microservices best practices (automation, design for failure, data separation, integrity and performance). Some crosscutting concerns (security, logging, health checks, testing) were also presented as the open source projects that can be used to address them. I really liked to see that [DDD - Domain Driven Design](https://domainlanguage.com/ddd/) - was also explored as I really think that when you develop an application using DDD "building blocks" and "strategic design", it's easy to evolve your application. DDD is a subject that I've been talking about since before [2008](http://rafabene.com/2008/09/30/workshop-ddd-em-brasilia/).

Having microservices concepts introduced, finally Java came in to the scene and it's then explained how to implement the microservices concepts using Java EE 7 and its specifications (JAX-RS 2.0, WebSocker 1.0, Concurrency utilities 1.0, Servlet 3.1, EJB 3.2). Different "Migration Approaches" were presented and discussed after that. There's a whole chapter with "Microservices Design Patterns" with a didatic explanation and a diagram about each one of them.

After reading this book, I totally agree with [Mark Little](https://developer.jboss.org/blogs/mark.little)'s foreword:

>"Overall, this report is a goldmine of experience-driven information valuable to Java EE developers who are looking to answer the question about where Java EE fits into the rapidly evolving world of microservices, DevOps and the cloud." (Mark Little)

The language used is easy to understand and the reading is a pleasing experience for Java EE developers that want to get in touch with modern Enterprise Development. If you are curious about it, I have an excellent news to you: There's a free available to download here in [Markus' Blog post about the book](http://blog.eisele.net/2015/10/my-book-modern-java-ee-design-patterns.html). 

Good reading!



