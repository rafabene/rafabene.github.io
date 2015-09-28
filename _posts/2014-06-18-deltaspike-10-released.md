---
layout: post
title: DeltaSpike 1.0 Released
comments: true
---

![](http://deltaspike.apache.org/resources/images/logos/logo.png)

Hi!!!! 

When [CDI spec](http://cdi-spec.org/) came out, there was a huge revolution on everything related to [DI](http://martinfowler.com/articles/injection.html) on the Java ecosystem. One of the biggest features on CDI is that you’re allowed to [create your own extensions](http://blog.eisele.net/2010/01/jsr-299-cdi-portable-extensions.html) and make CDI even more powerful.

Imagine: Simply drop a jar to your project’s classpath and voila! You now have available new powerful beans available to be injected on your source code, bringing the most amazing features with loose coupling.

This is [DeltaSpike](deltaspike.apache.org)! A powerful set of CDI Extensions that will make your project rock!

DeltaSpike beans are available through modules built around the “core” module.

- *The “Core” module* – Is the only one required and brings features like Type-safe ProjectStage, @Exclude (to veto CDI beans), Type-safe messages & I18n, Exception handling framework and more.

- *The “Security” module* – Allows a fine grained and Type-safe control over method invocations.

- *The “JPA” module* – Allows a transactional context and also defines a CDI transactional scope.

- *The “Data” module* – Makes easy for you to implement the repository pattern as also brings an implementation for Entity auditing.

- *The “JSF” module* – Has a lot of improvements for JSF as Type-safe view config, Multi-Window handling, new scopes (WindowScoped, ViewScope, ViewAccessScoped, GroupedConversationScoped) and integration with DeltaSpike “core” messages and exception handling.

- *The “Bean Validation” module* – Allows you to inject CDI objects, EJBs etc in to your validators.

- *The “Servlet” module* – Allows the injection of Servlet objects on environments prior to CDI 1.1. It also propagates a number of Servlet object lifecycle events to the CDI event bus.

- *The “Partial Bean” module* –  Allows you to implement a generic handler to replace manual implementations of interfaces (or abstract classes)

- *The “Scheduler” module* – Provides a simple integration with Quartz v2 (per default) or any other scheduler which supports cron-expressions for job-classes.

- *The “Container & Control / Test-Control”* – Allows you to boot and shutdown the CDI container in SE applications, control the life-cycle of the built-in contexts of the CDI container and write CDI based tests easily.

Check it on [http://deltaspike.apache.org/](deltaspike.apache.org)

If you find any issues, please report on [Jira](https://issues.apache.org/jira/browse/DELTASPIKE) or get in touch through the [mail list](http://deltaspike.apache.org/community.html#mailing-lists).