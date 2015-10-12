---
layout: post
title: How I migrated my blog to Jekyll/Github
comments: true
---

I was a [frequent blogger](/posts/) until 5 years ago as I explained on my [previous post](http://rafabene.com/2015/09/28/5-years-in-1-post/) but this year I decided to return blogging and then move my blog to use [Jekyll](https://jekyllrb.com/) and host it on [GitHub Pages](https://pages.github.com/).

This post makes an overview of the steps that I used.

### Why Github pages?


[GitHub Pages](https://pages.github.com/) allows Users, Organizations and Projects to host websites directly from your [Github repository](https://github.com/rafabene/rafabene.github.io). It allows you to publish [HTML files](https://help.github.com/articles/creating-project-pages-manually/), use an [Automatic Page Generator](https://help.github.com/articles/creating-pages-with-the-automatic-generator/)(based on project's README file content) or use [Jekyll](https://help.github.com/articles/using-jekyll-with-pages/).

[Jekyll](https://help.github.com/articles/using-jekyll-with-pages/) is a static site generator. It makes *"easy to create site-wide headers and footers without having to copy them across every page. It also offers some other advanced [templating features](http://jekyllrb.com/docs/templates/)."* (from [GitHub help](https://help.github.com/articles/using-jekyll-with-pages/))

I've used another static site generators before like [{ :awestruct }](http://awestruct.org/). In my Blog, besides the *commenting* system, all other *"elements"* doesn't need to by dynamic, which means that a static site generator is a perfects match.

### The Jekyll theme

I'm an awful webdesigner, so I decided to look for an existing Jekyll theme. There's an website called [jekyllthemes.org](http://jekyllthemes.org/) that contains several themes that you can use.

I don't know how, but I landed on the [Minimal Mistakes Jekyll Theme](https://mademistakes.com/work/minimal-mistakes-jekyll-theme/). To start using it is as simple as its [instalation guide](https://mademistakes.com/work/minimal-mistakes-jekyll-theme/#installation) shows.

### The content migration from JRoller to Jekyll

It was very important for me to don't loose [all the previous content](/posts/) of my blog, so I was fortunate to find a webpage that explains hot to [migrate from JRoller to Jekyll](http://www.pongasoft.com/blog/yan/misc/2011/03/02/migrating-jroller-401-to-jekyll/).

JRoller is very customizable which allowed me to create a new page template that generates an XML with all Blog entries and comments. Then a Groovy script converted it to the proper directory structure needed by Jekyll and transformed it to [Markdown](https://daringfireball.net/projects/markdown/) - The template language used by Jekyll.

Of course, I needed to do many adjusts on the exported result, but it worthed to have the previous blog content available out of JRoller and available in text mode in [my github repostiory](https://github.com/rafabene/rafabene.github.io).

### The comments provider with Disqus

Having the comments back was also important to me, so the next step was to setup an account in [Disqus](https://disqus.com/). I've used it before when I was working with [{ :awestruct }](http://awestruct.org/) generated websites and it the [Minimal Mistakes Jekyll Theme](https://mademistakes.com/work/minimal-mistakes-jekyll-theme/) supports it [out of the box](https://en.wikipedia.org/wiki/Out_of_the_box_(feature)).

The same page that contained [instructions on how to migrate from JRoller](http://www.pongasoft.com/blog/yan/misc/2011/03/02/migrating-jroller-401-to-jekyll/), contained another Groovy script that converts the comments (extracted in a XML file) to another format that could be imported by [Disqus](https://disqus.com/). 

Disqus provides a tool to do an URL mapping from previous comments/blog to a new one. It was very helpful to adjust the imported URLs from JRoller.

### The final customization

Having my website page running at [http://rafabene.github.io](http://rafabene.github.io) made me feel that it was time to setup a [custom domain in Github Pages](https://help.github.com/articles/setting-up-a-custom-domain-with-github-pages/) - I had the domain  [rafabene.com](http://rafabene.com) since 2009 when I tried to use Wordpress.com as to host my Blog. 

The good part on having control on your own generated website is that I could activate back the [Google Analytics](http://www.google.com/analytics/) and [Google Webmasters](https://www.google.com/webmasters/).

In my [old JRoller blog](http://www.jroller.com/rafaelbenevides/) I placed a [Meta Refresh Tag](http://webdesign.about.com/od/metataglibraries/a/aa080300a.htm) to this page.

Finally I replaced the [Social Sharing links](https://mmistakes.github.io/minimal-mistakes/theme-setup/#social-sharing-links) to use the ones provided by [ShareThis](http://www.sharethis.com/). and also [added links to previous/next posts](http://david.elbe.me/jekyll/2015/06/20/how-to-link-to-next-and-previous-post-with-jekyll.html).

### Conclusion

The whole process took me a whole Saturday and I hope that describing the basic steps could encourage more people to get the benefits of [GitHub Pages](https://pages.github.com/) hosting. I'm very happy with the final result and having the blog availabe in [my github repostiory](https://github.com/rafabene/rafabene.github.io) is an extra. Feel free to poke around!

![](http://www.ryadel.com/wp-content/uploads/2015/03/github-logo.png)