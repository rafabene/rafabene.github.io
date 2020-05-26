---
title: What is JDF – JBoss Developer Framework?
---

A versão em português pode ser encontrada em:

[https://community.jboss.org/wiki/JDF-OQueEOJBossDeveloperFramework](https://community.jboss.org/wiki/JDF-OQueEOJBossDeveloperFramework)

The portuguese version can be found at:

[https://community.jboss.org/wiki/JDF-OQueEOJBossDeveloperFramework](https://community.jboss.org/wiki/JDF-OQueEOJBossDeveloperFramework)

![](http://design.jboss.org/jbossdeveloperframework/logo/final/jdf_logo_450px.png)

The first impression when we heard about the [JDF](http://www.jboss.org/jdf/),  is that since it is called a Framework we should find a library, a jar ou anything that remember the traditional frameworks. But JDF is a awesome knowledge base for the Java EE 6 platform. It is wrong to think that it is only a collection of examples and that’s why I’ll explain the JDF’s concept.

You known that Java EE 6 spec is a umbrella for other technologies ( EJB 3.1, JPA 2.0, JSF 2.0, Servlet 3.0, JAX-RS 1.1, etc). But what if we had a knowledge base with examples that even developed for many people it still following well known standards and recommendations that you can apply to your project? The JDF is it too.

The first thing that we realize is that all quickstarts follow the Maven structure. That’s the recommended way to create your projects and keep your source code. And please, do not commit your .project and .classpath files in you version control system (git, svn, cvs), ok? Eclipse with JBoss Tools or JBDS supports Maven projects and do the integration in a transparent way with WTP (Web Tools Project).

1 – Archetypes

Since all JDF project are Maven based, lets start by the Archetypes. Suppose that you want to start a new project Java EE 6. JDF give us some Maven Archetypes so you can start your project without needing to setup from scratch. JDF archetypes:

WEB Project – Project (WAR packaging) with an example application.
WEB Blank Project – Project (WAR packaging) blank.
EAR Project – Project (EAR packaging with WAR and EJB) with an example application.
EAR Blank Project – Project (EAR packaging with WAR and EJB) blank.
For more informations about Archetypes, take a  look at [http://maven.apache.org/archetype/maven-archetype-plugin/usage.html](http://maven.apache.org/archetype/maven-archetype-plugin/usage.html)

2 – BOMs (Bill of Materials)

Bill of Materials (aka BOM) is a concept that Maven uses to manage dependency versions. Only who had the experience to “mavenize” a existent project, knows how it is difficult to find the right version for a artifact.

Did you already see the quantity of JBoss project and technologies at  [http://www.jboss.org/projects](http://www.jboss.org/projects/)?

Now imagine that you have to know which is the right “groupId” and “artifactId” for each one of these projects? Now imagine how the complexity increases if you need to find the right version too. Increase the complexity if you need to know what version doesn’t conflict with another version from another artifact. This is the kind of thing that you won’t have if you use JDF BOMs. All information about GAV (“groupId”, “artifactId”, “version”) is there.

Another great advantage is that all these BOM managed artifacts have the guaranty that it is on Maven Central repo (except by the Enterprise artifacts).

Some BOMs options:

Java EE 6 with Hibernate
Java EE 6 with Resteasy
Java EE 6 with Infinispan
Java EE 6 with Richfaces
Java EE 6 with …
The are many other options. If you need to know more about BOMs, take a look at: [http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Dependency_Management](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Dependency_Management)

3 – Stacks

We saw how BOMs can help, but we know that the JBoss technologies are continuously being improved and for this reason, a BOM or Archetype can be updated. For customers who has subscriptions, there’s also many products that needs integrate in a supported way as is the case of [EAP](http://www.redhat.com/products/jbossenterprisemiddleware/application-platform/) (Enterprise Application Platform) 6.0.x and [WFK](http://www.redhat.com/products/jbossenterprisemiddleware/web-framework-kit/) (Web Framework Kit) 2.x.

JDF has a sub project called Stacks that manages the relationship between a Runtime  (JBoss AS, JBoss EAP, [JDG](http://www.redhat.com/products/jbossenterprisemiddleware/data-grid/), etc) Archetypes and its versions. You can have an idea about this relationship by looking this diagram: [https://github.com/jboss-jdf/jdf-stack/blob/1.0.0.Final/fileformat.png](https://github.com/jboss-jdf/jdf-stack/blob/1.0.0.Final/fileformat.png)

This relationship is kept in a YAML file: [https://github.com/jboss-jdf/jdf-stack/blob/1.0.0.Final/stacks.yaml](https://github.com/jboss-jdf/jdf-stack/blob/1.0.0.Final/stacks.yaml) – This file is consumed for some tools like JBDS and the [Forge plugin](http://www.jboss.org/jdf/stack/plugin-jdf/). So when you use this tools, you’re using the latest data from BOMs and Archetypes according to the selected Runtime.

The JDF JBoss Stacks page also uses the same information provided by this file. In this page is possible to navigate through this Runtimes, Boms, Archetypes and versions. Check it out: [http://www.jboss.org/jdf/stack/stacks/](http://www.jboss.org/jdf/stack/stacks/)

You can also consume this data without dealing with downloading and parsing the file. You can use the a Java client tool: [http://www.jboss.org/jdf/stack/stacks-client/](http://www.jboss.org/jdf/stack/stacks-client/)

4 – Quickstarts and standards

With almost [80 quickstarts](http://www.jboss.org/developer-materials/#!sys_type=quickstart) being developed by [many people](http://www.jboss.org/get-involved/) (as from Red Hat engineers, as from community colaborators, there are some standards that are followed through the quickstarts and you can also follow on your projects.

For example, almost all projects doesn’t use the web.xml file. The beans.xml and faces-config.xml files are used to activate CDI and JSF. It is also possible to see how is the recommended way to use a specific technology (infinispan, deltaspike, etc), as how the project can be tested using [arquillian](http://arquillian.org/) or even how to deploy the application using  [jboss-as-maven-plugin](https://docs.jboss.org/jbossas/7/plugins/maven/latest/).

More than examples, this quickstarts are truly tutorials since all of them are very well documented and its source code comments explains why some technicals approach was used. JDF is Java EE 6 knowledge base. One of the most complete and rich that exists today!

Contribute

Great part of this quickstarts were built by Red Hat engineers considering the recommended way to use the technologies. For example, Richfaces and Infinispan quickstarts were built by its engineers from these teams. But don’t let this afraid you. You can contribute, just look on the quantity of contributors at [http://www.jboss.org/jdf/about/contributors/](http://www.jboss.org/jdf/about/contributors/)

Each [JDF](http://www.jboss.org/jdf/) page has a “Get Involved”  section with a step-by-step guide on how to contribute. It’s an excellent opportunity to involve more with open source and work with amazing technologies.

All source code are at [https://github.com/jboss-developer](https://github.com/jboss-developer) – Take a look. It is full of cool stuff there.

Cya!