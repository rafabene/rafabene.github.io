/**
 * Parses the xml file produced by the template from jroller
 */

def xml = new XmlSlurper()
def posts = xml.parse(new File(args[0]))

posts.post.each { post ->
  def date = post."@date".toString().split("T")[0]
  def title = post."@title".toString()
  def permalink = post."@permalink".toString() - "http://www.pongasoft.com/blog/yan"
  def name = permalink.split("/")[-1].replace("_", "-")
  def tags = post."@tags".toString().split(" ").findAll { it }
  def category = post."@category".toString()
  category = category.replace(".", "")
  def filename = "${date}-${name}.textile"

  println "${filename} => ${title} / ${tags}"


  def content = """---
layout: post
permalink: ${permalink}
title: ${title}"""

  content = new StringBuilder(content)

  if(category)
    content << "\ncategory: ${category}"
  if(tags)
  {
    content << "\ntags:"
    tags.each {
      content << "\n  - ${it}"
    }
  }
  content << "\n---\n"

  content << post.content.text()

  new File(filename).text = content
}
